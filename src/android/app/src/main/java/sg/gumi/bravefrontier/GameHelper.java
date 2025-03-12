package sg.gumi.bravefrontier;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnSuccessListener;
import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxHelper;
import java.security.MessageDigest;

//import com.google.android.gms.games.multiplayer.Invitation;

final public class GameHelper extends GameService implements GameService.GameHelperListener {

    final public static class GooglePlaySignInFailureReason extends GameService.SignInFailureReason {
        public GooglePlaySignInFailureReason(int errorCode) {
            super(errorCode, -100);
        }

        public GooglePlaySignInFailureReason(int errorCode, int resultCode) {
            super(errorCode, resultCode);
        }

        public String toString() {
            StringBuilder ret = new StringBuilder();
            ret.append("SignInFailureReason(serviceErrorCode:");
            ret.append(GameHelper.errorCodeToString(getServiceErrorCode()));
            if (this.getActivityResultCode() != -100) {
                ret.append(",activityResultCode:").append(GameHelper.activityResponseCodeToString(getActivityResultCode())).append(")");
            } else {
                ret.append(")");
            }

            return ret.toString();
        }
    }

    static class SignInFailureTask implements Runnable {
        final GameHelper helper;

        SignInFailureTask(GameHelper helper) {
            super();
            this.helper = helper;
        }

        public void run() {
            helper.notifyListener(false);
        }
    }

    static class SignOutSuccessTask implements Runnable {
        final GameHelper helper;

        SignOutSuccessTask(GameHelper helper) {
            super();
            this.helper = helper;
        }

        public void run() {
            if (Cocos2dxHelper.isNativeLibraryLoaded()) {
                BraveFrontier.onGPGSSignOutSucceeded();
            }
        }
    }


    static class SignInSucceededTask implements Runnable {
        final GameHelper helper;

        SignInSucceededTask(GameHelper helper) {
            super();
            this.helper = helper;
        }

        public void run() {
            SharedPreferences preferences = BraveFrontier.getAppContext().getSharedPreferences(GOOGLE_PLAY_PREF, 0);
            SharedPreferences.Editor editor = preferences.edit();
            String pref = preferences.getString(GOOGLE_PLAY_ACHIEVEMENT_KEY, "");
            if (!pref.equals("")) {
                String[] split = pref.split(",");
                for(int i = 0; i < split.length; i++) {
                    BraveFrontier.unlockGPGSAchievement(split[i]);
                }
            }
            editor.remove(GOOGLE_PLAY_ACHIEVEMENT_KEY);
            editor.commit();
            if (Cocos2dxHelper.isNativeLibraryLoaded()) {
                BraveFrontier.onGPGSSignInSucceeded();
            }
        }
    }

    static class SignInFailedTask implements Runnable {
        final GameHelper helper;

        SignInFailedTask(GameHelper helper) {
            super();
            this.helper = helper;
        }

        public void run() {
            if (Cocos2dxHelper.isNativeLibraryLoaded()) {
                BraveFrontier.onGPGSSignInFailed();
            }
        }
    }

    final class GameHelperTask extends android.os.AsyncTask {
        final static int TASK_TYPE_SIGN_IN_FAILED = 2;
        final static int TASK_TYPE_SIGN_IN_SUCCEEDED = 1;
        final static int TASK_TYPE_SIGN_OUT_SUCCEEDED = 3;
        private int mAttempts;
        int mTaskType;
        final GameHelper helper;

        private GameHelperTask(GameHelper helper) {
            super();
            this.helper = helper;
            mAttempts = GameHelper.DEFAULT_MAX_SIGN_IN_ATTEMPTS;
        }

        GameHelperTask(GameHelper helper, GameHelper.SuccessListener successListener) {
            this(helper);
        }

        protected Object doInBackground(Object[] params) {
            while(true) {
                if (!isCancelled() && mAttempts > 0) {
                    try {
                        Thread.sleep(100L);
                    } catch(Throwable ignoredException) {
                    }
                    publishProgress();
                    continue;
                }
                return null;
            }
        }

