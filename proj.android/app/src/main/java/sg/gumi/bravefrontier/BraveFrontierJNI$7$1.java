package sg.gumi.bravefrontier;

class BraveFrontierJNI$7$1 implements android.content.DialogInterface$OnClickListener {
    final sg.gumi.bravefrontier.BraveFrontierJNI$7 this$0;
    final android.content.Context val$mContext;
    
    BraveFrontierJNI$7$1(sg.gumi.bravefrontier.BraveFrontierJNI$7 a, android.content.Context a0) {
        this.this$0 = a;
        this.val$mContext = a0;
        super();
    }
    
    public void onClick(android.content.DialogInterface a, int i) {
        String s = null;
        if (sg.gumi.util.BFConfig.PLATFORM != sg.gumi.util.BFConfig.PLATFORM_AMAZON) {
            if (sg.gumi.util.BFConfig.PLATFORM != sg.gumi.util.BFConfig.PLATFORM_SAMSUNG) {
                StringBuilder a0 = new StringBuilder();
                a0.append("market://details?id=");
                a0.append(this.val$mContext.getPackageName());
                s = a0.toString();
            } else {
                StringBuilder a1 = new StringBuilder();
                a1.append("samsungapps://ProductDetail/");
                a1.append(this.val$mContext.getPackageName());
                s = a1.toString();
            }
        } else {
            StringBuilder a2 = new StringBuilder();
            a2.append("amzn://apps/android?p=");
            a2.append(this.val$mContext.getPackageName());
            s = a2.toString();
        }
        android.util.Log.v("MARKET-URL", s);
        android.content.Intent a3 = new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse(s));
        ((android.app.Activity)sg.gumi.bravefrontier.BraveFrontierJNI.getActivity()).startActivity(a3);
        sg.gumi.bravefrontier.BraveFrontierJNI.nativeRateThisAppPopupCallback(0);
    }
}
