package sg.gumi.bravefrontier;

class BraveFrontierJNI$7$2 implements android.content.DialogInterface$OnClickListener {
    final sg.gumi.bravefrontier.BraveFrontierJNI$7 this$0;
    
    BraveFrontierJNI$7$2(sg.gumi.bravefrontier.BraveFrontierJNI$7 a) {
        this.this$0 = a;
        super();
    }
    
    public void onClick(android.content.DialogInterface a, int i) {
        sg.gumi.bravefrontier.BraveFrontierJNI.nativeRateThisAppPopupCallback(1);
    }
}