        protected void onProgressUpdate(Object[] a) {
            try {
                if (mTaskType == TASK_TYPE_SIGN_IN_SUCCEEDED) {
                    GameHelper.callOnSignInSucceeded(helper);
                } else if (mTaskType == TASK_TYPE_SIGN_IN_FAILED) {
                    GameHelper.callOnSignInFailed(helper);
                } else if (mTaskType == TASK_TYPE_SIGN_OUT_SUCCEEDED) {
                    GameHelper.callOnSignOutSucceeded(helper);
                }
                mAttempts = 0;
                GameHelper.callEndTask(helper);
            } catch(Throwable ignoredException) {
                mAttempts--;
            }
        }
    }

    static class SuccessListener implements OnSuccessListener {
        final GameHelper helper;

        SuccessListener(GameHelper helper) {
            super();
            this.helper = helper;
        }

        public void onSuccess(Intent intent) {
            helper.mActivity.startActivityForResult(intent, GameHelper.getRcAchievementUi());
        }

        public void onSuccess(Object tResult) {
            onSuccess((Intent)tResult);
        }
    }

    final static int DEFAULT_MAX_SIGN_IN_ATTEMPTS = 3;
    final private static String[] FALLBACK_STRINGS = new String[] {
            "*Unknown error.",
            "*Failed to sign in. Please check your network connection and try again.",
            "*The application is incorrectly configured. Check that the package name and signing certificate match the client ID created in Developer Console. Also, if the application is not yet published, check that the account you are trying to sign in with is listed as a tester account. See logs for more information.",
            "*License check failed.",
    };

    final public static String GOOGLE_PLAY_ACHIEVEMENT_KEY = "ACH";
    final public static int GOOGLE_PLAY_ACHIEVEMENT_REQUEST_CODE = 5001;
    final public static String GOOGLE_PLAY_LEADERBOARD_KEY = "LBD";
    final public static int GOOGLE_PLAY_LEADERBOARD_REQUEST_CODE = 5002;
    final public static String GOOGLE_PLAY_PREF = "GOOGLE_PLAY";
    final private static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static int RC_ACHIEVEMENT_UI = 10001;
    final static int RC_RESOLVE = 9001;
    private static int RC_SIGN_IN = 10000;
    final static int RC_UNUSED = 9002;
    final private static int[] RES_IDS = new int[] {
            R.string.gamehelper_unknown_error, R.string.gamehelper_sign_in_failed, R.string.gamehelper_app_misconfigured, R.string.gamehelper_license_failed
    };

    final public static int R_APP_MISCONFIGURED = 2;
    final public static int R_LICENSE_FAILED = 3;
    final public static int R_SIGN_IN_FAILED = 1;
    final public static int R_UNKNOWN_ERROR = 0;
    final private String GAMEHELPER_SHARED_PREFS = "GAMEHELPER_SHARED_PREFS";
    final private String KEY_SIGN_IN_CANCELLATIONS = "KEY_SIGN_IN_CANCELLATIONS";
    Context mAppContext;
    Api.ApiOptions.NoOptions mAppStateApiOptions;
    boolean mConnectOnStart;
    private boolean mConnecting;
    ConnectionResult mConnectionResult;
    boolean mDebugLog;
    String mDebugTag;
    boolean mDisconnectedFromAchievementUI;
    boolean mExpectingResolution;
    GoogleSignInClient mGoogleSignInClient;
    Handler mHandler;
    //Invitation mInvitation;
    GameService.GameHelperListener mListener;
    int mMaxAutoSignInAttempts;
    GameHelper.GameHelperTask mPendingTask;
    int mRequestedClients;
    private boolean mSetupDone;
    boolean mShowErrorDialogs;
    boolean mSignInCancelled;

    SignInFailureReason mSignInFailureReason;

    boolean mUserInitiatedSignIn;

