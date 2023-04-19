package sg.gumi.bravefrontier;

class BraveFrontier$1 implements Runnable {
    final sg.gumi.bravefrontier.BraveFrontier this$0;
    
    BraveFrontier$1(sg.gumi.bravefrontier.BraveFrontier a) {
        this.this$0 = a;
        super();
    }
    
    public void run() {
        String s = android.provider.Settings$Secure.getString(((android.app.Activity)this.this$0).getContentResolver(), "android_id");
        try {
            sg.gumi.bravefrontier.BraveFrontier.access$002(com.google.android.gms.ads.identifier.AdvertisingIdClient.getAdvertisingIdInfo(((android.app.Activity)this.this$0).getApplicationContext()).getId());
        } catch(Exception ignoredException) {
            sg.gumi.bravefrontier.BraveFrontier.access$002(s);
        }
    }
}
