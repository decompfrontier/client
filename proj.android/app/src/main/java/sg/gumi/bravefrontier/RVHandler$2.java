package sg.gumi.bravefrontier;

class RVHandler$2 implements com.ironsource.mediationsdk.sdk.OfferwallListener {
    RVHandler$2() {
    }
    
    public void onGetOfferwallCreditsFailed(com.ironsource.mediationsdk.logger.IronSourceError a) {
        String s = sg.gumi.bravefrontier.RVHandler.access$000();
        StringBuilder a0 = new StringBuilder();
        a0.append("onGetOfferwallCreditsFailed ");
        a0.append((Object)a);
        android.util.Log.d(s, a0.toString());
    }
    
    public boolean onOfferwallAdCredited(int i, int i0, boolean b) {
        String s = sg.gumi.bravefrontier.RVHandler.access$000();
        StringBuilder a = new StringBuilder();
        a.append("onOfferwallAdCredited credits:");
        a.append(i);
        a.append(" totalCredits:");
        a.append(i0);
        a.append(" totalCreditsFlag:");
        a.append(b);
        android.util.Log.d(s, a.toString());
        android.util.Log.d("BF-RVHandler", "offerwall crediated");
        sg.gumi.bravefrontier.RVHandler.offerWallInvokeCompletedCallback();
        return true;
    }
    
    public void onOfferwallAvailable(boolean b) {
        if (b) {
            android.util.Log.d("BF-RVHandler", "offer wall add avaialable");
            sg.gumi.bravefrontier.RVHandler.onOfferWalldAvailable();
        } else {
            android.util.Log.d("BF-RVHandler", "offer wall add not avaialable");
            sg.gumi.bravefrontier.RVHandler.onOfferWalldNotAvailable();
        }
    }
    
    public void onOfferwallClosed() {
        android.util.Log.d("BF-RVHandler", "onOfferwallClosed");
    }
    
    public void onOfferwallOpened() {
        android.util.Log.d("BF-RVHandler", "onOfferwallOpened");
    }
    
    public void onOfferwallShowFailed(com.ironsource.mediationsdk.logger.IronSourceError a) {
        StringBuilder a0 = new StringBuilder();
        a0.append("onOfferwallShowFailed ");
        a0.append((Object)a);
        android.util.Log.d("BF-RVHandler", a0.toString());
        sg.gumi.bravefrontier.RVHandler.offerWallInvokeFailedCallback();
    }
}
