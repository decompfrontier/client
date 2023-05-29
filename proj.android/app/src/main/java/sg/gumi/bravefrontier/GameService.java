package sg.gumi.bravefrontier;

import android.app.Activity;
import static sg.gumi.util.BFConfig.*;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;

abstract public class GameService {

    public interface GameHelperListener {
        void onSignInFailed();


        void onSignInSucceeded();


        void onSignOutSucceeded();
    }

    public static class SignInFailureReason {
        final public static int NO_ACTIVITY_RESULT_CODE = -100;
        private int mActivityResultCode;
        private int mServiceErrorCode;

        public SignInFailureReason(int errorCode) {
            this(errorCode, -100);
        }

        public SignInFailureReason(int errorCode, int resultCode) {
            mServiceErrorCode = 0;
            mActivityResultCode = NO_ACTIVITY_RESULT_CODE;
            mServiceErrorCode = errorCode;
            mActivityResultCode = resultCode;
        }

        final public int getActivityResultCode() {
            return mActivityResultCode;
        }

        final public int getServiceErrorCode() {
            return mServiceErrorCode;
        }
    }

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
    
    
    abstract public GameService.GameHelperListener getGameHelperListener();
    
    
    abstract public Object getGamesClient();
    
    
    abstract public String getInvitationId();
    
    
    public void getLeaderBoardScore(String leaderboard) {
    }
    
    abstract public String getScopes();
    
    
    abstract public String[] getScopesArray();
    
    
    abstract public int getSignInCancellations();
    
    
    abstract public SignInFailureReason getSignInError();
    
    
    public void googleAnalyticsSendScreenView(String screenName) {
        if (mTracker != null) {
            try {
                mTracker.setScreenName(screenName);
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
    
    public void googleAnalyticsTrackPurchase(String transactionId, String name, String id, String category, double price, long quantity, String cu) {
        if (this.mTracker != null) {
            try {
                Product product = new Product().setId(id).setName(name).setCategory(category).setPrice(price).setQuantity((int)quantity);
                ProductAction action = new ProductAction("purchase").setTransactionId(transactionId);
                HitBuilders.ScreenViewBuilder screenView = new HitBuilders.ScreenViewBuilder().addProduct(product);
                screenView.setProductAction(action);
                mTracker.setScreenName("transaction");
                mTracker.set("&cu", cu);
                mTracker.send(screenView.build());
            } catch(Throwable ignoredException) {
            }
        }
    }
    
    abstract public boolean hasSignInError();
    
    
    public void initGoogleAnalytics(Activity activity) {
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
    
    abstract public void onActivityResult(int requestId, int responseId, Intent intent);
    
    
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
    
    abstract public void setup(GameService.GameHelperListener listener, int reqClients, String[] args);
    
    
    abstract public void showAchievements();
    
    
    final public void showAlert(String msg) {
        new android.app.AlertDialog.Builder(getActivity()).setMessage(msg).setNeutralButton(android.R.string.ok, null).create().show();
    }
    
    final public void showAlert(String title, String message) {
        new AlertDialog.Builder(getActivity()).setTitle(title).setMessage(message).setNeutralButton(android.R.string.ok, null).create().show();
    }
    
    public void showPlayPhoneButton(boolean visible) {
    }
    
    abstract public void signOut();
    
    
    public void submitLeaderBoardScore(String leaderboard, int score) {
    }
    
    abstract public void unlockAchievement(String id);
    
    
    abstract public void updateLeaderboard(int value, String name);
}
