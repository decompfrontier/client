package sg.gumi.bravefrontier;

final class YoutubeActivity$MyPlaybackEventListener implements com.google.android.youtube.player.YouTubePlayer$PlaybackEventListener {
    final sg.gumi.bravefrontier.YoutubeActivity this$0;
    
    private YoutubeActivity$MyPlaybackEventListener(sg.gumi.bravefrontier.YoutubeActivity a) {
        this.this$0 = a;
        super();
    }
    
    YoutubeActivity$MyPlaybackEventListener(sg.gumi.bravefrontier.YoutubeActivity a, sg.gumi.bravefrontier.YoutubeActivity$1 a0) {
        this(a);
    }
    
    public void onBuffering(boolean b) {
        sg.gumi.bravefrontier.YoutubeActivity.access$202(b ? "(BUFFERING)" : "");
        android.util.Log.v("Youtube", sg.gumi.bravefrontier.YoutubeActivity.access$200());
    }
    
    public void onPaused() {
        sg.gumi.bravefrontier.YoutubeActivity.access$202("PAUSED");
        android.util.Log.v("Youtube", sg.gumi.bravefrontier.YoutubeActivity.access$200());
        sg.gumi.bravefrontier.BraveFrontierJNI.videoSkippedCallback();
        sg.gumi.bravefrontier.YoutubeActivity.access$300(this.this$0).release();
        sg.gumi.bravefrontier.YoutubeActivity.access$302(this.this$0, (com.google.android.youtube.player.YouTubePlayer)null);
        ((android.app.Activity)this.this$0).finish();
    }
    
    public void onPlaying() {
        sg.gumi.bravefrontier.YoutubeActivity.access$202("PLAYING");
        android.util.Log.v("Youtube", sg.gumi.bravefrontier.YoutubeActivity.access$200());
    }
    
    public void onSeekTo(int i) {
        String s = sg.gumi.bravefrontier.YoutubeActivity.access$400(this.this$0, i);
        sg.gumi.bravefrontier.YoutubeActivity a = this.this$0;
        Object[] a0 = new Object[2];
        a0[0] = s;
        a0[1] = sg.gumi.bravefrontier.YoutubeActivity.access$400(a, sg.gumi.bravefrontier.YoutubeActivity.access$300(a).getDurationMillis());
        android.util.Log.v("YoutubeActivity", String.format("\tSEEKTO: (%s/%s)", a0));
    }
    
    public void onStopped() {
        sg.gumi.bravefrontier.YoutubeActivity.access$202("STOPPED");
        android.util.Log.v("Youtube", sg.gumi.bravefrontier.YoutubeActivity.access$200());
    }
}
