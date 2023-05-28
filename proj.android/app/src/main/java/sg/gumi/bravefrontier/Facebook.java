package sg.gumi.bravefrontier;

public class Facebook {
    private static com.facebook.CallbackManager callbackManager;
    private static sg.gumi.bravefrontier.BraveFrontier mActivity;
    private static com.facebook.login.LoginManager mLoginManager;
    private static int offset;
    public static sg.gumi.bravefrontier.Facebook sFacebook;
    private com.facebook.appevents.AppEventsLogger appEventsLogger;
    private android.os.Bundle feedParams;
    private java.util.HashMap[] m_openGraphObjectsArr;
    
    static {
    }
    
    public Facebook(sg.gumi.bravefrontier.BraveFrontier a) {
        this.feedParams = null;
        sFacebook = this;
        mActivity = a;
        this.appEventsLogger = com.facebook.appevents.AppEventsLogger.newLogger((android.content.Context)a, ((android.app.Activity)a).getResources().getString(2131558540));
        mLoginManager = com.facebook.login.LoginManager.getInstance();
        com.facebook.CallbackManager a0 = com.facebook.CallbackManager$Factory.create();
        callbackManager = a0;
        mLoginManager.registerCallback(a0, (com.facebook.FacebookCallback)(Object)new sg.gumi.bravefrontier.Facebook$1(this));
    }
    
    public static boolean callbackmanagerOnActivityResult(int i, int i0, android.content.Intent a) {
        return callbackManager.onActivityResult(i, i0, a);
    }
    
    public static boolean checkForPermission(java.util.List a) {
        return false;
    }
    
    public static String getAccessToken() {
        com.facebook.AccessToken a = com.facebook.AccessToken.getCurrentAccessToken();
        if (a == null) {
            return "";
        }
        return a.getToken();
    }
    
    public static String getUserEmail() {
        return "";
    }
    
    public static String getUserId() {
        com.facebook.AccessToken a = com.facebook.AccessToken.getCurrentAccessToken();
        if (a == null) {
            return "";
        }
        return a.getUserId();
    }
    
    public static boolean isLoggedIn() {
        if (com.facebook.AccessToken.getCurrentAccessToken() == null) {
            return false;
        }
        return true;
    }
    
    public static void login() {
        mLoginManager.logInWithReadPermissions((android.app.Activity)mActivity, (java.util.Collection)null);
    }
    
    public static void logout() {
        android.util.Log.d("ALEX_TEST", "FB LOG OUT");
        mLoginManager.logOut();
    }
    
    native public static void onError(String arg);
    
    
    native public static void onFacebookOperationCancelledException();
    
    
    native public static void onLoginFail(String arg);
    
    
    native public static void onLoginSuccess();
    
    
    native public static void onUserLogin();
    
    
    final public static void trackPurchase(float f, String s) {
        try {
            sFacebook.appEventsLogger.logPurchase(java.math.BigDecimal.valueOf((double)f), java.util.Currency.getInstance(s));
        } catch(Throwable ignoredException) {
        }
    }
}
