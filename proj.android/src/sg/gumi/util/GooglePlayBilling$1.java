package sg.gumi.util;

class GooglePlayBilling$1 implements com.android.billingclient.api.BillingClientStateListener {
    final sg.gumi.util.GooglePlayBilling this$0;
    
    GooglePlayBilling$1(sg.gumi.util.GooglePlayBilling a) {
        this.this$0 = a;
        super();
    }
    
    public void onBillingServiceDisconnected() {
        android.util.Log.w("GooglePlay Billing Service", "Billing service disconnected v3");
    }
    
    public void onBillingSetupFinished(com.android.billingclient.api.BillingResult a) {
        try {
            sg.gumi.util.GooglePlayBilling.access$000(this.this$0);
        } catch(Exception a0) {
            a0.printStackTrace();
        }
    }
}