    public GameHelper(Activity activity) {
        super(activity);
        mSetupDone = false;
        mConnecting = false;
        mExpectingResolution = false;
        mSignInCancelled = false;
        mAppContext = null;
        mAppStateApiOptions = null;
        mGoogleSignInClient = null;
        mRequestedClients = 0;
        mConnectOnStart = false;
        mUserInitiatedSignIn = false;
        mConnectionResult = null;
        mSignInFailureReason = null;
        mShowErrorDialogs = true;
        mDebugLog = false;
        mDebugTag = "GameHelper";
        mListener = null;
        mPendingTask = null;
        mMaxAutoSignInAttempts = DEFAULT_MAX_SIGN_IN_ATTEMPTS;
        mDisconnectedFromAchievementUI = false;
        try {
            mAppContext = activity.getApplicationContext();
        } catch(Throwable ignoredException) {
        }
        mRequestedClients = 1;
        mDisconnectedFromAchievementUI = false;
        mHandler = new Handler();
    }

    static int getRcAchievementUi() {
        return RC_ACHIEVEMENT_UI;
    }

    static void callOnSignInSucceeded(GameHelper helper) {
        helper.onSignInSucceededTask();
    }

    static void callOnSignInFailed(GameHelper helper) {
        helper.onSignInFailedTask();
    }

    static void callOnSignOutSucceeded(GameHelper helper) {
        helper.onSignOutSucceededTask();
    }

    static void callEndTask(GameHelper helper) {
        helper.endTask();
    }

    static String activityResponseCodeToString(int result) {
        if (result == -1) {
            return "RESULT_OK";
        }
        if (result == 0) {
            return "RESULT_CANCELED";
        }
        switch(result) {
            case 10005: {
                return "RESULT_LEFT_ROOM";
            }
            case 10004: {
                return "RESULT_APP_MISCONFIGURED";
            }
            case 10003: {
                return "RESULT_LICENSE_FAILED";
            }
            case 10002: {
                return "SIGN_IN_FAILED";
            }
            case 10001: {
                return "RESULT_RECONNECT_REQUIRED";
            }
            default: {
                return String.valueOf(result);
            }
        }
    }

    static void byteToString(StringBuilder string, byte b) {
        int i = b;
        int i0 = b;
        if (b < 0) {
            i0 = i + 256;
        }
        int i1 = i0 / 16;
        int i2 = i0 % 16;
        string.append("0123456789ABCDEF".substring(i1, i1 + 1));
        string.append("0123456789ABCDEF".substring(i2, i2 + 1));
    }

    private synchronized void endTask() {
        if (mPendingTask != null) {
            try {
                mPendingTask.cancel(true);
            } catch(Throwable ignoredException) {
            }
            mPendingTask = null;
        }
    }

    static String errorCodeToString(int errorCode) {
        switch(errorCode) {
            case 11: {
                return "LICENSE_CHECK_FAILED(" +
                        errorCode +
                        ")";
            }
            case 10: {
                return "DEVELOPER_ERROR(" +
                        errorCode +
                        ")";
            }
            case 9: {
                return "SERVICE_INVALID(" +
                        errorCode +
                        ")";
            }
            case 8: {
                return "INTERNAL_ERROR(" +
                        errorCode +
                        ")";
            }
            case 7: {
                return "NETWORK_ERROR(" +
                        errorCode +
                        ")";
            }
            case 6: {
                return "RESOLUTION_REQUIRED(" +
                        errorCode +
                        ")";
            }
            case 5: {
                return "INVALID_ACCOUNT(" +
                        errorCode +
                        ")";
            }
            case 4: {
                return "SIGN_IN_REQUIRED(" +
                        errorCode +
                        ")";
            }
            case 3: {
                return "SERVICE_DISABLED(" +
                        errorCode +
                        ")";
            }
            case 2: {
                return "SERVICE_VERSION_UPDATE_REQUIRED(" +
                        errorCode +
                        ")";
            }
            case 1: {
                return "SERVICE_MISSING(" +
                        errorCode +
                        ")";
            }
            case 0: {
                return "SUCCESS(" +
                        errorCode +
                        ")";
            }
            default: {
                return "Unknown error code " +
                        errorCode;
            }
        }
    }

    static String getAppIdFromResource(Context context) {
        String appId;
        try {
            Resources res = context.getResources();
            appId = res.getString(res.getIdentifier("app_id", "string", context.getPackageName()));
        } catch(Exception ex) {
            ex.printStackTrace();
            return "??? (failed to retrieve APP ID)";
        }
        return appId;
    }

