package sg.gumi.bravefrontier;

class BraveFrontier$4 implements android.content.DialogInterface$OnClickListener {
    final sg.gumi.bravefrontier.BraveFrontier this$0;
    
    BraveFrontier$4(sg.gumi.bravefrontier.BraveFrontier a) {
        this.this$0 = a;
        super();
    }
    
    public void onClick(android.content.DialogInterface a, int i) {
        if (i == -2) {
            try {
                ((android.app.Activity)this.this$0).startActivity(new android.content.Intent("android.settings.APPLICATION_SETTINGS"));
            } catch(Throwable ignoredException) {
            }
        }
        sg.gumi.bravefrontier.BraveFrontierJNI.appExit();
    }
}
