package sg.gumi.bravefrontier;

import android.util.Log;

//import com.ironsource.mediationsdk.sdk.OfferwallListener;
//import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
//import com.ironsource.mediationsdk.model.Placement;
//import com.ironsource.mediationsdk.logger.IronSourceError;
//import com.ironsource.mediationsdk.IronSource;
//import com.ironsource.mediationsdk.integration.IntegrationHelper;
//import com.ironsource.adapters.supersonicads.SupersonicConfig;

public class RVHandler {

    static class RVListener /*implements RewardedVideoListener*/ {
        //Placement mRewardPlacement;

        RVListener() {
            //mRewardPlacement = null;
        }

        /*public void onRewardedVideoAdClicked(Placement placement) {
        }*/

        public void onRewardedVideoAdClosed() {
            Log.d("BF-RVHandler", "public void onRewardedVideoAdClosed");
            RVHandler.adInvokeCompletedCallback();
            //mRewardPlacement = null;
        }

        public void onRewardedVideoAdEnded() {
        }

        public void onRewardedVideoAdOpened() {
            Log.d("BF-RVHandler", "public void onRewardedVideoAdOpened");
        }

        /*public void onRewardedVideoAdRewarded(Placement placement) {
            placement.getRewardName();
            placement.getRewardAmount();
            mRewardPlacement = placement;
            Log.d("BF-RVHandler", "public void onRewardedVideoAdRewarded");
        }

        public void onRewardedVideoAdShowFailed(IronSourceError placement) {
            Log.d("BF-RVHandler", "public void onRewardedVideoInitFail");
            placement.getErrorCode();
            placement.getErrorMessage();
            RVHandler.onRVInitFailure();
        }*/

        public void onRewardedVideoAdStarted() {
            RVHandler.adInvokeStarted();
        }

        public void onRewardedVideoAvailabilityChanged(boolean adAvailable) {
            if (adAvailable) {
                Log.d("BF-RVHandler", "public void onVideoAvailabilityChanged - true");
                RVHandler.onRVAdAvailable();
            } else {
                Log.d("BF-RVHandler", "public void onVideoAvailabilityChanged - false");
                RVHandler.onRVAdNotAvailable();
            }
        }
    }


    class RVOfferWallListener /*implements OfferwallListener*/ {
        RVOfferWallListener() {
        }

        /*public void onGetOfferwallCreditsFailed(IronSourceError error) {
            Log.d(RVHandler.getTag(), "onGetOfferwallCreditsFailed " +
                    error);
        }*/

        public boolean onOfferwallAdCredited(int credits, int totalCredits, boolean flag) {
            Log.d(RVHandler.getTag(), "onOfferwallAdCredited credits:" +
                    credits +
                    " totalCredits:" +
                    totalCredits +
                    " totalCreditsFlag:" +
                    flag);
            Log.d("BF-RVHandler", "offerwall crediated");
            RVHandler.offerWallInvokeCompletedCallback();
            return true;
        }

        public void onOfferwallAvailable(boolean available) {
            if (available) {
                Log.d("BF-RVHandler", "offer wall add avaialable");
                RVHandler.onOfferWalldAvailable();
            } else {
                Log.d("BF-RVHandler", "offer wall add not avaialable");
                RVHandler.onOfferWalldNotAvailable();
            }
        }

        public void onOfferwallClosed() {
            Log.d("BF-RVHandler", "onOfferwallClosed");
        }

        public void onOfferwallOpened() {
            Log.d("BF-RVHandler", "onOfferwallOpened");
        }

        /*public void onOfferwallShowFailed(IronSourceError err) {
            Log.d("BF-RVHandler", "onOfferwallShowFailed " +
                    err);
            RVHandler.offerWallInvokeFailedCallback();
        }*/
    }


    private static String TAG = "OfferWall";
    private static RVHandler instance;
    /*final private static OfferwallListener mOfferWallListener = new RVOfferWallListener();
    final private static RewardedVideoListener mRewardedVideoListener = new RVListener();*/

    protected RVHandler() {
        Log.d("BF-RVHandler", "protected RVHandler");
    }
    
    static String getTag() {
        return TAG;
    }
    
    native public static void adInvokeCompletedCallback();
    
    
    native public static void adInvokeFailedCallback();
    
    
    native public static void adInvokeStarted();
    
    
    public static RVHandler getInstance() {
        if (instance == null) { // singleton
            instance = new RVHandler();
        }
        return instance;
    }
    
    public static void initialiseHandler() {
        Log.d("BF-RVHandler", "public static void initialiseHandler");
    }
    
    native public static void initialiseMediator();
    
    
    public static void invokeAd(String s) {
        /*if (IronSource.isRewardedVideoAvailable()) {
            Log.d("BF-RVHandler", "public static void invokeAd");
            IronSource.showRewardedVideo(s);
        }*/
    }
    
    public static void invokeOfferWall(String s) {
        /*if (IronSource.isOfferwallAvailable()) {
            Log.d("BF-RVHandler", "public static void invokeOfferWall");
            IronSource.showOfferwall(s);
        } else {
            Log.d("BF-RVHandler", "offerwall not available");
        }*/
    }
    
    public static boolean isOfferWallAvailable() {
        //return IronSource.isOfferwallAvailable();
        return false;
    }
    
    public static boolean isRewardedVideoAvailable() {
        //return IronSource.isRewardedVideoAvailable();
        return false;
    }
    
    native public static void offerWallInvokeCompletedCallback();
    
    
    native public static void offerWallInvokeFailedCallback();
    
    
    native public static void onOfferWalldAvailable();
    
    
    native public static void onOfferWalldNotAvailable();
    
    
    public static void onPause() {
        Log.d("BF-RVHandler", "public static void onPause");
        //IronSource.onPause(BraveFrontier.getActivity());
    }
    
    native public static void onRVAdAvailable();
    
    
    native public static void onRVAdNotAvailable();
    
    
    native public static void onRVInitFailure();
    
    
    public static void onResume() {
        Log.d("BF-RVHandler", "public static void onResume");
        //IronSource.onResume(BraveFrontier.getActivity());
    }
    
    public static void setUpIronSourceSDK(String userid, String appkey) {
        Log.d("BF-RVHandler", "user id is and app key is" +
                userid +
                appkey);
        /*IronSource.setUserId(userid);
        IronSource.setRewardedVideoListener(mRewardedVideoListener);
        IronSource.setOfferwallListener(mOfferWallListener);
        SupersonicConfig.getConfigObj().setClientSideCallbacks(true);
        IronSource.init(BraveFrontier.getActivity(), appkey);
        IntegrationHelper.validateIntegration(BraveFrontier.getActivity());*/
        RVHandler.initialiseMediator();
    }
}
