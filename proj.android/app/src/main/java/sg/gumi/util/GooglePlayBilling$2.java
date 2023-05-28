package sg.gumi.util;

class GooglePlayBilling$2 implements com.android.billingclient.api.ConsumeResponseListener {
    final sg.gumi.util.GooglePlayBilling this$0;
    final String val$prodId;
    final String val$signature;
    final String val$signedData;
    
    GooglePlayBilling$2(sg.gumi.util.GooglePlayBilling a, String s, String s0, String s1) {
        this.this$0 = a;
        this.val$signedData = s;
        this.val$signature = s0;
        this.val$prodId = s1;
        super();
    }
    
    public void onConsumeResponse(com.android.billingclient.api.BillingResult a, String s) {
        android.util.Log.d("DEREKT", "onConsumeResponse Purchases consumed");
        com.soomla.store.StoreController.getInstance().onPurchaseStateChange(this.val$signedData, this.val$signature, this.val$prodId);
    }
}
