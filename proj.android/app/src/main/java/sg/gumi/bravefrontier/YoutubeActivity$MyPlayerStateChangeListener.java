package sg.gumi.bravefrontier;

final class YoutubeActivity$MyPlayerStateChangeListener implements com.google.android.youtube.player.YouTubePlayer$PlayerStateChangeListener {
    final sg.gumi.bravefrontier.YoutubeActivity this$0;
    
    private YoutubeActivity$MyPlayerStateChangeListener(sg.gumi.bravefrontier.YoutubeActivity a) {
        this.this$0 = a;
        super();
    }
    
    YoutubeActivity$MyPlayerStateChangeListener(sg.gumi.bravefrontier.YoutubeActivity a, sg.gumi.bravefrontier.YoutubeActivity$1 a0) {
        this(a);
    }
    
    public void onAdStarted() {
        sg.gumi.bravefrontier.YoutubeActivity.access$502("AD_STARTED");
        android.util.Log.v("Youtube", sg.gumi.bravefrontier.YoutubeActivity.access$500());
    }
    
    public void onError(com.google.android.youtube.player.YouTubePlayer$ErrorReason a) {
        StringBuilder a0 = new StringBuilder();
        a0.append("ERROR (");
        a0.append((Object)a);
        a0.append(")");
        sg.gumi.bravefrontier.YoutubeActivity.access$502(a0.toString());
        sg.gumi.bravefrontier.BraveFrontierJNI.videoSkippedCallback();
        sg.gumi.bravefrontier.YoutubeActivity.access$300(this.this$0).release();
        sg.gumi.bravefrontier.YoutubeActivity.access$302(this.this$0, (com.google.android.youtube.player.YouTubePlayer)null);
        ((android.app.Activity)this.this$0).finish();
        android.util.Log.v("Youtube", sg.gumi.bravefrontier.YoutubeActivity.access$500());
    }
    
    public void onLoaded(String s) {
        Object[] a = new Object[1];
        a[0] = s;
        sg.gumi.bravefrontier.YoutubeActivity.access$502(String.format("LOADED %s", a));
        android.util.Log.v("Youtube", sg.gumi.bravefrontier.YoutubeActivity.access$500());
    }
    
    public void onLoading() {
        sg.gumi.bravefrontier.YoutubeActivity.access$502("LOADING");
        android.util.Log.v("Youtube", sg.gumi.bravefrontier.YoutubeActivity.access$500());
    }
    
    public void onVideoEnded() {
        sg.gumi.bravefrontier.YoutubeActivity.access$502("VIDEO_ENDED");
        android.util.Log.v("Youtube", sg.gumi.bravefrontier.YoutubeActivity.access$500());
        sg.gumi.bravefrontier.BraveFrontierJNI.videoFinishedCallback();
        sg.gumi.bravefrontier.YoutubeActivity.access$300(this.this$0).release();
        sg.gumi.bravefrontier.YoutubeActivity.access$302(this.this$0, (com.google.android.youtube.player.YouTubePlayer)null);
        ((android.app.Activity)this.this$0).finish();
    }
    
    public void onVideoStarted() {
        sg.gumi.bravefrontier.YoutubeActivity.access$502("VIDEO_STARTED");
        android.util.Log.v("Youtube", sg.gumi.bravefrontier.YoutubeActivity.access$500());
    }
}
