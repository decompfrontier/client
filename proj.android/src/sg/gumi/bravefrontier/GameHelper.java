package sg.gumi.bravefrontier;

final public class GameHelper extends sg.gumi.bravefrontier.GameService implements sg.gumi.bravefrontier.GameService$GameHelperListener {
    final static int DEFAULT_MAX_SIGN_IN_ATTEMPTS = 3;
    final private static String[] FALLBACK_STRINGS;
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
    final private static int[] RES_IDS;
    final public static int R_APP_MISCONFIGURED = 2;
    final public static int R_LICENSE_FAILED = 3;
    final public static int R_SIGN_IN_FAILED = 1;
    final public static int R_UNKNOWN_ERROR = 0;
    final private String GAMEHELPER_SHARED_PREFS;
    final private String KEY_SIGN_IN_CANCELLATIONS;
    android.content.Context mAppContext;
    com.google.android.gms.common.api.Api$ApiOptions$NoOptions mAppStateApiOptions;
    boolean mConnectOnStart;
    private boolean mConnecting;
    com.google.android.gms.common.ConnectionResult mConnectionResult;
    boolean mDebugLog;
    String mDebugTag;
    boolean mDisconnectedFromAchievementUI;
    boolean mExpectingResolution;
    com.google.android.gms.auth.api.signin.GoogleSignInClient mGoogleSignInClient;
    android.os.Handler mHandler;
    com.google.android.gms.games.multiplayer.Invitation mInvitation;
    sg.gumi.bravefrontier.GameService$GameHelperListener mListener;
    int mMaxAutoSignInAttempts;
    sg.gumi.bravefrontier.GameHelper$GameHelperTask mPendingTask;
    int mRequestedClients;
    private boolean mSetupDone;
    boolean mShowErrorDialogs;
    boolean mSignInCancelled;
    sg.gumi.bravefrontier.GameService$SignInFailureReason mSignInFailureReason;
    com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch mTurnBasedMatch;
    boolean mUserInitiatedSignIn;
    
    static {
        String[] a = new String[4];
        a[0] = "*Unknown error.";
        a[1] = "*Failed to sign in. Please check your network connection and try again.";
        a[2] = "*The application is incorrectly configured. Check that the package name and signing certificate match the client ID created in Developer Console. Also, if the application is not yet published, check that the account you are trying to sign in with is listed as a tester account. See logs for more information.";
        a[3] = "*License check failed.";
        FALLBACK_STRINGS = a;
        int[] a0 = new int[4];
        a0[0] = 2131558548;
        a0[1] = 2131558547;
        a0[2] = 2131558545;
        a0[3] = 2131558546;
        RES_IDS = a0;
    }
    
    public GameHelper(android.app.Activity a) {
        super(a);
        this.mSetupDone = false;
        this.mConnecting = false;
        this.mExpectingResolution = false;
        this.mSignInCancelled = false;
        this.mAppContext = null;
        this.mAppStateApiOptions = null;
        this.mGoogleSignInClient = null;
        this.mRequestedClients = 0;
        this.mConnectOnStart = false;
        this.mUserInitiatedSignIn = false;
        this.mConnectionResult = null;
        this.mSignInFailureReason = null;
        this.mShowErrorDialogs = true;
        this.mDebugLog = false;
        this.mDebugTag = "GameHelper";
        this.mListener = null;
        this.mPendingTask = null;
        this.mMaxAutoSignInAttempts = 3;
        this.mDisconnectedFromAchievementUI = false;
        this.GAMEHELPER_SHARED_PREFS = "GAMEHELPER_SHARED_PREFS";
        this.KEY_SIGN_IN_CANCELLATIONS = "KEY_SIGN_IN_CANCELLATIONS";
        try {
            this.mAppContext = a.getApplicationContext();
        } catch(Throwable ignoredException) {
        }
        this.mRequestedClients = 1;
        this.mDisconnectedFromAchievementUI = false;
        this.mHandler = new android.os.Handler();
    }
    
    static int access$000() {
        return RC_ACHIEVEMENT_UI;
    }
    
    static void access$200(sg.gumi.bravefrontier.GameHelper a) {
        a.onSignInSucceededTask();
    }
    
    static void access$300(sg.gumi.bravefrontier.GameHelper a) {
        a.onSignInFailedTask();
    }
    
    static void access$400(sg.gumi.bravefrontier.GameHelper a) {
        a.onSignOutSucceededTask();
    }
    
    static void access$500(sg.gumi.bravefrontier.GameHelper a) {
        a.endTask();
    }
    
