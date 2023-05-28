package sg.gumi.bravefrontier;

class BraveFrontier$3$1 extends java.util.TimerTask {
    final sg.gumi.bravefrontier.BraveFrontier$3 this$1;
    
    BraveFrontier$3$1(sg.gumi.bravefrontier.BraveFrontier$3 a) {
        this.this$1 = a;
        super();
    }
    
    public void run() {
        org.cocos2dx.lib.Cocos2dxHelper.setBackgroundMusicVolume(sg.gumi.bravefrontier.BraveFrontier.access$200(this.this$1.this$0));
        org.cocos2dx.lib.Cocos2dxHelper.setEffectsVolume(sg.gumi.bravefrontier.BraveFrontier.access$300(this.this$1.this$0));
    }
}