    static String getSHA1CertFingerprint(Context context) {

        try {
            try {
                Signature[] signatures = (Build.VERSION.SDK_INT < 28) ? context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures : context.getPackageManager().getPackageInfo(context.getPackageName(), 134217728).signingInfo.getApkContentsSigners();
                if (signatures.length == 0) {
                    return "ERROR: NO SIGNATURE.";
                }
                if (signatures.length > 1) {
                    return "ERROR: MULTIPLE SIGNATURES";
                }
                byte[] sha1 = MessageDigest.getInstance("SHA1").digest(signatures[0].toByteArray());
                StringBuilder sha1Str = new StringBuilder();
                int i = 0;
                for(; i < sha1.length; i = i + 1) {
                    if (i > 0) {
                        sha1Str.append(":");
                    }
                    GameHelper.byteToString(sha1Str, sha1[i]);
                }
                return sha1Str.toString();
            } catch(java.security.NoSuchAlgorithmException ex) {
                ex.printStackTrace();
                return "(ERROR: SHA1 algorithm not found)";
            }
        } catch(PackageManager.NameNotFoundException ex2) {
            ex2.printStackTrace();
            return "(ERROR: package not found)";
        }
    }

    static String getString(Context context, int resId, String s) {
        if (resId < 0) {
            resId = 0;
        }
        if (resId < RES_IDS.length) {
            resId = 0;
        }

        int id = RES_IDS[resId];
        try {
            return context.getString(id);
        } catch(Exception ex) {
            ex.printStackTrace();
            Log.w(s, "*** GameHelper could not found resource id #" +
                    id +
                    ". This probably happened because you included it as a stand-alone JAR. BaseGameUtils should be compiled as a LIBRARY PROJECT, so that it can access its resources. Using a fallback string.");
            return FALLBACK_STRINGS[resId];
        }
    }

    static Dialog makeSimpleDialog(Activity activity, String msg) {
        return new AlertDialog.Builder(activity).setMessage(msg).setNeutralButton(android.R.string.ok, null).create();
    }

    static android.app.Dialog makeSimpleDialog(Activity activity, String title, String message) {
        return new AlertDialog.Builder(activity).setMessage(message).setTitle(title).setNeutralButton(android.R.string.ok, null).create();
    }

    private void onSignInFailedTask() {
        Log.e("BraveFrontier", "GPGS Sign in failed");
        ((Cocos2dxActivity)this.getActivity()).getGLView().queueEvent(new SignInFailedTask(this));
    }

    private void onSignInSucceededTask() {
        Log.e("BraveFrontier", "GPGS Sign in successful");
        ((Cocos2dxActivity)this.getActivity()).getGLView().queueEvent(new SignInSucceededTask(this));
    }

    private void onSignOutSucceededTask() {
        ((Cocos2dxActivity)this.getActivity()).getGLView().queueEvent(new SignOutSuccessTask(this));
    }

    static void printMisconfiguredDebugInfo(Context context) {
        Log.w("GameHelper", "****");
        Log.w("GameHelper", "****");
        Log.w("GameHelper", "**** APP NOT CORRECTLY CONFIGURED TO USE GOOGLE PLAY GAME SERVICES");
        Log.w("GameHelper", "**** This is usually caused by one of these reasons:");
        Log.w("GameHelper", "**** (1) Your package name and certificate fingerprint do not match");
        Log.w("GameHelper", "****     the client ID you registered in Developer Console.");
        Log.w("GameHelper", "**** (2) Your App ID was incorrectly entered.");
        Log.w("GameHelper", "**** (3) Your game settings have not been published and you are ");
        Log.w("GameHelper", "****     trying to log in with an account that is not listed as");
        Log.w("GameHelper", "****     a test account.");
        Log.w("GameHelper", "****");
        if (context == null) {
            Log.w("GameHelper", "*** (no Context, so can't print more debug info)");
            return;
        }
        Log.w("GameHelper", "**** To help you debug, here is the information about this app");
        Log.w("GameHelper", "**** Package name         : " +
                context.getPackageName());
        Log.w("GameHelper", "**** Cert SHA1 fingerprint: " +
                GameHelper.getSHA1CertFingerprint(context));
        Log.w("GameHelper", "**** App ID from          : " +
                GameHelper.getAppIdFromResource(context));
        Log.w("GameHelper", "****");
        Log.w("GameHelper", "**** Check that the above information matches your setup in ");
        Log.w("GameHelper", "**** Developer Console. Also, check that you're logging in with the");
        Log.w("GameHelper", "**** right account (it should be listed in the Testers section if");
        Log.w("GameHelper", "**** your project is not yet published).");
        Log.w("GameHelper", "****");
        Log.w("GameHelper", "**** For more information, refer to the troubleshooting guide:");
        Log.w("GameHelper", "****   http://developers.google.com/games/services/android/troubleshooting");
    }