    static String activityResponseCodeToString(int i) {
        if (i == -1) {
            return "RESULT_OK";
        }
        if (i == 0) {
            return "RESULT_CANCELED";
        }
        switch(i) {
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
                return String.valueOf(i);
            }
        }
    }
    
    static void byteToString(StringBuilder a, byte a0) {
        int i = a0;
        int i0 = a0;
        if (a0 < 0) {
            i0 = i + 256;
        }
        int i1 = i0 / 16;
        int i2 = i0 % 16;
        a.append("0123456789ABCDEF".substring(i1, i1 + 1));
        a.append("0123456789ABCDEF".substring(i2, i2 + 1));
    }
    
    private void endTask() {
        /*monenter(this)*/;
        try {
            if (this.mPendingTask != null) {
                try {
                    ((android.os.AsyncTask)this.mPendingTask).cancel(true);
                } catch(Throwable ignoredException) {
                }
                this.mPendingTask = null;
            }
        } catch(NullPointerException a) {
            /*monexit(this)*/;
            throw a;
        }
        /*monexit(this)*/;
    }
    
    static String errorCodeToString(int i) {
        switch(i) {
            case 11: {
                StringBuilder a = new StringBuilder();
                a.append("LICENSE_CHECK_FAILED(");
                a.append(i);
                a.append(")");
                return a.toString();
            }
            case 10: {
                StringBuilder a0 = new StringBuilder();
                a0.append("DEVELOPER_ERROR(");
                a0.append(i);
                a0.append(")");
                return a0.toString();
            }
            case 9: {
                StringBuilder a1 = new StringBuilder();
                a1.append("SERVICE_INVALID(");
                a1.append(i);
                a1.append(")");
                return a1.toString();
            }
            case 8: {
                StringBuilder a2 = new StringBuilder();
                a2.append("INTERNAL_ERROR(");
                a2.append(i);
                a2.append(")");
                return a2.toString();
            }
            case 7: {
                StringBuilder a3 = new StringBuilder();
                a3.append("NETWORK_ERROR(");
                a3.append(i);
                a3.append(")");
                return a3.toString();
            }
            case 6: {
                StringBuilder a4 = new StringBuilder();
                a4.append("RESOLUTION_REQUIRED(");
                a4.append(i);
                a4.append(")");
                return a4.toString();
            }
            case 5: {
                StringBuilder a5 = new StringBuilder();
                a5.append("INVALID_ACCOUNT(");
                a5.append(i);
                a5.append(")");
                return a5.toString();
            }
            case 4: {
                StringBuilder a6 = new StringBuilder();
                a6.append("SIGN_IN_REQUIRED(");
                a6.append(i);
                a6.append(")");
                return a6.toString();
            }
            case 3: {
                StringBuilder a7 = new StringBuilder();
                a7.append("SERVICE_DISABLED(");
                a7.append(i);
                a7.append(")");
                return a7.toString();
            }
            case 2: {
                StringBuilder a8 = new StringBuilder();
                a8.append("SERVICE_VERSION_UPDATE_REQUIRED(");
                a8.append(i);
                a8.append(")");
                return a8.toString();
            }
            case 1: {
                StringBuilder a9 = new StringBuilder();
                a9.append("SERVICE_MISSING(");
                a9.append(i);
                a9.append(")");
                return a9.toString();
            }
            case 0: {
                StringBuilder a10 = new StringBuilder();
                a10.append("SUCCESS(");
                a10.append(i);
                a10.append(")");
                return a10.toString();
            }
            default: {
                StringBuilder a11 = new StringBuilder();
                a11.append("Unknown error code ");
                a11.append(i);
                return a11.toString();
            }
        }
    }
    
    static String getAppIdFromResource(android.content.Context a) {
        String s = null;
        try {
            android.content.res.Resources a0 = a.getResources();
            s = a0.getString(a0.getIdentifier("app_id", "string", a.getPackageName()));
        } catch(Exception a1) {
            a1.printStackTrace();
            return "??? (failed to retrieve APP ID)";
        }
        return s;
    }
    
    static String getSHA1CertFingerprint(android.content.Context a) {
        java.security.NoSuchAlgorithmException a0 = null;
        try {
            try {
                android.content.pm.Signature[] a1 = (android.os.Build$VERSION.SDK_INT < 28) ? a.getPackageManager().getPackageInfo(a.getPackageName(), 64).signatures : a.getPackageManager().getPackageInfo(a.getPackageName(), 134217728).signingInfo.getApkContentsSigners();
                if (a1.length == 0) {
                    return "ERROR: NO SIGNATURE.";
                }
                if (a1.length > 1) {
                    return "ERROR: MULTIPLE SIGNATURES";
                }
                byte[] a2 = java.security.MessageDigest.getInstance("SHA1").digest(a1[0].toByteArray());
                StringBuilder a3 = new StringBuilder();
                int i = 0;
                for(; i < a2.length; i = i + 1) {
                    if (i > 0) {
                        a3.append(":");
                    }
                    sg.gumi.bravefrontier.GameHelper.byteToString(a3, (byte)(int)a2[i]);
                }
                return a3.toString();
            } catch(java.security.NoSuchAlgorithmException a4) {
                a0 = a4;
            }
        } catch(android.content.pm.PackageManager$NameNotFoundException a5) {
            a5.printStackTrace();
            return "(ERROR: package not found)";
        }
        a0.printStackTrace();
        return "(ERROR: SHA1 algorithm not found)";
    }
    
    static String getString(android.content.Context a, int i, String s) {
        String s0 = null;
        label0: {
            label1: {
                if (i < 0) {
                    break label1;
                }
                if (i < RES_IDS.length) {
                    break label0;
                }
            }
            i = 0;
        }
        int i0 = RES_IDS[i];
        try {
            s0 = a.getString(i0);
        } catch(Exception a0) {
            a0.printStackTrace();
            StringBuilder a1 = new StringBuilder();
            a1.append("*** GameHelper could not found resource id #");
            a1.append(i0);
            a1.append(". This probably happened because you included it as a stand-alone JAR. BaseGameUtils should be compiled as a LIBRARY PROJECT, so that it can access its resources. Using a fallback string.");
            android.util.Log.w(s, a1.toString());
            return FALLBACK_STRINGS[i];
        }
        return s0;
    }
    
    static android.app.Dialog makeSimpleDialog(android.app.Activity a, String s) {
        return new android.app.AlertDialog$Builder((android.content.Context)a).setMessage((CharSequence)(Object)s).setNeutralButton(17039370, (android.content.DialogInterface$OnClickListener)null).create();
    }
    
    static android.app.Dialog makeSimpleDialog(android.app.Activity a, String s, String s0) {
        return new android.app.AlertDialog$Builder((android.content.Context)a).setMessage((CharSequence)(Object)s0).setTitle((CharSequence)(Object)s).setNeutralButton(17039370, (android.content.DialogInterface$OnClickListener)null).create();
    }
    
    private void onSignInFailedTask() {
        android.util.Log.e("BraveFrontier", "GPGS Sign in failed");
        ((android.opengl.GLSurfaceView)((org.cocos2dx.lib.Cocos2dxActivity)(sg.gumi.bravefrontier.BraveFrontier)((sg.gumi.bravefrontier.GameService)this).getActivity()).getGLView()).queueEvent((Runnable)(Object)new sg.gumi.bravefrontier.GameHelper$4(this));
    }
    
    private void onSignInSucceededTask() {
        android.util.Log.e("BraveFrontier", "GPGS Sign in successful");
        ((android.opengl.GLSurfaceView)((org.cocos2dx.lib.Cocos2dxActivity)(sg.gumi.bravefrontier.BraveFrontier)((sg.gumi.bravefrontier.GameService)this).getActivity()).getGLView()).queueEvent((Runnable)(Object)new sg.gumi.bravefrontier.GameHelper$3(this));
    }
    
    private void onSignOutSucceededTask() {
        ((android.opengl.GLSurfaceView)((org.cocos2dx.lib.Cocos2dxActivity)(sg.gumi.bravefrontier.BraveFrontier)((sg.gumi.bravefrontier.GameService)this).getActivity()).getGLView()).queueEvent((Runnable)(Object)new sg.gumi.bravefrontier.GameHelper$5(this));
    }
    
    static void printMisconfiguredDebugInfo(android.content.Context a) {
        android.util.Log.w("GameHelper", "****");
        android.util.Log.w("GameHelper", "****");
        android.util.Log.w("GameHelper", "**** APP NOT CORRECTLY CONFIGURED TO USE GOOGLE PLAY GAME SERVICES");
        android.util.Log.w("GameHelper", "**** This is usually caused by one of these reasons:");
        android.util.Log.w("GameHelper", "**** (1) Your package name and certificate fingerprint do not match");
        android.util.Log.w("GameHelper", "****     the client ID you registered in Developer Console.");
        android.util.Log.w("GameHelper", "**** (2) Your App ID was incorrectly entered.");
        android.util.Log.w("GameHelper", "**** (3) Your game settings have not been published and you are ");
        android.util.Log.w("GameHelper", "****     trying to log in with an account that is not listed as");
        android.util.Log.w("GameHelper", "****     a test account.");
        android.util.Log.w("GameHelper", "****");
        if (a == null) {
            android.util.Log.w("GameHelper", "*** (no Context, so can't print more debug info)");
            return;
        }
        android.util.Log.w("GameHelper", "**** To help you debug, here is the information about this app");
        StringBuilder a0 = new StringBuilder();
        a0.append("**** Package name         : ");
        a0.append(a.getPackageName());
        android.util.Log.w("GameHelper", a0.toString());
        StringBuilder a1 = new StringBuilder();
        a1.append("**** Cert SHA1 fingerprint: ");
        a1.append(sg.gumi.bravefrontier.GameHelper.getSHA1CertFingerprint(a));
        android.util.Log.w("GameHelper", a1.toString());
        StringBuilder a2 = new StringBuilder();
        a2.append("**** App ID from          : ");
        a2.append(sg.gumi.bravefrontier.GameHelper.getAppIdFromResource(a));
        android.util.Log.w("GameHelper", a2.toString());
        android.util.Log.w("GameHelper", "****");
        android.util.Log.w("GameHelper", "**** Check that the above information matches your setup in ");
        android.util.Log.w("GameHelper", "**** Developer Console. Also, check that you're logging in with the");
        android.util.Log.w("GameHelper", "**** right account (it should be listed in the Testers section if");
        android.util.Log.w("GameHelper", "**** your project is not yet published).");
        android.util.Log.w("GameHelper", "****");
        android.util.Log.w("GameHelper", "**** For more information, refer to the troubleshooting guide:");
        android.util.Log.w("GameHelper", "****   http://developers.google.com/games/services/android/troubleshooting");
    }
    
    public static void showFailureDialog(android.app.Activity a, int i, int i0, String s) {
        android.app.Dialog a0 = null;
        if (a == null) {
            android.util.Log.e("GameHelper", "*** No Activity. Can't show failure dialog!");
            return;
        }
        switch(i) {
            case 10004: {
                a0 = sg.gumi.bravefrontier.GameHelper.makeSimpleDialog(a, sg.gumi.bravefrontier.GameHelper.getString((android.content.Context)a, 2, s));
                break;
            }
            case 10003: {
                a0 = sg.gumi.bravefrontier.GameHelper.makeSimpleDialog(a, sg.gumi.bravefrontier.GameHelper.getString((android.content.Context)a, 3, s));
                break;
            }
            case 10002: {
                a0 = sg.gumi.bravefrontier.GameHelper.makeSimpleDialog(a, sg.gumi.bravefrontier.GameHelper.getString((android.content.Context)a, 1, s));
                break;
            }
            default: {
                a0 = com.google.android.gms.common.GoogleApiAvailability.getInstance().getErrorDialog(a, i0, 9000);
                if (a0 == null) {
                    android.util.Log.e("GameHelper", "No standard error dialog available. Making fallback dialog.");
                    StringBuilder a1 = new StringBuilder();
                    a1.append(sg.gumi.bravefrontier.GameHelper.getString((android.content.Context)a, 0, s));
                    a1.append(" ");
                    a1.append(sg.gumi.bravefrontier.GameHelper.errorCodeToString(i0));
                    a0 = sg.gumi.bravefrontier.GameHelper.makeSimpleDialog(a, a1.toString());
                }
            }
        }
        a0.show();
    }
    
    private void startTask(int i) {
        /*monenter(this)*/;
        try {
            if (this.mPendingTask == null) {
                sg.gumi.bravefrontier.GameHelper$GameHelperTask a = new sg.gumi.bravefrontier.GameHelper$GameHelperTask(this, (sg.gumi.bravefrontier.GameHelper$1)null);
                this.mPendingTask = a;
                a.mTaskType = i;
                ((android.os.AsyncTask)a).execute((Object[])null);
            }
        } catch(Throwable a0) {
            /*monexit(this)*/;
            throw a0;
        }
        /*monexit(this)*/;
    }
    
    void assertConfigured(String s) {
        if (!this.mSetupDone) {
            StringBuilder a = new StringBuilder();
            a.append("GameHelper error: Operation attempted without setup: ");
            a.append(s);
            a.append(". The setup() method must be called before attempting any other operation.");
            String s0 = a.toString();
            ((sg.gumi.bravefrontier.GameService)this).logError(s0);
            throw new IllegalStateException(s0);
        }
    }
    
    public void beginUserInitiatedSignIn() {
        ((sg.gumi.bravefrontier.GameService)this).debugLog("beginUserInitiatedSignIn: resetting attempt count.");
        this.resetSignInCancellations();
        this.mSignInCancelled = false;
        this.mConnectOnStart = true;
        if (this.isSignedIn()) {
            ((sg.gumi.bravefrontier.GameService)this).logWarn("beginUserInitiatedSignIn() called when already connected. Calling listener directly to notify of success.");
            this.notifyListener(true);
            return;
        }
        ((sg.gumi.bravefrontier.GameService)this).debugLog("Starting USER-INITIATED sign-in flow.");
        this.mUserInitiatedSignIn = true;
        if (this.mConnectionResult == null) {
            ((sg.gumi.bravefrontier.GameService)this).debugLog("beginUserInitiatedSignIn: starting new sign-in flow.");
            this.mConnecting = true;
            this.connect();
        } else {
            ((sg.gumi.bravefrontier.GameService)this).debugLog("beginUserInitiatedSignIn: continuing pending sign-in flow.");
            this.mConnecting = true;
            this.resolveConnectionResult();
        }
    }
    
    void connect() {
        if (this.isSignedIn()) {
            ((sg.gumi.bravefrontier.GameService)this).debugLog("Already connected.");
            return;
        }
        ((sg.gumi.bravefrontier.GameService)this).debugLog("Starting connection.");
        this.mConnecting = true;
        this.mInvitation = null;
        this.mTurnBasedMatch = null;
        android.content.Intent a = this.mGoogleSignInClient.getSignInIntent();
        this.mActivity.startActivityForResult(a, RC_SIGN_IN);
    }
    
    public void disconnect() {
        if (this.isSignedIn()) {
            ((sg.gumi.bravefrontier.GameService)this).debugLog("Disconnecting client.");
            this.mGoogleSignInClient.revokeAccess();
        } else {
            android.util.Log.w(this.mDebugTag, "disconnect() called when client was already disconnected.");
        }
    }
    
    public com.google.android.gms.auth.api.signin.GoogleSignInClient getApiClient() {
        com.google.android.gms.auth.api.signin.GoogleSignInClient a = this.mGoogleSignInClient;
        if (a == null) {
            throw new IllegalStateException("No GoogleSignInClient. Did you call setup()?");
        }
        return a;
    }
    
    public Object getAppStateClient() {
        return null;
    }
    
    public sg.gumi.bravefrontier.GameService$GameHelperListener getGameHelperListener() {
        return (sg.gumi.bravefrontier.GameService$GameHelperListener)(Object)this;
    }
    
    public Object getGamesClient() {
        return null;
    }
    
    public String getInvitationId() {
        if (!this.isSignedIn()) {
            android.util.Log.w(this.mDebugTag, "Warning: getInvitationId() should only be called when signed in, that is, after getting onSignInSuceeded()");
        }
        com.google.android.gms.games.multiplayer.Invitation a = this.mInvitation;
        return (a != null) ? a.getInvitationId() : null;
    }
    
    public String getScopes() {
        return null;
    }
    
    public String[] getScopesArray() {
        return null;
    }
    
    public int getSignInCancellations() {
        return this.mAppContext.getSharedPreferences("GAMEHELPER_SHARED_PREFS", 0).getInt("KEY_SIGN_IN_CANCELLATIONS", 0);
    }
    
    public sg.gumi.bravefrontier.GameService$SignInFailureReason getSignInError() {
        return this.mSignInFailureReason;
    }
    
    void giveUp(sg.gumi.bravefrontier.GameService$SignInFailureReason a) {
        this.mConnectOnStart = false;
        this.disconnect();
        this.mSignInFailureReason = a;
        if (a.getActivityResultCode() == 10004) {
            sg.gumi.bravefrontier.GameHelper.printMisconfiguredDebugInfo(this.mAppContext);
        }
        this.showFailureDialog();
        this.mConnecting = false;
        this.notifyListener(false);
    }
    
    public boolean hasSignInError() {
        return this.mSignInFailureReason != null;
    }
    
    int incrementSignInCancellations() {
        int i = this.getSignInCancellations();
        android.content.SharedPreferences$Editor a = this.mAppContext.getSharedPreferences("GAMEHELPER_SHARED_PREFS", 0).edit();
        int i0 = i + 1;
        a.putInt("KEY_SIGN_IN_CANCELLATIONS", i0);
        a.commit();
        return i0;
    }
    
    public boolean isSignedIn() {
        if (com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(this.mAppContext) == null) {
            return false;
        }
        return true;
    }
    
    public android.app.Dialog makeSimpleDialog(String s) {
        android.app.Activity a = this.mActivity;
        if (a == null) {
            ((sg.gumi.bravefrontier.GameService)this).logError("*** makeSimpleDialog failed: no current Activity!");
            android.app.Dialog a0 = null;
            return a0;
        }
        return sg.gumi.bravefrontier.GameHelper.makeSimpleDialog(a, s);
    }
    
    public android.app.Dialog makeSimpleDialog(String s, String s0) {
        android.app.Activity a = this.mActivity;
        if (a == null) {
            ((sg.gumi.bravefrontier.GameService)this).logError("*** makeSimpleDialog failed: no current Activity!");
            android.app.Dialog a0 = null;
            return a0;
        }
        return sg.gumi.bravefrontier.GameHelper.makeSimpleDialog(a, s, s0);
    }
    
    void notifyListener(boolean b) {
        StringBuilder a = new StringBuilder();
        a.append("Notifying LISTENER of sign-in ");
        a.append(b ? "SUCCESS" : (this.mSignInFailureReason == null) ? "FAILURE (no error)" : "FAILURE (error)");
        ((sg.gumi.bravefrontier.GameService)this).debugLog(a.toString());
        sg.gumi.bravefrontier.GameService$GameHelperListener a0 = this.mListener;
        if (a0 != null) {
            if (b) {
                a0.onSignInSucceeded();
            } else {
                a0.onSignInFailed();
            }
        }
    }
    
    public void onActivityResult(int i, int i0, android.content.Intent a) {
        StringBuilder a0 = new StringBuilder();
        a0.append("onActivityResult: req=");
        a0.append((i != 9001) ? String.valueOf(i) : "RC_RESOLVE");
        a0.append(", resp=");
        a0.append(sg.gumi.bravefrontier.GameHelper.activityResponseCodeToString(i0));
        ((sg.gumi.bravefrontier.GameService)this).debugLog(a0.toString());
        if (i == RC_SIGN_IN && i0 == -1) {
            com.google.android.gms.auth.api.signin.GoogleSignInResult a1 = com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInResultFromIntent(a);
            if (a1.isSuccess()) {
                com.google.android.gms.auth.api.signin.GoogleSignInAccount a2 = a1.getSignInAccount();
                com.google.android.gms.games.Games.getGamesClient(((sg.gumi.bravefrontier.GameService)this).getActivity(), a2).setViewForPopups((android.view.View)((org.cocos2dx.lib.Cocos2dxActivity)(sg.gumi.bravefrontier.BraveFrontier)((sg.gumi.bravefrontier.GameService)this).getActivity()).getGLView());
            }
            return;
        }
        this.mExpectingResolution = false;
        if (!this.mConnecting) {
            ((sg.gumi.bravefrontier.GameService)this).debugLog("onActivityResult: ignoring because we are not connecting.");
            return;
        }
        if (i0 != -1) {
            if (i0 != 10001) {
                if (i0 != 0) {
                    StringBuilder a3 = new StringBuilder();
                    a3.append("onAR: responseCode=");
                    a3.append(sg.gumi.bravefrontier.GameHelper.activityResponseCodeToString(i0));
                    a3.append(", so giving up.");
                    ((sg.gumi.bravefrontier.GameService)this).debugLog(a3.toString());
                    com.google.android.gms.common.ConnectionResult a4 = this.mConnectionResult;
                    this.giveUp(new sg.gumi.bravefrontier.GameService$SignInFailureReason((a4 == null) ? -1 : a4.getErrorCode(), i0));
                } else {
                    ((sg.gumi.bravefrontier.GameService)this).debugLog("onAR: Got a cancellation result, so disconnecting.");
                    this.mSignInCancelled = true;
                    this.mConnectOnStart = false;
                    this.mUserInitiatedSignIn = false;
                    this.mSignInFailureReason = null;
                    this.mConnecting = false;
                    this.disconnect();
                    int i1 = this.getSignInCancellations();
                    int i2 = this.incrementSignInCancellations();
                    StringBuilder a5 = new StringBuilder();
                    a5.append("onAR: # of cancellations ");
                    a5.append(i1);
                    a5.append(" --> ");
                    a5.append(i2);
                    a5.append(", max ");
                    a5.append(this.mMaxAutoSignInAttempts);
                    ((sg.gumi.bravefrontier.GameService)this).debugLog(a5.toString());
                    this.notifyListener(false);
                }
            } else {
                ((sg.gumi.bravefrontier.GameService)this).debugLog("onAR: Resolution was RECONNECT_REQUIRED, so reconnecting.");
                this.connect();
            }
        } else {
            ((sg.gumi.bravefrontier.GameService)this).debugLog("onAR: Resolution was RESULT_OK, so connecting current client again.");
            this.connect();
        }
    }
    
    public void onPause(android.app.Activity a) {
    }
    
    public void onResume(android.app.Activity a) {
    }
    
    public void onSignInFailed() {
        this.startTask(2);
    }
    
    public void onSignInSucceeded() {
        this.startTask(1);
    }
    
    public void onSignOutSucceeded() {
        this.startTask(3);
    }
    
    public void onStart(android.app.Activity a) {
        this.mActivity = a;
        this.mAppContext = a.getApplicationContext();
        ((sg.gumi.bravefrontier.GameService)this).debugLog("onStart");
        this.assertConfigured("onStart");
        if (this.isSignedIn()) {
            if (this.isSignedIn()) {
                android.util.Log.w(this.mDebugTag, "GameHelper: client was already connected on onStart()");
                com.google.android.gms.auth.api.signin.GoogleSignInAccount a0 = com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(this.mAppContext);
                com.google.android.gms.games.Games.getGamesClient(((sg.gumi.bravefrontier.GameService)this).getActivity(), a0).setViewForPopups((android.view.View)((org.cocos2dx.lib.Cocos2dxActivity)(sg.gumi.bravefrontier.BraveFrontier)((sg.gumi.bravefrontier.GameService)this).getActivity()).getGLView());
            } else {
                ((sg.gumi.bravefrontier.GameService)this).debugLog("Connecting client.");
                this.mConnecting = true;
                this.connect();
            }
        } else {
            ((sg.gumi.bravefrontier.GameService)this).debugLog("Not attempting to connect becase mConnectOnStart=false");
            ((sg.gumi.bravefrontier.GameService)this).debugLog("Instead, reporting a sign-in failure.");
            this.mHandler.postDelayed((Runnable)(Object)new sg.gumi.bravefrontier.GameHelper$2(this), 1000L);
        }
        com.google.android.gms.analytics.Tracker a1 = this.mTracker;
        if (a1 != null) {
            try {
                a1.setScreenName("start");
                this.mTracker.send(((com.google.android.gms.analytics.HitBuilders$HitBuilder)(com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder)((com.google.android.gms.analytics.HitBuilders$HitBuilder)new com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder()).setNewSession()).build());
            } catch(Throwable ignoredException) {
            }
        }
    }
    
    public void onStop(android.app.Activity a) {
        ((sg.gumi.bravefrontier.GameService)this).debugLog("onStop");
        this.assertConfigured("onStop");
        if (this.isSignedIn()) {
            ((sg.gumi.bravefrontier.GameService)this).debugLog("Disconnecting client due to onStop");
        } else {
            ((sg.gumi.bravefrontier.GameService)this).debugLog("Client already disconnected when we got onStop.");
        }
        this.mConnecting = false;
        this.mExpectingResolution = false;
        this.mActivity = null;
    }
    
    public void reconnectClient() {
    }
    
    public void reconnectClients(int i) {
        this.reconnectClient();
    }
    
    void resetSignInCancellations() {
        android.content.SharedPreferences$Editor a = this.mAppContext.getSharedPreferences("GAMEHELPER_SHARED_PREFS", 0).edit();
        a.putInt("KEY_SIGN_IN_CANCELLATIONS", 0);
        a.commit();
    }
    
    void resolveConnectionResult() {
        if (this.mExpectingResolution) {
            ((sg.gumi.bravefrontier.GameService)this).debugLog("We're already expecting the result of a previous resolution.");
            return;
        }
        if (this.mActivity == null) {
            android.util.Log.e(this.mDebugTag, "Ignoring attempt to resolve connection result without an active Activity.");
            return;
        }
        StringBuilder a = new StringBuilder();
        a.append("resolveConnectionResult: trying to resolve result: ");
        a.append((Object)this.mConnectionResult);
        ((sg.gumi.bravefrontier.GameService)this).debugLog(a.toString());
        boolean b = this.mConnectionResult.hasResolution();
        label0: {
            label1: if (b) {
                ((sg.gumi.bravefrontier.GameService)this).debugLog("Result has resolution. Starting it.");
                try {
                    this.mExpectingResolution = true;
                    this.mConnectionResult.startResolutionForResult(this.mActivity, 9001);
                } catch(android.content.IntentSender$SendIntentException ignoredException) {
                    break label1;
                }
                break label0;
            } else {
                ((sg.gumi.bravefrontier.GameService)this).debugLog("resolveConnectionResult: result has no resolution. Giving up.");
                com.google.android.gms.common.ConnectionResult a0 = this.mConnectionResult;
                this.giveUp(new sg.gumi.bravefrontier.GameService$SignInFailureReason((a0 == null) ? -1 : a0.getErrorCode()));
                break label0;
            }
            ((sg.gumi.bravefrontier.GameService)this).debugLog("SendIntentException, so connecting again.");
            this.connect();
        }
    }
    
    public void setGoogleAdvertisingId(android.app.Activity a, Object a0) {
    }
    
    public void setup(sg.gumi.bravefrontier.GameService$GameHelperListener a) {
        if (this.mSetupDone) {
            ((sg.gumi.bravefrontier.GameService)this).logError("GameHelper: you cannot call GameHelper.setup() more than once!");
            throw new IllegalStateException("GameHelper: you cannot call GameHelper.setup() more than once!");
        }
        this.mListener = a;
        StringBuilder a0 = new StringBuilder();
        a0.append("Setup: requested clients: ");
        a0.append(this.mRequestedClients);
        ((sg.gumi.bravefrontier.GameService)this).debugLog(a0.toString());
        com.google.android.gms.auth.api.signin.GoogleSignInOptions a1 = new com.google.android.gms.auth.api.signin.GoogleSignInOptions$Builder(com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN).requestScopes(com.google.android.gms.games.Games.SCOPE_GAMES_LITE, new com.google.android.gms.common.api.Scope[0]).requestEmail().build();
        this.mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this.mAppContext, a1);
        this.mSetupDone = true;
    }
    
    public void setup(sg.gumi.bravefrontier.GameService$GameHelperListener a, int i, String[] a0) {
        this.mRequestedClients = i;
        this.setup(a);
    }
    
    public void showAchievements() {
        com.google.android.gms.games.Games.getAchievementsClient(this.mActivity, com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(this.mAppContext)).getAchievementsIntent().addOnSuccessListener((com.google.android.gms.tasks.OnSuccessListener)(Object)new sg.gumi.bravefrontier.GameHelper$1(this));
    }
    
    public void showFailureDialog() {
        sg.gumi.bravefrontier.GameService$SignInFailureReason a = this.mSignInFailureReason;
        if (a != null) {
            int i = a.getServiceErrorCode();
            int i0 = this.mSignInFailureReason.getActivityResultCode();
            if (this.mShowErrorDialogs) {
                sg.gumi.bravefrontier.GameHelper.showFailureDialog(this.mActivity, i0, i, this.mDebugTag);
            } else {
                StringBuilder a0 = new StringBuilder();
                a0.append("Not showing error dialog because mShowErrorDialogs==false. Error was: ");
                a0.append((Object)this.mSignInFailureReason);
                ((sg.gumi.bravefrontier.GameService)this).debugLog(a0.toString());
            }
        }
    }
    
    public void signOut() {
        if (this.isSignedIn()) {
            this.signOutWithoutCheck();
            return;
        }
        ((sg.gumi.bravefrontier.GameService)this).debugLog("signOut: was already disconnected, ignoring.");
    }
    
    public void signOutWithoutCheck() {
        ((sg.gumi.bravefrontier.GameService)this).debugLog("Disconnecting client.");
        this.mConnectOnStart = false;
        this.mConnecting = false;
        this.mGoogleSignInClient.signOut();
        sg.gumi.bravefrontier.GameService$GameHelperListener a = this.mListener;
        if (a != null) {
            a.onSignOutSucceeded();
        }
    }
    
    public void unlockAchievement(String s) {
        boolean b = this.isSignedIn();
        label0: {
            Throwable a = null;
            label1: if (b) {
                try {
                    android.content.res.Resources a0 = ((sg.gumi.bravefrontier.GameService)this).getActivity().getResources();
                    String s0 = a0.getString(a0.getIdentifier(s, "string", ((sg.gumi.bravefrontier.GameService)this).getActivity().getPackageName()));
                    com.google.android.gms.games.Games.getAchievementsClient(this.mActivity, com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(this.mAppContext)).unlock(s0);
                    com.google.android.gms.games.Games.getAchievementsClient(this.mActivity, com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(this.mAppContext)).load(false);
                } catch(Throwable a1) {
                    a = a1;
                    break label1;
                }
                break label0;
            } else {
                android.content.SharedPreferences a2 = sg.gumi.bravefrontier.BraveFrontier.getAppContext().getSharedPreferences("GOOGLE_PLAY", 0);
                android.content.SharedPreferences$Editor a3 = a2.edit();
                String s1 = a2.getString("ACH", "");
                if (!s1.equals((Object)"")) {
                    StringBuilder a4 = new StringBuilder();
                    a4.append(s1);
                    a4.append(",");
                    a4.append(s);
                    s = a4.toString();
                }
                a3.putString("ACH", s);
                a3.commit();
                break label0;
            }
            a.printStackTrace();
        }
    }
    
    public void updateLeaderboard(int i, String s) {
        boolean b = this.isSignedIn();
        label0: {
            Throwable a = null;
            label1: if (b) {
                try {
                    android.content.res.Resources a0 = ((sg.gumi.bravefrontier.GameService)this).getActivity().getResources();
                    String s0 = a0.getString(a0.getIdentifier(s, "string", ((sg.gumi.bravefrontier.GameService)this).getActivity().getPackageName()));
                    StringBuilder a1 = new StringBuilder();
                    a1.append("updateLeaderboard id: ");
                    a1.append(s);
                    a1.append(", leaderboardID: ");
                    a1.append(s0);
                    a1.append(", score: ");
                    a1.append(i);
                    android.util.Log.d("GameHelper", a1.toString());
                    com.google.android.gms.games.Games.getLeaderboardsClient(this.mActivity, com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(this.mAppContext)).submitScore(s0, (long)i);
                } catch(Throwable a2) {
                    a = a2;
                    break label1;
                }
                break label0;
            } else {
                android.content.SharedPreferences$Editor a3 = sg.gumi.bravefrontier.BraveFrontier.getAppContext().getSharedPreferences("GOOGLE_PLAY", 0).edit();
                a3.putInt(s, i);
                a3.commit();
                break label0;
            }
            a.printStackTrace();
        }
    }
}
