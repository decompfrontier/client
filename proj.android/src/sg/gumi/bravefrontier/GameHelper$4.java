package sg.gumi.bravefrontier;

class GameHelper$4 implements Runnable {
    final sg.gumi.bravefrontier.GameHelper this$0;
    
    GameHelper$4(sg.gumi.bravefrontier.GameHelper a) {
        this.this$0 = a;
        super();
    }
    
    public void run() {
        if (org.cocos2dx.lib.Cocos2dxHelper.isNativeLibraryLoaded()) {
            sg.gumi.bravefrontier.BraveFrontier.onGPGSSignInFailed();
        }
    }
}