    public static void showFailureDialog(Activity activity, int errorCode, int gmsErrorCode, String s) {
        android.app.Dialog dialog;
        if (activity == null) {
            Log.e("GameHelper", "*** No Activity. Can't show failure dialog!");
            return;
        }
        switch(errorCode) {
            case 10004: {
                dialog = GameHelper.makeSimpleDialog(activity, GameHelper.getString(activity, 2, s));
                break;
            }
            case 10003: {
                dialog = GameHelper.makeSimpleDialog(activity, GameHelper.getString(activity, 3, s));
                break;
            }
            case 10002: {
                dialog = GameHelper.makeSimpleDialog(activity, GameHelper.getString(activity, 1, s));
                break;
            }
            default: {
                dialog = com.google.android.gms.common.GoogleApiAvailability.getInstance().getErrorDialog(activity, gmsErrorCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                if (dialog == null) {
                    Log.e("GameHelper", "No standard error dialog available. Making fallback dialog.");
                    dialog = GameHelper.makeSimpleDialog(activity, GameHelper.getString(activity, 0, s) +
                            " " +
                            GameHelper.errorCodeToString(gmsErrorCode));
                }
            }
        }
        dialog.show();
    }

    private synchronized void startTask(int type) {
        if (mPendingTask == null) {
            mPendingTask = new GameHelperTask(this, null);
            mPendingTask.mTaskType = type;
            mPendingTask.execute((Object[])null);
        }
    }

    void assertConfigured(String msg) {
        if (!mSetupDone) {
            String err = "GameHelper error: Operation attempted without setup: " +
                    msg +
                    ". The setup() method must be called before attempting any other operation.";
            logError(err);
            throw new IllegalStateException(err);
        }
    }

    public void beginUserInitiatedSignIn() {
        debugLog("beginUserInitiatedSignIn: resetting attempt count.");
        resetSignInCancellations();
        mSignInCancelled = false;
        mConnectOnStart = true;
        if (this.isSignedIn()) {
            logWarn("beginUserInitiatedSignIn() called when already connected. Calling listener directly to notify of success.");
            notifyListener(true);
            return;
        }
        debugLog("Starting USER-INITIATED sign-in flow.");
        mUserInitiatedSignIn = true;
        if (mConnectionResult == null) {
            debugLog("beginUserInitiatedSignIn: starting new sign-in flow.");
            mConnecting = true;
            connect();
        } else {
            debugLog("beginUserInitiatedSignIn: continuing pending sign-in flow.");
            mConnecting = true;
            resolveConnectionResult();
        }
    }

    void connect() {
        if (isSignedIn()) {
            debugLog("Already connected.");
            return;
        }
        debugLog("Starting connection.");
        mConnecting = true;
        //mInvitation = null;
        Intent intent = mGoogleSignInClient.getSignInIntent();
        mActivity.startActivityForResult(intent, RC_SIGN_IN);
    }

    public void disconnect() {
        if (isSignedIn()) {
            debugLog("Disconnecting client.");
            mGoogleSignInClient.revokeAccess();
        } else {
            Log.w(mDebugTag, "disconnect() called when client was already disconnected.");
        }
    }

    public GoogleSignInClient getApiClient() {
        if (mGoogleSignInClient == null) {
            throw new IllegalStateException("No GoogleSignInClient. Did you call setup()?");
        }
        return mGoogleSignInClient;
    }

    public Object getAppStateClient() {
        return null;
    }

    public GameService.GameHelperListener getGameHelperListener() {
        return this;
    }

    public Object getGamesClient() {
        return null;
    }

    public String getInvitationId() {
        if (!isSignedIn()) {
            Log.w(mDebugTag, "Warning: getInvitationId() should only be called when signed in, that is, after getting onSignInSuceeded()");
        }

        //return (mInvitation != null) ? mInvitation.getInvitationId() : null;
        return null;
    }

    public String getScopes() {
        return null;
    }

    public String[] getScopesArray() {
        return null;
    }

    public int getSignInCancellations() {
        return mAppContext.getSharedPreferences(GAMEHELPER_SHARED_PREFS, 0).getInt(KEY_SIGN_IN_CANCELLATIONS, 0);
    }

    public GameService.SignInFailureReason getSignInError() {
        return mSignInFailureReason;
    }

    void giveUp(GameService.SignInFailureReason reason) {
        mConnectOnStart = false;
        disconnect();
        mSignInFailureReason = reason;
        if (reason.getActivityResultCode() == 10004) {
            GameHelper.printMisconfiguredDebugInfo(mAppContext);
        }
        showFailureDialog();
        mConnecting = false;
        notifyListener(false);
    }

    public boolean hasSignInError() {
        return mSignInFailureReason != null;
    }

    int incrementSignInCancellations() {
        int cancellations = getSignInCancellations() + 1;
        SharedPreferences.Editor editor = mAppContext.getSharedPreferences(GAMEHELPER_SHARED_PREFS, 0).edit();
        editor.putInt(KEY_SIGN_IN_CANCELLATIONS, cancellations);
        editor.commit();
        return cancellations;
    }

    public boolean isSignedIn() {
        if (GoogleSignIn.getLastSignedInAccount(mAppContext) == null) {
            return false;
        }
        return true;
    }

    public Dialog makeSimpleDialog(String msg) {

        if (mActivity == null) {
            logError("*** makeSimpleDialog failed: no current Activity!");
            return null;
        }
        return GameHelper.makeSimpleDialog(mActivity, msg);
    }

    public Dialog makeSimpleDialog(String title, String msg) {
        if (mActivity == null) {
            logError("*** makeSimpleDialog failed: no current Activity!");
            return null;
        }
        return GameHelper.makeSimpleDialog(mActivity, title, msg);
    }

    void notifyListener(boolean success) {
        String log = "Notifying LISTENER of sign-in " +
                (success ? "SUCCESS" : (this.mSignInFailureReason == null) ? "FAILURE (no error)" : "FAILURE (error)");
        debugLog(log);

        if (mListener != null) {
            if (success) {
                mListener.onSignInSucceeded();
            } else {
                mListener.onSignInFailed();
            }
        }
    }

    public void onActivityResult(int requestId, int responseId, Intent intent) {
        String log = "onActivityResult: req=" +
                ((requestId != RC_RESOLVE) ? String.valueOf(requestId) : "RC_RESOLVE") +
                ", resp=" +
                GameHelper.activityResponseCodeToString(responseId);
        debugLog(log);
        if (requestId == RC_SIGN_IN && responseId == -1) {
            GoogleSignInResult res = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            if (res.isSuccess()) {
                GoogleSignInAccount acc = res.getSignInAccount();
                Games.getGamesClient(getActivity(), acc).setViewForPopups(((Cocos2dxActivity)getActivity()).getGLView());
            }
            return;
        }
       mExpectingResolution = false;
        if (!this.mConnecting) {
            debugLog("onActivityResult: ignoring because we are not connecting.");
            return;
        }
        if (responseId != -1) {
            if (responseId != 10001) {
                if (responseId != 0) {
                    log = "onAR: responseCode=" +
                            GameHelper.activityResponseCodeToString(responseId) +
                            ", so giving up.";
                    debugLog(log);
                    ConnectionResult res = mConnectionResult;
                    giveUp(new GameService.SignInFailureReason((res == null) ? -1 : res.getErrorCode(), responseId));
                } else {
                    debugLog("onAR: Got a cancellation result, so disconnecting.");
                    mSignInCancelled = true;
                    mConnectOnStart = false;
                    mUserInitiatedSignIn = false;
                    mSignInFailureReason = null;
                    mConnecting = false;
                    disconnect();

                    log = "onAR: # of cancellations " +
                            getSignInCancellations() +
                            " --> " +
                            incrementSignInCancellations() +
                            ", max " +
                            this.mMaxAutoSignInAttempts;
                    debugLog(log);
                    notifyListener(false);
                }
            } else {
                debugLog("onAR: Resolution was RECONNECT_REQUIRED, so reconnecting.");
                connect();
            }
        } else {
            debugLog("onAR: Resolution was RESULT_OK, so connecting current client again.");
            connect();
        }
    }

    public void onPause(Activity activity) {
    }

    public void onResume(Activity activity) {
    }

    public void onSignInFailed() {
       startTask(GameHelperTask.TASK_TYPE_SIGN_IN_FAILED);
    }

    public void onSignInSucceeded() {
       startTask(GameHelperTask.TASK_TYPE_SIGN_IN_SUCCEEDED );
    }

    public void onSignOutSucceeded() {
       startTask(GameHelperTask.TASK_TYPE_SIGN_OUT_SUCCEEDED);
    }

    public void onStart(Activity activity) {
        mActivity = activity;
        mAppContext = activity.getApplicationContext();
        debugLog("onStart");
        assertConfigured("onStart");
        if (isSignedIn()) {
            if (isSignedIn()) {
                Log.w(mDebugTag, "GameHelper: client was already connected on onStart()");
                GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(mAppContext);
                Games.getGamesClient(getActivity(), acc).setViewForPopups(((Cocos2dxActivity)getActivity()).getGLView());
            } else {
                debugLog("Connecting client.");
                mConnecting = true;
                connect();
            }
        } else {
            debugLog("Not attempting to connect becase mConnectOnStart=false");
            debugLog("Instead, reporting a sign-in failure.");
            mHandler.postDelayed(new SignInFailureTask(this), 1000L);
        }
        Tracker tracker = mTracker;
        if (tracker != null) {
            try {
                tracker.setScreenName("start");
               mTracker.send(((new HitBuilders.ScreenViewBuilder()).setNewSession()).build());
            } catch(Throwable ignoredException) {
            }
        }
    }

    public void onStop(Activity activity) {
        debugLog("onStop");
        assertConfigured("onStop");
        if (isSignedIn()) {
            debugLog("Disconnecting client due to onStop");
        } else {
            debugLog("Client already disconnected when we got onStop.");
        }
        mConnecting = false;
        mExpectingResolution = false;
        mActivity = null;
    }

    public void reconnectClient() {
    }

    public void reconnectClients(int client) {
        reconnectClient();
    }

    void resetSignInCancellations() {
        SharedPreferences.Editor a = mAppContext.getSharedPreferences(GAMEHELPER_SHARED_PREFS, 0).edit();
        a.putInt(KEY_SIGN_IN_CANCELLATIONS, 0);
        a.commit();
    }

    void resolveConnectionResult() {
        if (mExpectingResolution) {
            debugLog("We're already expecting the result of a previous resolution.");
            return;
        }
        if (mActivity == null) {
            Log.e(mDebugTag, "Ignoring attempt to resolve connection result without an active Activity.");
            return;
        }

        debugLog("resolveConnectionResult: trying to resolve result: " +
                mConnectionResult);

        if (mConnectionResult.hasResolution()) {
            debugLog("Result has resolution. Starting it.");
            try {
               mExpectingResolution = true;
               mConnectionResult.startResolutionForResult(mActivity, RC_RESOLVE);
            } catch(IntentSender.SendIntentException ignoredException) {
                debugLog("SendIntentException, so connecting again.");
                connect();
            }
        } else {
            debugLog("resolveConnectionResult: result has no resolution. Giving up.");
            giveUp(new GameService.SignInFailureReason((mConnectionResult == null) ? -1 : mConnectionResult.getErrorCode()));
        }
    }

    public void setGoogleAdvertisingId(Activity activity, Object obj) {
    }

    public void setup(GameService.GameHelperListener listener) {
        if (mSetupDone) {
            logError("GameHelper: you cannot call GameHelper.setup() more than once!");
            throw new IllegalStateException("GameHelper: you cannot call GameHelper.setup() more than once!");
        }
        mListener = listener;

        debugLog("Setup: requested clients: " +
                mRequestedClients);
        mGoogleSignInClient = GoogleSignIn.getClient(mAppContext,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestScopes(Games.SCOPE_GAMES_LITE, new Scope[0]).requestEmail().build());
        mSetupDone = true;
    }

    public void setup(GameService.GameHelperListener listener, int reqClients, String[] args) {
       mRequestedClients = reqClients;
       setup(listener);
    }

    public void showAchievements() {
        Games.getAchievementsClient(mActivity, GoogleSignIn.getLastSignedInAccount(mAppContext)).getAchievementsIntent().addOnSuccessListener(new SuccessListener(this));
    }

    public void showFailureDialog() {
        if (mSignInFailureReason != null) {
            int serviceErrorCode = mSignInFailureReason.getServiceErrorCode();
            int activityResultCode = mSignInFailureReason.getActivityResultCode();
            if (mShowErrorDialogs) {
                GameHelper.showFailureDialog(mActivity, activityResultCode, serviceErrorCode,mDebugTag);
            } else {
                debugLog("Not showing error dialog because mShowErrorDialogs==false. Error was: " +
                        mSignInFailureReason);
            }
        }
    }

    public void signOut() {
        if (isSignedIn()) {
            signOutWithoutCheck();
            return;
        }
        debugLog("signOut: was already disconnected, ignoring.");
    }

    public void signOutWithoutCheck() {
        debugLog("Disconnecting client.");
        mConnectOnStart = false;
        mConnecting = false;
        mGoogleSignInClient.signOut();
        if (mListener != null) {
            mListener.onSignOutSucceeded();
        }
    }

    public void unlockAchievement(String id) {
        if (isSignedIn()) {
            try {
                Resources res = getActivity().getResources();
                String packageName = res.getString(res.getIdentifier(id, "string", getActivity().getPackageName()));
                Games.getAchievementsClient(mActivity, GoogleSignIn.getLastSignedInAccount(mAppContext)).unlock(packageName);
                Games.getAchievementsClient(mActivity, GoogleSignIn.getLastSignedInAccount(mAppContext)).load(false);
            } catch(Throwable ex) {
                ex.printStackTrace();
            }
        } else {
            SharedPreferences preferences = BraveFrontier.getAppContext().getSharedPreferences(GOOGLE_PLAY_PREF, 0);
            SharedPreferences.Editor editor = preferences.edit();
            String achievementKey = preferences.getString(GOOGLE_PLAY_ACHIEVEMENT_KEY, "");
            if (!achievementKey.equals("")) {
                id = achievementKey +
                        "," +
                        id;
            }
            editor.putString(GOOGLE_PLAY_ACHIEVEMENT_KEY, id);
            editor.commit();
        }
    }

    public void updateLeaderboard(int value, String name) {
        if (isSignedIn()) {
            try {
                Resources res = getActivity().getResources();
                String leaderBoardValue = res.getString(res.getIdentifier(name, "string", getActivity().getPackageName()));
                Log.d("GameHelper", "updateLeaderboard id: " +
                        name +
                        ", leaderboardID: " +
                        leaderBoardValue +
                        ", score: " +
                        value);
                Games.getLeaderboardsClient(mActivity, GoogleSignIn.getLastSignedInAccount(mAppContext)).submitScore(leaderBoardValue, value);
            } catch(Throwable ex) {
                ex.printStackTrace();
            }
        } else {
            SharedPreferences.Editor editor = BraveFrontier.getAppContext().getSharedPreferences(GOOGLE_PLAY_PREF, 0).edit();
            editor.putInt(name, value);
            editor.commit();
        }
    }
}
