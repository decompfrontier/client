package sg.gumi.bravefrontier;

import android.content.Intent;
import android.os.Bundle;
import org.cocos2dx.lib.Cocos2dxActivity;
import static android.os.Build.*;

abstract public class BaseGameActivity extends Cocos2dxActivity {
    final public static int CLIENT_ALL = 5;
    final public static int CLIENT_APPSTATE = 4;
    final public static int CLIENT_GAMES = 1;

    private String[] mAdditionalScopes;
    protected boolean mDebugLog;
    protected String mDebugTag;
    protected GameService mHelper = null;
    protected int mRequestedClients;
    
    public BaseGameActivity() {
        mRequestedClients = CLIENT_GAMES;
        mDebugTag = "BaseGameActivity";
        mDebugLog = true;
    }
    
    protected void beginUserInitiatedSignIn() {
        mHelper.beginUserInitiatedSignIn();
    }
    
    protected void enableDebugLog(boolean enable, String tag) {
        mDebugLog = enable;
        mDebugTag = tag;
        if (mHelper != null) {
            mHelper.enableDebugLog(enable, tag);
        }
    }
    
    public GameService getGameService() {
        return mHelper;
    }
    
    protected String getInvitationId() {
        return mHelper.getInvitationId();
    }
    
    protected String getScopes() {
        return mHelper.getScopes();
    }
    
    protected String[] getScopesArray() {
        return mHelper.getScopesArray();
    }
    
    protected sg.gumi.bravefrontier.GameService$SignInFailureReason getSignInError() {
        return mHelper.getSignInError();
    }
    
    protected boolean hasSignInError() {
        return mHelper.hasSignInError();
    }
    
    public void hideSystemUI() {
        if (VERSION.SDK_INT < 19) {
            return;
        }
        getWindow().getDecorView().setSystemUiVisibility(5894);
    }
    
    protected boolean isSignedIn() {
        return mHelper.isSignedIn();
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mHelper.onActivityResult(requestCode, resultCode, intent);
    }
    
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mHelper = GameService.createService(this);
        if (mDebugLog) {
            mHelper.enableDebugLog(true, mDebugTag);
        }

        mHelper.setup(mHelper.getGameHelperListener(), mRequestedClients, mAdditionalScopes);
    }
    
    protected void onPause() {
        super.onPause();
        mHelper.onPause(this);
    }
    
    protected void onResume() {
        super.onResume();
        mHelper.onResume(this);
    }
    
    protected void onStart() {
        super.onStart();
        mHelper.onStart(this);
    }
    
    protected void onStop() {
        super.onStop();
        mHelper.onStop(this);
    }
    
    public void onWindowFocusChanged(boolean changed) {
        super.onWindowFocusChanged(changed);
        if (changed) {
            hideSystemUI();
        }
    }
    
    protected void reconnectClients(int client) {
        mHelper.reconnectClients(client);
    }
    
    protected void setRequestedClients(int reqClients, String[] additionalScopes) {
        mRequestedClients = reqClients;
        mAdditionalScopes = additionalScopes;
    }
    
    protected void showAlert(String msg) {
        mHelper.showAlert(msg);
    }
    
    protected void showAlert(String title, String message) {
        mHelper.showAlert(title, message);
    }
    
    protected void signOut() {
        mHelper.signOut();
    }
}
