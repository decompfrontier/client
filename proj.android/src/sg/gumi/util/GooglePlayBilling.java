package sg.gumi.util;

public class GooglePlayBilling implements com.android.billingclient.api.PurchasesUpdatedListener {
    final private static String TAG = "GooglePlay Billing Service";
    private com.android.billingclient.api.BillingClient billingClient;
    
    public GooglePlayBilling() {
    }
    
    static void access$000(sg.gumi.util.GooglePlayBilling a) {
        a.runPendingRequests();
    }
    
    private boolean isCanRunRequests() {
        if (sg.gumi.util.BFConfig.PLATFORM != sg.gumi.util.BFConfig.PLATFORM_AMAZON) {
            return false;
        }
        return true;
    }
    
    private void runPendingRequests() {
        /*monenter(this)*/;
        /*monexit(this)*/;
    }
    
    public void OldRequestPurchase() {
    }
    
    public void OldSyncItemPricesAndPurchasesThread() {
    }
    
    public void RequestPurchase(String s) {
        com.soomla.store.domain.data.VirtualCurrencyPack a = null;
        try {
            a = com.soomla.store.data.StoreInfo.getPackByGoogleProductId(s);
        } catch(Exception a0) {
            a0.printStackTrace();
            a = null;
        }
        com.android.billingclient.api.SkuDetails a1 = a.getSkuDetails();
        com.android.billingclient.api.BillingFlowParams a2 = com.android.billingclient.api.BillingFlowParams.newBuilder().setSkuDetails(a1).build();
        this.billingClient.launchBillingFlow((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity(), a2);
    }
    
    public void SyncItemPricesAndPurchasesThread() {
        java.util.ArrayList a = new java.util.ArrayList();
        Object a0 = com.soomla.store.data.StoreInfo.getCurrencyPacks().iterator();
        while(((java.util.Iterator)a0).hasNext()) {
            ((java.util.List)(Object)a).add((Object)((com.soomla.store.domain.data.VirtualCurrencyPack)((java.util.Iterator)a0).next()).getProductId());
        }
        com.android.billingclient.api.SkuDetailsParams$Builder a1 = com.android.billingclient.api.SkuDetailsParams.newBuilder();
        a1.setSkusList((java.util.List)(Object)a).setType("inapp");
        this.billingClient.querySkuDetailsAsync(a1.build(), (com.android.billingclient.api.SkuDetailsResponseListener)(Object)new sg.gumi.util.GooglePlayBilling$3(this));
    }
    
    public boolean bindToMarketBillingService() {
        return false;
    }
    
    public void initialize() {
        com.android.billingclient.api.BillingClient a = com.android.billingclient.api.BillingClient.newBuilder((android.content.Context)sg.gumi.bravefrontier.BraveFrontier.getActivity()).setListener((com.android.billingclient.api.PurchasesUpdatedListener)(Object)this).enablePendingPurchases().build();
        this.billingClient = a;
        a.startConnection((com.android.billingclient.api.BillingClientStateListener)(Object)new sg.gumi.util.GooglePlayBilling$1(this));
    }
    
    public void onPurchasesUpdated(com.android.billingclient.api.BillingResult a, java.util.List a0) {
        if (a == null) {
            android.util.Log.wtf("GooglePlay Billing Service", "onPurchasesUpdated: null BillingResult");
            return;
        }
        int i = a.getResponseCode();
        String s = a.getDebugMessage();
        android.util.Log.d("GooglePlay Billing Service", "onPurchasesUpdated: $responseCode $debugMessage");
        if (i != 0) {
            com.soomla.store.StoreController.getInstance().onPurchaseStateChange("", "", "");
            StringBuilder a1 = new StringBuilder();
            a1.append("onActivityResult payment attempt fail! Response code: ");
            a1.append(i);
            a1.append(" msg:");
            a1.append(s);
            android.util.Log.e("onActivityResult", a1.toString());
            return;
        }
        Object a2 = a0.iterator();
        while(((java.util.Iterator)a2).hasNext()) {
            com.android.billingclient.api.Purchase a3 = (com.android.billingclient.api.Purchase)((java.util.Iterator)a2).next();
            String s0 = a3.getPurchaseToken();
            String s1 = a3.getOriginalJson();
            String s2 = a3.getSignature();
            String s3 = a3.getSku();
            com.android.billingclient.api.ConsumeParams a4 = com.android.billingclient.api.ConsumeParams.newBuilder().setPurchaseToken(s0).build();
            sg.gumi.util.GooglePlayBilling$2 a5 = new sg.gumi.util.GooglePlayBilling$2(this, s1, s2, s3);
            this.billingClient.consumeAsync(a4, (com.android.billingclient.api.ConsumeResponseListener)(Object)a5);
        }
    }
    
    public boolean runOrWaitRequest() {
        return false;
    }
}
