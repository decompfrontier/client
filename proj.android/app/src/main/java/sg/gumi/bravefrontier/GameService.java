package sg.gumi.bravefrontier;

import android.app.Activity;
import static sg.gumi.util.BFConfig.*;

import android.content.Intent;
import android.util.Log;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

abstract public class GameService {
    final public static int CLIENT_ALL = 5;
    final public static int CLIENT_APPSTATE = 4;
    final public static int CLIENT_GAMES = 1;
    final public static int CLIENT_NONE = 0;

    final protected static int GA_DISPATCH_PERIOD = 30;
    final protected static boolean GA_IS_DRY_RUN = false;
	
    Activity mActivity;
    boolean mDebugLog;
    String mDebugTag;
    protected Tracker mTracker;
    String mUnknownErrorMessage;
    
    public GameService(Activity activity) {
        mTracker = null;
        mDebugLog = false;
        mDebugTag = "GameHelper";
        mUnknownErrorMessage = "Unknown game error";
        mActivity = activity;
    }
    
    public static GameService createService(Activity activity)
    {

        if (PLATFORM == PLATFORM_AMAZON)
        {
            return null;
        }
        else if (PLATFORM == PLATFORM_SAMSUNG)
        {
            return null;
        }
        else if (PLATFORM == PLATFORM_GOOGLE)
        {
            return new GameHelper(activity);
        }

        return null;

    }
    
    abstract public void beginUserInitiatedSignIn();
    
    
    final protected void debugLog(String msg) {
        if (mDebugLog) {
            Log.d(mDebugTag, msg);
        }
    }
    
    final public void enableDebugLog(boolean enable, String tag) {
        mDebugLog = enable;
        mDebugTag = tag;
    }
    
    final protected Activity getActivity() {
        return mActivity;
    }
    
    abstract public Object getAppStateClient();
    
    
    abstract public GameService$GameHelperListener getGameHelperListener();
    
    
    abstract public Object getGamesClient();
    
    
    abstract public String getInvitationId();
    
    
    public void getLeaderBoardScore(String leaderboard) {
    }
    
    abstract public String getScopes();
    
    
    abstract public String[] getScopesArray();
    
    
    abstract public int getSignInCancellations();
    
    
    abstract public GameService$SignInFailureReason getSignInError();
    
    
    public void googleAnalyticsSendScreenView(String s) {
        if (mTracker != null) {
            try {
                mTracker.setScreenName(s);
                mTracker.send((new HitBuilders.ScreenViewBuilder()).build());
            } catch(Throwable ignoredException) {
            }
        }
    }
    
    public void googleAnalyticsSetUserID(String userid) {
        if (mTracker != null) {
            try {
                mTracker.set("&uid", userid);
            } catch(Throwable ignoredException) {
            }
        }
    }
    
    public void googleAnalyticsTrackEvent(String category, String action, String label, long value) {
        if (mTracker != null) {
            try {
                mTracker.send((new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).setValue(value)).build());
            } catch(Throwable ignoredException) {
            }
        }
    }
    
    public void googleAnalyticsTrackPurchase(String s, String s0, String s1, String s2, double d, long j, String s3) {
        if (this.mTracker != null) {
            try {
                com.google.android.gms.analytics.ecommerce.Product a = new com.google.android.gms.analytics.ecommerce.Product().setId(s1).setName(s0).setCategory(s2).setPrice(d).setQuantity((int)j);
                com.google.android.gms.analytics.ecommerce.ProductAction a0 = new com.google.android.gms.analytics.ecommerce.ProductAction("purchase").setTransactionId(s);
                com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder a1 = (com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder)((com.google.android.gms.analytics.HitBuilders$HitBuilder)new com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder()).addProduct(a);
                ((com.google.android.gms.analytics.HitBuilders$HitBuilder)a1).setProductAction(a0);
                this.mTracker.setScreenName("transaction");
                this.mTracker.set("&cu", s3);
                this.mTracker.send(((com.google.android.gms.analytics.HitBuilders$HitBuilder)a1).build());
            } catch(Throwable ignoredException) {
            }
        }
    }
    
    abstract public boolean hasSignInError();
    
    
    public void initGoogleAnalytics(Activity a) {
    }
    
    public void initializeAmazonPhoneManager() {
    }
    
    public void initializePSGN() {
    }
    
    abstract public boolean isSignedIn();
    
    
    void logError(String err) {
        if (mDebugLog) {
            Log.e(mDebugTag, err);
        }
    }
    
    void logWarn(String warn) {
        if (mDebugLog) {
            Log.w(mDebugTag, warn);
        }
    }
    
    abstract public void onActivityResult(int arg, int arg0, Intent intent);
    
    
    abstract public void onPause(Activity activity);
    
    
    abstract public void onResume(Activity activity);
    
    
    abstract public void onStart(Activity activity);
    
    
    abstract public void onStop(Activity activity);
    
    
    abstract public void reconnectClients(int arg);
    
    
    public void setAndroidIds(Activity activity, Object a0) {
    }
    
    public void setGoogleAdvertisingId(Activity activity, Object a0) {
    }
    
    final public void setUnknownErrorMessage(String err) {
        debugLog("Setting unknown error message to: " +
                err);
        mUnknownErrorMessage = err;
    }
    
    abstract public void setup(GameService$GameHelperListener arg, int arg0, String[] arg1);
    
    
    abstract public void showAchievements();
    
    
    final public void showAlert(String msg) {
        new android.app.AlertDialog.Builder(this.getActivity()).setMessage(msg).setNeutralButton(17039370, null).create().show();
    }
    
    final public void showAlert(String title, String message) {
        new android.app.AlertDialog.Builder(this.getActivity()).setTitle(title).setMessage(message).setNeutralButton(17039370, null).create().show();
    }
    
    public void showPlayPhoneButton(boolean visible) {
    }
    
    abstract public void signOut();
    
    
    public void submitLeaderBoardScore(String leaderboard, int score) {
    }
    
    abstract public void unlockAchievement(String id);
    
    
    abstract public void updateLeaderboard(int arg, String arg0);
}
