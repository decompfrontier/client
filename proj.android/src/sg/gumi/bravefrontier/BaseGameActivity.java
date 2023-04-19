package sg.gumi.bravefrontier;

abstract public class BaseGameActivity extends org.cocos2dx.lib.Cocos2dxActivity {
    final public static int CLIENT_ALL = 5;
    final public static int CLIENT_APPSTATE = 4;
    final public static int CLIENT_GAMES = 1;
    private String[] mAdditionalScopes;
    protected boolean mDebugLog;
    protected String mDebugTag;
    protected sg.gumi.bravefrontier.GameService mHelper;
    protected int mRequestedClients;
    
    public BaseGameActivity() {
        this.mRequestedClients = 1;
        this.mDebugTag = "BaseGameActivity";
        this.mDebugLog = true;
    }
    
    protected void beginUserInitiatedSignIn() {
        this.mHelper.beginUserInitiatedSignIn();
    }
    
    protected void enableDebugLog(boolean b, String s) {
        this.mDebugLog = true;
        this.mDebugTag = s;
        sg.gumi.bravefrontier.GameService a = this.mHelper;
        if (a != null) {
            a.enableDebugLog(b, s);
        }
    }
    
    public sg.gumi.bravefrontier.GameService getGameService() {
        return this.mHelper;
    }
    
    protected String getInvitationId() {
        return this.mHelper.getInvitationId();
    }
    
    protected String getScopes() {
        return this.mHelper.getScopes();
    }
    
    protected String[] getScopesArray() {
        return this.mHelper.getScopesArray();
    }
    
    protected sg.gumi.bravefrontier.GameService$SignInFailureReason getSignInError() {
        return this.mHelper.getSignInError();
    }
    
    protected boolean hasSignInError() {
        return this.mHelper.hasSignInError();
    }
    
    public void hideSystemUI() {
        if (android.os.Build$VERSION.SDK_INT < 19) {
            return;
        }
        ((android.app.Activity)this).getWindow().getDecorView().setSystemUiVisibility(5894);
    }
    
    protected boolean isSignedIn() {
        return this.mHelper.isSignedIn();
    }
    
    public void onActivityResult(int i, int i0, android.content.Intent a) {
        ((android.app.Activity)this).onActivityResult(i, i0, a);
        this.mHelper.onActivityResult(i, i0, a);
    }
    
    protected void onCreate(android.os.Bundle a) {
        ((org.cocos2dx.lib.Cocos2dxActivity)this).onCreate(a);
        sg.gumi.bravefrontier.GameService a0 = sg.gumi.bravefrontier.GameService.createService((android.app.Activity)this);
        this.mHelper = a0;
        boolean b = this.mDebugLog;
        if (b) {
            a0.enableDebugLog(b, this.mDebugTag);
        }
        sg.gumi.bravefrontier.GameService a1 = this.mHelper;
        a1.setup(a1.getGameHelperListener(), this.mRequestedClients, this.mAdditionalScopes);
    }
    
    protected void onPause() {
        ((org.cocos2dx.lib.Cocos2dxActivity)this).onPause();
        this.mHelper.onPause((android.app.Activity)this);
    }
    
    protected void onResume() {
        ((org.cocos2dx.lib.Cocos2dxActivity)this).onResume();
        this.mHelper.onResume((android.app.Activity)this);
    }
    
    protected void onStart() {
        ((android.app.Activity)this).onStart();
        this.mHelper.onStart((android.app.Activity)this);
    }
    
    protected void onStop() {
        ((android.app.Activity)this).onStop();
        this.mHelper.onStop((android.app.Activity)this);
    }
    
    public void onWindowFocusChanged(boolean b) {
        ((android.app.Activity)this).onWindowFocusChanged(b);
        if (b) {
            this.hideSystemUI();
        }
    }
    
    protected void reconnectClients(int i) {
        this.mHelper.reconnectClients(i);
    }
    
    protected void setRequestedClients(int i, String[] a) {
        this.mRequestedClients = i;
        this.mAdditionalScopes = a;
    }
    
    protected void showAlert(String s) {
        this.mHelper.showAlert(s);
    }
    
    protected void showAlert(String s, String s0) {
        this.mHelper.showAlert(s, s0);
    }
    
    protected void signOut() {
        this.mHelper.signOut();
    }
}
