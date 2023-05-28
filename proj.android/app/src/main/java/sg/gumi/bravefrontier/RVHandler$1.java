package sg.gumi.bravefrontier;

class RVHandler$1 implements com.ironsource.mediationsdk.sdk.RewardedVideoListener {
    com.ironsource.mediationsdk.model.Placement mRewardPlacement;
    
    RVHandler$1() {
        this.mRewardPlacement = null;
    }
    
    public void onRewardedVideoAdClicked(com.ironsource.mediationsdk.model.Placement a) {
    }
    
    public void onRewardedVideoAdClosed() {
        android.util.Log.d("BF-RVHandler", "public void onRewardedVideoAdClosed");
        sg.gumi.bravefrontier.RVHandler.adInvokeCompletedCallback();
        this.mRewardPlacement = null;
    }
    
    public void onRewardedVideoAdEnded() {
    }
    
    public void onRewardedVideoAdOpened() {
        android.util.Log.d("BF-RVHandler", "public void onRewardedVideoAdOpened");
    }
    
    public void onRewardedVideoAdRewarded(com.ironsource.mediationsdk.model.Placement a) {
        a.getRewardName();
        a.getRewardAmount();
        this.mRewardPlacement = a;
        android.util.Log.d("BF-RVHandler", "public void onRewardedVideoAdRewarded");
    }
    
    public void onRewardedVideoAdShowFailed(com.ironsource.mediationsdk.logger.IronSourceError a) {
        android.util.Log.d("BF-RVHandler", "public void onRewardedVideoInitFail");
        a.getErrorCode();
        a.getErrorMessage();
        sg.gumi.bravefrontier.RVHandler.onRVInitFailure();
    }
    
    public void onRewardedVideoAdStarted() {
        sg.gumi.bravefrontier.RVHandler.adInvokeStarted();
    }
    
    public void onRewardedVideoAvailabilityChanged(boolean b) {
        if (b) {
            android.util.Log.d("BF-RVHandler", "public void onVideoAvailabilityChanged - true");
            sg.gumi.bravefrontier.RVHandler.onRVAdAvailable();
        } else {
            android.util.Log.d("BF-RVHandler", "public void onVideoAvailabilityChanged - false");
            sg.gumi.bravefrontier.RVHandler.onRVAdNotAvailable();
        }
    }
}
