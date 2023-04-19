package sg.gumi.bravefrontier;

public class RVHandler {
    private static String TAG = "OfferWall";
    private static sg.gumi.bravefrontier.RVHandler instance;
    final private static com.ironsource.mediationsdk.sdk.OfferwallListener mOfferWallListener;
    final private static com.ironsource.mediationsdk.sdk.RewardedVideoListener mRewardedVideoListener;
    
    static {
        mRewardedVideoListener = (com.ironsource.mediationsdk.sdk.RewardedVideoListener)(Object)new sg.gumi.bravefrontier.RVHandler$1();
        mOfferWallListener = (com.ironsource.mediationsdk.sdk.OfferwallListener)(Object)new sg.gumi.bravefrontier.RVHandler$2();
    }
    
    protected RVHandler() {
        android.util.Log.d("BF-RVHandler", "protected RVHandler");
    }
    
    static String access$000() {
        return TAG;
    }
    
    native public static void adInvokeCompletedCallback();
    
    
    native public static void adInvokeFailedCallback();
    
    
    native public static void adInvokeStarted();
    
    
    public static sg.gumi.bravefrontier.RVHandler getInstance() {
        if (instance == null) {
            instance = new sg.gumi.bravefrontier.RVHandler();
        }
        return instance;
    }
    
    public static void initialiseHandler() {
        android.util.Log.d("BF-RVHandler", "public static void initialiseHandler");
    }
    
    native public static void initialiseMediator();
    
    
    public static void invokeAd(String s) {
        if (com.ironsource.mediationsdk.IronSource.isRewardedVideoAvailable()) {
            android.util.Log.d("BF-RVHandler", "public static void invokeAd");
            com.ironsource.mediationsdk.IronSource.showRewardedVideo(s);
        }
    }
    
    public static void invokeOfferWall(String s) {
        if (com.ironsource.mediationsdk.IronSource.isOfferwallAvailable()) {
            android.util.Log.d("BF-RVHandler", "public static void invokeOfferWall");
            com.ironsource.mediationsdk.IronSource.showOfferwall(s);
        } else {
            android.util.Log.d("BF-RVHandler", "offerwall not available");
        }
    }
    
    public static boolean isOfferWallAvailable() {
        return com.ironsource.mediationsdk.IronSource.isOfferwallAvailable();
    }
    
    public static boolean isRewardedVideoAvailable() {
        return com.ironsource.mediationsdk.IronSource.isRewardedVideoAvailable();
    }
    
    native public static void offerWallInvokeCompletedCallback();
    
    
    native public static void offerWallInvokeFailedCallback();
    
    
    native public static void onOfferWalldAvailable();
    
    
    native public static void onOfferWalldNotAvailable();
    
    
    public static void onPause() {
        android.util.Log.d("BF-RVHandler", "public static void onPause");
        com.ironsource.mediationsdk.IronSource.onPause((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity());
    }
    
    native public static void onRVAdAvailable();
    
    
    native public static void onRVAdNotAvailable();
    
    
    native public static void onRVInitFailure();
    
    
    public static void onResume() {
        android.util.Log.d("BF-RVHandler", "public static void onResume");
        com.ironsource.mediationsdk.IronSource.onResume((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity());
    }
    
    public static void setUpIronSourceSDK(String s, String s0) {
        StringBuilder a = new StringBuilder();
        a.append("user id is and app key is");
        a.append(s);
        a.append(s0);
        android.util.Log.d("BF-RVHandler", a.toString());
        com.ironsource.mediationsdk.IronSource.setUserId(s);
        com.ironsource.mediationsdk.IronSource.setRewardedVideoListener(mRewardedVideoListener);
        com.ironsource.mediationsdk.IronSource.setOfferwallListener(mOfferWallListener);
        com.ironsource.adapters.supersonicads.SupersonicConfig.getConfigObj().setClientSideCallbacks(true);
        com.ironsource.mediationsdk.IronSource.init((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity(), s0);
        com.ironsource.mediationsdk.integration.IntegrationHelper.validateIntegration((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity());
        sg.gumi.bravefrontier.RVHandler.initialiseMediator();
    }
}
