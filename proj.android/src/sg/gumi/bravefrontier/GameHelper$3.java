package sg.gumi.bravefrontier;

class GameHelper$3 implements Runnable {
    final sg.gumi.bravefrontier.GameHelper this$0;
    
    GameHelper$3(sg.gumi.bravefrontier.GameHelper a) {
        this.this$0 = a;
        super();
    }
    
    public void run() {
        Object a = null;
        android.content.SharedPreferences a0 = sg.gumi.bravefrontier.BraveFrontier.getAppContext().getSharedPreferences("GOOGLE_PLAY", 0);
        android.content.SharedPreferences$Editor a1 = a0.edit();
        String s = a0.getString("ACH", "");
        if (s.equals((Object)"")) {
            a = a1;
        } else {
            String[] a2 = s.split(",");
            a = a1;
            int i = 0;
            for(; i < a2.length; i = i + 1) {
                sg.gumi.bravefrontier.BraveFrontier.unlockGPGSAchievement(a2[i]);
            }
        }
        ((android.content.SharedPreferences$Editor)a).remove("ACH");
        ((android.content.SharedPreferences$Editor)a).commit();
        if (org.cocos2dx.lib.Cocos2dxHelper.isNativeLibraryLoaded()) {
            sg.gumi.bravefrontier.BraveFrontier.onGPGSSignInSucceeded();
        }
    }
}
