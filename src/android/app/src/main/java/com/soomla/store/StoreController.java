/*
 * Copyright (C) 2012 Soomla Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soomla.store;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import com.soomla.billing.BillingServiceV3;
import com.soomla.store.data.StoreInfo;
import com.soomla.store.domain.data.VirtualCurrencyPack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONObject;
import sg.gumi.bravefrontier.BraveFrontier;
import sg.gumi.bravefrontier.BraveFrontierJNI;
import sg.gumi.util.GooglePlayBilling;

/**
 * This class is where all the important stuff happens. You can use it to purchase products from Google Play,
 * buy virtual goods, and get events on whatever happens.
 *
 * This is the only class you need to initialize in order to use the SOOMLA SDK. If you use the UI,
 * you'll need to also use {@link com.soomla.store.storefront.StorefrontActivity}.
 *
 * In addition to initializing this class, you'll also have to call
 * {@link StoreController#storeOpening(android.app.Activity, android.os.Handler)} and
 * {@link com.soomla.store.StoreController#storeClosing()} when you open the store window or close it. These two
 * calls initializes important components that support billing and storage information (see implementation below).
 * IMPORTANT: if you use the SOOMLA storefront (SOOMLA Storefront), than DON'T call these 2 functions.
 *
 */
public class StoreController {
    private static final String TAG = "SOOMLA StoreController";
    private static Activity mActivity;
    private static Handler mHandler;
    private static StoreController sInstance;
    private GooglePlayBilling mGooglePlayBillingService;
    private boolean mStoreOpen = false;
    private Lock mLock = new ReentrantLock();

    /**
     * This initializer also initializes {@link StoreInfo}.
     * @param storeAssets is the definition of your application specific assets.
     */
    public void initialize(IStoreAssets storeAssets, Activity activity, Handler handler) {
        mActivity = activity;
        mHandler = handler;
        Log.d("StoreController", "initialize store assets");
        if (storeAssets != null) {
            StoreInfo.setStoreAssets(storeAssets);
        }
        startBillingService();
    }

    public static void syncItemPricesAndPurchases() {
        try {
            mActivity.runOnUiThread(new Runnable() {
                @Override // java.lang.Runnable
                public void run() {
                    StoreController.getInstance()._syncItemPricesAndPurchases();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void _syncItemPricesAndPurchases() {
        if (this.mGooglePlayBillingService == null) {
            GooglePlayBilling googlePlayBilling = new GooglePlayBilling();
            this.mGooglePlayBillingService = googlePlayBilling;
            googlePlayBilling.initialize();
        }
        this.mGooglePlayBillingService.SyncItemPricesAndPurchasesThread();
    }

    public static void buyGoogleMarketItem(final String str) throws Exception {
        mActivity.runOnUiThread(new Runnable() { // from class: com.soomla.store.StoreController.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    StoreController.getInstance()._buyGoogleMarketItem(str);
                } catch (Exception unused) {
                }
            }
        });
    }

    public void _buyGoogleMarketItem(String str) throws Exception {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(BraveFrontier.getAppContext()).edit();
        edit.putBoolean("RESTORE_TRANSACTION_NEXT_TIME", true);
        edit.commit();
        this.mGooglePlayBillingService.RequestPurchase(str);
    }

    public static void storeOpening() throws Exception {
        getInstance()._storeOpening();
    }

    public void _storeOpening() {
        Log.d("StoreController", "opening store");
        this.mLock.lock();
        if (this.mStoreOpen) {
            Log.e(TAG, "You already sent storeOpening !");
            this.mLock.unlock();
        } else {
            this.mStoreOpen = true;
            this.mLock.unlock();
            startBillingService();
        }
    }

    public static void storeClosing() throws Exception {
        getInstance()._storeClosing();
    }

    public void _storeClosing() {
        this.mStoreOpen = false;
        stopBillingService();
    }

    public void onPurchaseStateChange(String iapData, String iapSignature, String purchase) {
        String productId;
        if (iapData.isEmpty() && iapSignature.isEmpty()) {
            BraveFrontierJNI.purchaseStateChangedCallback(iapData, iapSignature, purchase);
            return;
        }
        BraveFrontierJNI.purchaseStateChangedCallback(iapData, iapSignature, purchase);
        BillingServiceV3.consumePurchase(iapData, iapSignature);
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(BraveFrontier.getAppContext()).edit();
        edit.remove("RESTORE_TRANSACTION_NEXT_TIME");
        edit.commit();
        JSONObject jSONObject = null;
        try {
            jSONObject = new JSONObject(iapData);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            jSONObject.getString("orderId");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        try {
            productId = jSONObject.getString("productId");
        } catch (Throwable ex) {
            ex.printStackTrace();
            productId = "";
        }
        try {
            StoreInfo.getPackByGoogleProductId(productId);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    static String getPackPriceForProductID(String productId) {
        Log.d("BraveFrontier", "Get pack price");
        try {
            VirtualCurrencyPack packByGoogleProductId = StoreInfo.getPackByGoogleProductId(productId);
            if (packByGoogleProductId.getPriceString().equals("")) {
                syncItemPricesAndPurchases();
            }
            Log.v("BraveFrontierIAP", productId + ": " + packByGoogleProductId.getPriceString());
            return packByGoogleProductId.getPriceString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private boolean startBillingService() {
        Log.d("StoreController", "startBillingService()");
        this.mLock.lock();
        if (this.mGooglePlayBillingService == null) {
            GooglePlayBilling googlePlayBilling = new GooglePlayBilling();
            this.mGooglePlayBillingService = googlePlayBilling;
            googlePlayBilling.initialize();
        }
        this.mLock.unlock();
        return true;
    }

    private void stopBillingService() {
        this.mLock.lock();
        this.mLock.unlock();
    }

    public static StoreController getInstance() {
        if (sInstance == null) {
            sInstance = new StoreController();
        }
        return sInstance;
    }

    private StoreController() {
    }
}