package sg.gumi.bravefrontier;

class BraveFrontier$3 extends android.telephony.PhoneStateListener {
    final sg.gumi.bravefrontier.BraveFrontier this$0;
    
    BraveFrontier$3(sg.gumi.bravefrontier.BraveFrontier a) {
        this.this$0 = a;
        super();
    }
    
    public void onCallStateChanged(int i, String s) {
        if (i != 0) {
            if (i != 1) {
                sg.gumi.bravefrontier.BraveFrontier.access$202(this.this$0, org.cocos2dx.lib.Cocos2dxHelper.getBackgroundMusicVolume());
                sg.gumi.bravefrontier.BraveFrontier.access$302(this.this$0, org.cocos2dx.lib.Cocos2dxHelper.getEffectsVolume());
                org.cocos2dx.lib.Cocos2dxHelper.setBackgroundMusicVolume(0.0f);
                org.cocos2dx.lib.Cocos2dxHelper.setEffectsVolume(0.0f);
                sg.gumi.bravefrontier.BraveFrontier.access$102(this.this$0, false);
            } else {
                sg.gumi.bravefrontier.BraveFrontier.access$102(this.this$0, false);
            }
        } else {
            if (!sg.gumi.bravefrontier.BraveFrontier.access$100(this.this$0)) {
                new java.util.Timer().schedule((java.util.TimerTask)new sg.gumi.bravefrontier.BraveFrontier$3$1(this), 300L);
            }
            sg.gumi.bravefrontier.BraveFrontier.access$102(this.this$0, true);
        }
        ((android.telephony.PhoneStateListener)this).onCallStateChanged(i, s);
    }
}
