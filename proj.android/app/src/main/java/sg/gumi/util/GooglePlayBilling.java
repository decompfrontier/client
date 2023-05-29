package sg.gumi.util;

import android.util.Log;
import com.android.billingclient.api.*;
import sg.gumi.bravefrontier.BraveFrontier;

import java.util.ArrayList;
import java.util.List;

//import com.soomla.store.domain.data.VirtualCurrencyPack;
//import com.soomla.store.data.StoreInfo;
//import com.soomla.store.StoreController;

public class GooglePlayBilling implements PurchasesUpdatedListener {

    static class SkuDetailsTask implements SkuDetailsResponseListener {
        final GooglePlayBilling billing;

        SkuDetailsTask(GooglePlayBilling billing) {
            super();
            this.billing = billing;
        }

        public void onSkuDetailsResponse(BillingResult result, List products) {
            if (products != null) {
                for (Object p: products)
                {
                    SkuDetails skuDetails = (SkuDetails)p;
                    String sku = skuDetails.getSku();
                    String price = skuDetails.getPrice();

                    /*for (VirtualCurrencyPack pack: StoreInfo.getCurrencyPacks())
                    {
                        if (pack.getProductId().equals(sku)) {
                            pack.setPriceString(price);
                            pack.setSkuDetails(skuDetails);
                            break;
                        }
                    }*/
                }
            }
        }
    }

    static class ConsumeTask implements ConsumeResponseListener {
        final GooglePlayBilling billing;
        final String prodId;
        final String signature;
        final String signedData;

        ConsumeTask(GooglePlayBilling billing, String signedData, String signature, String prodId) {
            super();
            this.billing = billing;
            this.signedData = signedData;
            this.signature = signature;
            this.prodId = prodId;
        }

        public void onConsumeResponse(BillingResult result, String msg) {
            Log.d("DEREKT", "onConsumeResponse Purchases consumed");
            //com.soomla.store.StoreController.getInstance().onPurchaseStateChange(signedData, signature, prodId);
        }
    }

    class SetupTask implements BillingClientStateListener {
        final GooglePlayBilling billing;

        SetupTask(sg.gumi.util.GooglePlayBilling billing) {
            super();
            this.billing = billing;

        }

        public void onBillingServiceDisconnected() {
            Log.w("GooglePlay Billing Service", "Billing service disconnected v3");
        }

        public void onBillingSetupFinished(BillingResult result) {
            try {
                GooglePlayBilling.runPendingRequests(billing);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    final private static String TAG = "GooglePlay Billing Service";
    private BillingClient billingClient;
    
    public GooglePlayBilling() {
    }
    
    static void runPendingRequests(GooglePlayBilling billing) {
        billing.runPendingRequests();
    }

    private boolean isCanRunRequests() {
        if (BFConfig.PLATFORM != BFConfig.PLATFORM_AMAZON) {
            return false;
        }
        return true;
    }
    
    private void runPendingRequests() {
        /* TODO: MISSING!!! */
    }
    
    public void OldRequestPurchase() {
    }
    
    public void OldSyncItemPricesAndPurchasesThread() {
    }
    
    public void RequestPurchase(String productId) {
        /*VirtualCurrencyPack pack;
        try {
            pack = StoreInfo.getPackByGoogleProductId(productId);
        } catch(Exception ex) {
            ex.printStackTrace();
            pack = null;
        }
        SkuDetails sku = pack.getSkuDetails();
        BillingFlowParams params= BillingFlowParams.newBuilder().setSkuDetails(sku).build();
        billingClient.launchBillingFlow(BraveFrontier.getActivity(), params);*/
    }
    
    public void SyncItemPricesAndPurchasesThread() {

        ArrayList<String> products = new ArrayList<>();

        /*for (VirtualCurrencyPack p: StoreInfo.getCurrencyPacks())
        {
            products.add(p.getProductId());
        }*/

        SkuDetailsParams.Builder sku = SkuDetailsParams.newBuilder();
        sku.setSkusList(products).setType("inapp");
        this.billingClient.querySkuDetailsAsync(sku.build(), new SkuDetailsTask(this));
    }
    
    public boolean bindToMarketBillingService() {
        return false;
    }
    
    public void initialize() {
        billingClient = BillingClient.newBuilder(BraveFrontier.getActivity()).setListener(this).enablePendingPurchases().build();;
        billingClient.startConnection(new SetupTask(this));
    }
    
    public void onPurchasesUpdated(BillingResult result, List purchases) {
        if (result == null) {
            Log.wtf(TAG, "onPurchasesUpdated: null BillingResult");
            return;
        }
        int code = result.getResponseCode();
        String debugMsg = result.getDebugMessage();
        Log.d(TAG, "onPurchasesUpdated: $responseCode $debugMessage");
        if (code != 0) {
            //com.soomla.store.StoreController.getInstance().onPurchaseStateChange("", "", "");
            android.util.Log.e("onActivityResult", "onActivityResult payment attempt fail! Response code: " +
                    code +
                    " msg:" +
                    debugMsg);
            return;
        }

        for (Object p: purchases)
        {
            Purchase purchase = (Purchase)p;
            String token = purchase.getPurchaseToken();
            String json = purchase.getOriginalJson();
            String sig = purchase.getSignature();
            String sku = "";
            /* update for newer billing api */
            if (purchase.getSkus().size() > 0)
                sku = purchase.getSkus().get(0);

            ConsumeParams params = ConsumeParams.newBuilder().setPurchaseToken(token).build();
            this.billingClient.consumeAsync(params, new ConsumeTask(this, json, sig, sku));
        }
    }
    
    public boolean runOrWaitRequest() {
        return false;
    }
}
