package sg.gumi.bravefrontier;

public class YoutubeActivity extends sg.gumi.bravefrontier.YouTubeFailureRecoveryActivity {
    public static String VIDEO_ID = "";
    private static String playbackState = "";
    private static String playerState = "";
    private sg.gumi.bravefrontier.YoutubeActivity$MyPlaybackEventListener playbackEventListener;
    private com.google.android.youtube.player.YouTubePlayer player;
    private sg.gumi.bravefrontier.YoutubeActivity$MyPlayerStateChangeListener playerStateChangeListener;
    
    static {
    }
    
    public YoutubeActivity() {
    }
    
    static String access$200() {
        return playbackState;
    }
    
    static String access$202(String s) {
        playbackState = s;
        return s;
    }
    
    static com.google.android.youtube.player.YouTubePlayer access$300(sg.gumi.bravefrontier.YoutubeActivity a) {
        return a.player;
    }
    
    static com.google.android.youtube.player.YouTubePlayer access$302(sg.gumi.bravefrontier.YoutubeActivity a, com.google.android.youtube.player.YouTubePlayer a0) {
        a.player = a0;
        return a0;
    }
    
    static String access$400(sg.gumi.bravefrontier.YoutubeActivity a, int i) {
        return a.formatTime(i);
    }
    
    static String access$500() {
        return playerState;
    }
    
    static String access$502(String s) {
        playerState = s;
        return s;
    }
    
    private String formatTime(int i) {
        String s = null;
        int i0 = i / 1000;
        int i1 = i0 / 60;
        int i2 = i1 / 60;
        StringBuilder a = new StringBuilder();
        if (i2 != 0) {
            StringBuilder a0 = new StringBuilder();
            a0.append(i2);
            a0.append(":");
            s = a0.toString();
        } else {
            s = "";
        }
        a.append(s);
        Object[] a1 = new Object[2];
        a1[0] = Integer.valueOf(i1 % 60);
        a1[1] = Integer.valueOf(i0 % 60);
        a.append(String.format("%02d:%02d", a1));
        return a.toString();
    }
    
    protected com.google.android.youtube.player.YouTubePlayer$Provider getYouTubePlayerProvider() {
        return (com.google.android.youtube.player.YouTubePlayer$Provider)(Object)(com.google.android.youtube.player.YouTubePlayerView)((android.app.Activity)this).findViewById(2131165408);
    }
    
    public void onCreate(android.os.Bundle a) {
        ((com.google.android.youtube.player.YouTubeBaseActivity)this).onCreate(a);
        ((android.app.Activity)this).setContentView(2131361849);
        ((com.google.android.youtube.player.YouTubePlayerView)((android.app.Activity)this).findViewById(2131165408)).initialize("AIzaSyBpm9ijwTe3-T-4UsW2Z4XsuouGusEDPIE", (com.google.android.youtube.player.YouTubePlayer$OnInitializedListener)(Object)this);
        this.playerStateChangeListener = new sg.gumi.bravefrontier.YoutubeActivity$MyPlayerStateChangeListener(this, (sg.gumi.bravefrontier.YoutubeActivity$1)null);
        this.playbackEventListener = new sg.gumi.bravefrontier.YoutubeActivity$MyPlaybackEventListener(this, (sg.gumi.bravefrontier.YoutubeActivity$1)null);
    }
    
    public void onInitializationSuccess(com.google.android.youtube.player.YouTubePlayer$Provider a, com.google.android.youtube.player.YouTubePlayer a0, boolean b) {
        this.player = a0;
        a0.setPlayerStateChangeListener((com.google.android.youtube.player.YouTubePlayer$PlayerStateChangeListener)(Object)this.playerStateChangeListener);
        a0.setPlaybackEventListener((com.google.android.youtube.player.YouTubePlayer$PlaybackEventListener)(Object)this.playbackEventListener);
        a0.loadVideo(VIDEO_ID);
        a0.setFullscreen(true);
        a0.setShowFullscreenButton(false);
        a0.setPlayerStyle(com.google.android.youtube.player.YouTubePlayer$PlayerStyle.MINIMAL);
    }
}
