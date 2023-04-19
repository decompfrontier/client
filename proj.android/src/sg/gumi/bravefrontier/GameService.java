package sg.gumi.bravefrontier;

abstract public class GameService {
    final public static int CLIENT_ALL = 5;
    final public static int CLIENT_APPSTATE = 4;
    final public static int CLIENT_GAMES = 1;
    final public static int CLIENT_NONE = 0;
    final protected static int GA_DISPATCH_PERIOD = 30;
    final protected static boolean GA_IS_DRY_RUN = false;
    android.app.Activity mActivity;
    boolean mDebugLog;
    String mDebugTag;
    protected com.google.android.gms.analytics.Tracker mTracker;
    String mUnknownErrorMessage;
    
    public GameService(android.app.Activity a) {
        this.mActivity = null;
        this.mTracker = null;
        this.mDebugLog = false;
        this.mDebugTag = "GameHelper";
        this.mUnknownErrorMessage = "Unknown game error";
        this.mActivity = a;
    }
    
    public static sg.gumi.bravefrontier.GameService createService(android.app.Activity a) {
        sg.gumi.bravefrontier.GameHelper a0 = null;
        sg.gumi.util.BFConfig$Platform a1 = sg.gumi.util.BFConfig.PLATFORM;
        sg.gumi.util.BFConfig$Platform a2 = sg.gumi.util.BFConfig.PLATFORM_AMAZON;
        label2: {
            label0: {
                label1: {
                    if (a1 != a2) {
                        break label1;
                    }
                    break label0;
                }
                if (sg.gumi.util.BFConfig.PLATFORM == sg.gumi.util.BFConfig.PLATFORM_SAMSUNG) {
                    break label0;
                }
                a0 = new sg.gumi.bravefrontier.GameHelper(a);
                break label2;
            }
            a0 = null;
        }
        return a0;
    }
    
    abstract public void beginUserInitiatedSignIn();
    
    
    final protected void debugLog(String s) {
        if (this.mDebugLog) {
            android.util.Log.d(this.mDebugTag, s);
        }
    }
    
    final public void enableDebugLog(boolean b, String s) {
        this.mDebugLog = b;
        this.mDebugTag = s;
    }
    
    final protected android.app.Activity getActivity() {
        return this.mActivity;
    }
    
    abstract public Object getAppStateClient();
    
    
    abstract public sg.gumi.bravefrontier.GameService$GameHelperListener getGameHelperListener();
    
    
    abstract public Object getGamesClient();
    
    
    abstract public String getInvitationId();
    
    
    public void getLeaderBoardScore(String s) {
    }
    
    abstract public String getScopes();
    
    
    abstract public String[] getScopesArray();
    
    
    abstract public int getSignInCancellations();
    
    
    abstract public sg.gumi.bravefrontier.GameService$SignInFailureReason getSignInError();
    
    
    public void googleAnalyticsSendScreenView(String s) {
        com.google.android.gms.analytics.Tracker a = this.mTracker;
        if (a != null) {
            try {
                a.setScreenName(s);
                this.mTracker.send(((com.google.android.gms.analytics.HitBuilders$HitBuilder)new com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder()).build());
            } catch(Throwable ignoredException) {
            }
        }
    }
    
    public void googleAnalyticsSetUserID(String s) {
        com.google.android.gms.analytics.Tracker a = this.mTracker;
        if (a != null) {
            try {
                a.set("&uid", s);
            } catch(Throwable ignoredException) {
            }
        }
    }
    
    public void googleAnalyticsTrackEvent(String s, String s0, String s1, long j) {
        com.google.android.gms.analytics.Tracker a = this.mTracker;
        if (a != null) {
            try {
                a.send(((com.google.android.gms.analytics.HitBuilders$HitBuilder)new com.google.android.gms.analytics.HitBuilders$EventBuilder().setCategory(s).setAction(s0).setLabel(s1).setValue(j)).build());
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
    
    
    public void initGoogleAnalytics(android.app.Activity a) {
    }
    
    public void initializeAmazonPhoneManager() {
    }
    
    public void initializePSGN() {
    }
    
    abstract public boolean isSignedIn();
    
    
    void logError(String s) {
        if (this.mDebugLog) {
            android.util.Log.e(this.mDebugTag, s);
        }
    }
    
    void logWarn(String s) {
        if (this.mDebugLog) {
            android.util.Log.w(this.mDebugTag, s);
        }
    }
    
    abstract public void onActivityResult(int arg, int arg0, android.content.Intent arg1);
    
    
    abstract public void onPause(android.app.Activity arg);
    
    
    abstract public void onResume(android.app.Activity arg);
    
    
    abstract public void onStart(android.app.Activity arg);
    
    
    abstract public void onStop(android.app.Activity arg);
    
    
    abstract public void reconnectClients(int arg);
    
    
    public void setAndroidIds(android.app.Activity a, Object a0) {
    }
    
    public void setGoogleAdvertisingId(android.app.Activity a, Object a0) {
    }
    
    final public void setUnknownErrorMessage(String s) {
        StringBuilder a = new StringBuilder();
        a.append("Setting unknown error message to: ");
        a.append(s);
        this.debugLog(a.toString());
        this.mUnknownErrorMessage = s;
    }
    
    abstract public void setup(sg.gumi.bravefrontier.GameService$GameHelperListener arg, int arg0, String[] arg1);
    
    
    abstract public void showAchievements();
    
    
    final public void showAlert(String s) {
        new android.app.AlertDialog$Builder((android.content.Context)this.getActivity()).setMessage((CharSequence)(Object)s).setNeutralButton(17039370, (android.content.DialogInterface$OnClickListener)null).create().show();
    }
    
    final public void showAlert(String s, String s0) {
        new android.app.AlertDialog$Builder((android.content.Context)this.getActivity()).setTitle((CharSequence)(Object)s).setMessage((CharSequence)(Object)s0).setNeutralButton(17039370, (android.content.DialogInterface$OnClickListener)null).create().show();
    }
    
    public void showPlayPhoneButton(boolean b) {
    }
    
    abstract public void signOut();
    
    
    public void submitLeaderBoardScore(String s, int i) {
    }
    
    abstract public void unlockAchievement(String arg);
    
    
    abstract public void updateLeaderboard(int arg, String arg0);
}
