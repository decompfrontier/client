package com.soomla.billing;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.vending.billing.IInAppBillingService;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.soomla.store.StoreController;
import com.soomla.store.data.StoreInfo;
import com.soomla.store.domain.data.VirtualCurrencyPack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import org.json.JSONException;
import org.json.JSONObject;
import sg.gumi.bravefrontier.BraveFrontier;
import sg.gumi.util.BFConfig;

/* loaded from: classes.dex */
public class BillingServiceV3 extends Service implements ServiceConnection {
    private static final String TAG = "SOOMLA BillingService";
    private static LinkedList<BillingRequest> mPendingRequests = new LinkedList<>();
    private static IInAppBillingService mService;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCanRunRequests() {
        return BFConfig.PLATFORM == BFConfig.PLATFORM_AMAZON || mService != null;
    }

    public boolean bindToMarketBillingService() {
        try {
            Log.i(TAG, "binding to Market billing service v3");
            Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            intent.setPackage("com.android.vending");
            if (bindService(intent, this, 1)) {
                Log.e(TAG, "Connected to v3 billing service.");
                return true;
            }
            Log.e(TAG, "Could not bind to v3 billing service.");
            return false;
        } catch (SecurityException e) {
            Log.e(TAG, "Security exception: " + e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void runPendingRequests() {
        int i = -1;
        while (true) {
            BillingRequest peek = mPendingRequests.peek();
            if (peek == null) {
                if (i >= 0) {
                    Log.i(TAG, "stopping billing service v3, startId: " + i);
                    stopSelf(i);
                }
                return;
            }
            if (peek.runIfConnected()) {
                mPendingRequests.remove();
                if (i < peek.getStartId()) {
                    i = peek.getStartId();
                }
            } else {
                bindToMarketBillingService();
                return;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            Log.d(TAG, "Billing service connected v3");
            IInAppBillingService asInterface = IInAppBillingService.Stub.asInterface(iBinder);
            mService = asInterface;
            if (asInterface != null) {
                runPendingRequests();
            } else {
                Log.e(TAG, "Failed to bind IInAppBillingService.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        Log.w(TAG, "Billing service disconnected v3");
        if (mService != null) {
            try {
                unbindService(this);
            } catch (IllegalArgumentException unused) {
                Log.w(TAG, "Billing service already disconnected v3");
            }
        }
        mService = null;
    }

    public void unbind() {
        try {
            unbindService(this);
        } catch (IllegalArgumentException unused) {
        }
    }

    public void setContext(Context context) {
        attachBaseContext(context);
    }

    abstract class BillingRequest {
        protected long mRequestId;
        private final int mStartId;

        protected abstract long run() throws RemoteException;

        public BillingRequest(int i) {
            this.mStartId = i;
        }

        public int getStartId() {
            return this.mStartId;
        }

        public boolean runOrWaitRequest() {
            BillingServiceV3.mPendingRequests.add(this);
            if (BillingServiceV3.this.isCanRunRequests()) {
                BillingServiceV3.this.runPendingRequests();
                return true;
            }
            return BillingServiceV3.this.bindToMarketBillingService();
        }

        public boolean runIfConnected() {
            Log.d(BillingServiceV3.TAG, getClass().getSimpleName());
            if (!BillingServiceV3.this.isCanRunRequests()) {
                return false;
            }
            try {
                this.mRequestId = run();
                Log.d(BillingServiceV3.TAG, "request id: " + this.mRequestId);
                return true;
            } catch (RemoteException e) {
                onRemoteException(e);
                return false;
            }
        }

        protected void onRemoteException(RemoteException remoteException) {
            Log.w(BillingServiceV3.TAG, "remote billing service crashed");
            BillingServiceV3.this.onServiceDisconnected(null);
        }
    }

    public class SyncItemPricesAndPurchasesThread extends Thread {
        public SyncItemPricesAndPurchasesThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            ArrayList<String> stringArrayList;
            if (BFConfig.PLATFORM != BFConfig.PLATFORM_AMAZON) {
                ArrayList<String> arrayList = new ArrayList<>();
                Iterator<VirtualCurrencyPack> it = StoreInfo.getCurrencyPacks().iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().getProductId());
                }
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("ITEM_ID_LIST", arrayList);
                try {
                    Bundle skuDetails = BillingServiceV3.mService.getSkuDetails(3, BraveFrontier.getAppContext().getPackageName(), "inapp", bundle);
                    if (skuDetails.getInt(Consts.BILLING_RESPONSE_RESPONSE_CODE) == 0 && (stringArrayList = skuDetails.getStringArrayList("DETAILS_LIST")) != null) {
                        Iterator<String> it2 = stringArrayList.iterator();
                        while (it2.hasNext()) {
                            try {
                                JSONObject jSONObject = new JSONObject(it2.next());
                                String string = jSONObject.getString("productId");
                                String string2 = jSONObject.getString("price");
                                Iterator<VirtualCurrencyPack> it3 = StoreInfo.getCurrencyPacks().iterator();
                                while (true) {
                                    if (it3.hasNext()) {
                                        VirtualCurrencyPack next = it3.next();
                                        if (next.getProductId().equals(string)) {
                                            next.setPriceString(string2);
                                            break;
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception unused) {
                    return;
                }
            }
            if (BFConfig.PLATFORM == BFConfig.PLATFORM_AMAZON) {
                return;
            }
            Bundle purchases = null;
            try {
                purchases = BillingServiceV3.mService.getPurchases(3, BillingServiceV3.this.getPackageName(), "inapp", null);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

            if (purchases.getInt(Consts.BILLING_RESPONSE_RESPONSE_CODE) == 0) {
                ArrayList<String> stringArrayList2 = purchases.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> stringArrayList3 = purchases.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                ArrayList<String> stringArrayList4 = purchases.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                for (int i = 0; i < stringArrayList2.size(); i++) {
                    StoreController.getInstance().onPurchaseStateChange(stringArrayList2.get(i), stringArrayList3.get(i), stringArrayList4.get(i));
                }
            }
        }
    }

    class SyncItemPricesAndPurchases extends BillingRequest {
        public SyncItemPricesAndPurchases() {
            super(-1);
        }

        @Override // com.soomla.billing.BillingServiceV3.BillingRequest
        protected long run() throws RemoteException {
            BillingServiceV3.this.new SyncItemPricesAndPurchasesThread().start();
            return 1L;
        }
    }

    public class RequestPurchase extends BillingRequest {
        public final String mDeveloperPayload;
        public final String mProductId;
        public final String mProductType;

        @Override // com.soomla.billing.BillingServiceV3.BillingRequest
        public /* bridge */ /* synthetic */ int getStartId() {
            return super.getStartId();
        }

        @Override // com.soomla.billing.BillingServiceV3.BillingRequest
        public /* bridge */ /* synthetic */ boolean runIfConnected() {
            return super.runIfConnected();
        }

        @Override // com.soomla.billing.BillingServiceV3.BillingRequest
        public /* bridge */ /* synthetic */ boolean runOrWaitRequest() {
            return super.runOrWaitRequest();
        }

        @Deprecated
        public RequestPurchase(BillingServiceV3 billingServiceV3, String str) {
            this(str, null, null);
        }

        @Deprecated
        public RequestPurchase(BillingServiceV3 billingServiceV3, String str, String str2) {
            this(str, null, str2);
        }

        public RequestPurchase(String str, String str2, String str3) {
            super(-1);
            this.mProductId = str;
            this.mDeveloperPayload = str3;
            this.mProductType = str2;
        }

        @Override // com.soomla.billing.BillingServiceV3.BillingRequest
        protected long run() throws RemoteException {
            if (BFConfig.PLATFORM == BFConfig.PLATFORM_AMAZON) {
                return 0L;
            }
            Bundle buyIntent = BillingServiceV3.mService.getBuyIntent(3, BillingServiceV3.this.getPackageName(), this.mProductId, "inapp", this.mDeveloperPayload);
            int i = buyIntent.getInt(Consts.BILLING_RESPONSE_RESPONSE_CODE);
            if (i != 0) {
                Log.e(BillingServiceV3.TAG, "getBuyIntent fail! Response code: " + i);
            }
            PendingIntent pendingIntent = (PendingIntent) buyIntent.getParcelable("BUY_INTENT");
            if (pendingIntent == null) {
                Log.e(BillingServiceV3.TAG, "Error with requestPurchase");
                return Consts.BILLING_RESPONSE_INVALID_REQUEST_ID;
            }
            try {
                BraveFrontier activity = BraveFrontier.getActivity();
                IntentSender intentSender = pendingIntent.getIntentSender();
                Intent intent = new Intent();
                Integer num = 0;
                int intValue = num.intValue();
                Integer num2 = 0;
                Integer num3 = 0;
                activity.startIntentSenderForResult(intentSender, CredentialsApi.ACTIVITY_RESULT_OTHER_ACCOUNT, intent, intValue, num2.intValue(), num3.intValue());
                return 0L;
            } catch (IntentSender.SendIntentException e) {
                Log.e(BillingServiceV3.TAG, "Error with startIntentSenderForResult: " + e.getMessage());
                return Consts.BILLING_RESPONSE_INVALID_REQUEST_ID;
            }
        }
    }

    public static void consumePurchase(String str, String str2) {
        String str3;
        if (BFConfig.PLATFORM == BFConfig.PLATFORM_AMAZON) {
            return;
        }
        try {
            str3 = new JSONObject(str).getString("purchaseToken");
        } catch (Throwable th) {
            th.printStackTrace();
            str3 = "";
        }
        try {
            mService.consumePurchase(3, BraveFrontier.getActivity().getPackageName(), str3);
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    public boolean syncItemPricesAndPurchases() {
        return new SyncItemPricesAndPurchases().runOrWaitRequest();
    }

    public boolean requestPurchase(String str, String str2, String str3) {
        return new RequestPurchase(str, str2, str3).runOrWaitRequest();
    }

    public static CurrencyValue parseCurrency(String str) {
        char charAt;
        if (str == null || str.length() == 0) {
            throw new NumberFormatException("String is null or empty");
        }
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            char charAt2 = str.charAt(i);
            if (Character.isWhitespace(charAt2) || (charAt2 >= '0' && charAt2 <= '9')) {
                break;
            }
            i2++;
            i++;
        }
        String str2 = "";
        String substring = i2 > 0 ? str.substring(0, i2) : "";
        while (i < str.length() && Character.isWhitespace(str.charAt(i))) {
            i++;
        }
        int i3 = i;
        int i4 = 0;
        int i5 = 0;
        while (i3 < str.length() && (((charAt = str.charAt(i3)) >= '0' && charAt <= '9') || charAt == '.' || charAt == ',')) {
            i4++;
            if (charAt >= '0' && charAt <= '9') {
                i5++;
            }
            i3++;
        }
        if (i5 == 0) {
            throw new NumberFormatException("No number");
        }
        int i6 = i4 - 1;
        for (int i7 = i6; i7 >= i4 - 3 && i7 >= 0; i7--) {
            int i8 = i + i7;
            char charAt3 = str.charAt(i8);
            if (charAt3 == '.' || charAt3 == ',') {
                int i9 = i8 + 1;
                str2 = str.substring(i9, (i6 - i7) + i9);
                i4 = i7;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i10 = 0; i10 < i4; i10++) {
            char charAt4 = str.charAt(i + i10);
            if (charAt4 >= '0' && charAt4 <= '9') {
                sb.append(charAt4);
            }
        }
        String sb2 = sb.toString();
        if (i2 == 0) {
            while (i3 < str.length() && Character.isWhitespace(str.charAt(i3))) {
                i3++;
            }
            int i11 = i3;
            while (i11 < str.length()) {
                char charAt5 = str.charAt(i11);
                if (Character.isWhitespace(charAt5) || (charAt5 >= '0' && charAt5 <= '9')) {
                    break;
                }
                i2++;
                i11++;
            }
            if (i2 > 0) {
                substring = str.substring(i3, i2 + i3);
            }
            i3 = i11;
        }
        if (i3 != str.length()) {
            throw new NumberFormatException("Invalid currency string");
        }
        CurrencyValue currencyValue = new CurrencyValue();
        currencyValue.setCurrency(substring);
        currencyValue.setDecimalPart(str2);
        currencyValue.setIntegerPart(sb2);
        return currencyValue;
    }
}