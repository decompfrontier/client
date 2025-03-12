package sg.gumi.bravefrontier;

import android.os.Bundle;
import android.util.Log;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeFailureRecoveryActivity {

    final static class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {
        final YoutubeActivity activity;

        private MyPlaybackEventListener(sg.gumi.bravefrontier.YoutubeActivity activity) {
            super();
            this.activity = activity;
        }

        public void onBuffering(boolean enableBuffer) {
            YoutubeActivity.setPlaybackState(enableBuffer ? "(BUFFERING)" : "");
            Log.v("Youtube", YoutubeActivity.getPlaybackState());
        }

        public void onPaused() {
            YoutubeActivity.setPlaybackState("PAUSED");
            Log.v("Youtube", YoutubeActivity.getPlaybackState());
            BraveFrontierJNI.videoSkippedCallback();
            YoutubeActivity.getPlayer(activity).release();
            YoutubeActivity.setPlayer(activity, null);
            activity.finish();
        }

        public void onPlaying() {
            YoutubeActivity.setPlaybackState("PLAYING");
            Log.v("Youtube", YoutubeActivity.getPlaybackState());
        }

        public void onSeekTo(int i) {
            String s = sg.gumi.bravefrontier.YoutubeActivity.formatActivityTime(this.activity, i);
            sg.gumi.bravefrontier.YoutubeActivity a = this.activity;
            Object[] a0 = new Object[2];
            a0[0] = s;
            a0[1] = sg.gumi.bravefrontier.YoutubeActivity.formatActivityTime(a, sg.gumi.bravefrontier.YoutubeActivity.getPlayer(a).getDurationMillis());
            android.util.Log.v("YoutubeActivity", String.format("\tSEEKTO: (%s/%s)", a0));
        }

        public void onStopped() {
            YoutubeActivity.setPlaybackState("STOPPED");
            Log.v("Youtube", YoutubeActivity.getPlaybackState());
        }
    }

    final static class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {
        final YoutubeActivity activity;

        private MyPlayerStateChangeListener(YoutubeActivity activity) {
            super();
            this.activity = activity;
        }

        public void onAdStarted() {
            YoutubeActivity.setPlayerState("AD_STARTED");
            Log.v("Youtube", YoutubeActivity.getPlayerState());
        }

        public void onError(YouTubePlayer.ErrorReason reason) {
            YoutubeActivity.setPlayerState("ERROR (" +
                    reason +
                    ")");
            BraveFrontierJNI.videoSkippedCallback();
            YoutubeActivity.getPlayer(activity).release();
            YoutubeActivity.setPlayer(activity, null);
            activity.finish();
            Log.v("Youtube", YoutubeActivity.getPlayerState());
        }

        public void onLoaded(String paramString) {
            YoutubeActivity.setPlayerState(String.format("LOADED %s", paramString));
            Log.v("Youtube", YoutubeActivity.getPlayerState());
        }

        public void onLoading() {
            YoutubeActivity.setPlayerState("LOADING");
            Log.v("Youtube", YoutubeActivity.getPlayerState());
        }

        public void onVideoEnded() {
            YoutubeActivity.setPlayerState("VIDEO_ENDED");
            Log.v("Youtube", YoutubeActivity.getPlayerState());
            BraveFrontierJNI.videoFinishedCallback();
            YoutubeActivity.getPlayer(activity).release();
            YoutubeActivity.setPlayer(activity, null);
            activity.finish();
        }

        public void onVideoStarted() {
            YoutubeActivity.setPlayerState("VIDEO_STARTED");
            Log.v("Youtube", YoutubeActivity.getPlayerState());
        }
    }

    public static String VIDEO_ID = "";
    private static String playbackState = "";
    private static String playerState = "";
    private MyPlaybackEventListener playbackEventListener;
    private YouTubePlayer player;
    private MyPlayerStateChangeListener playerStateChangeListener;

    public YoutubeActivity() {
    }
    
    static String getPlaybackState() {
        return playbackState;
    }
    
    static String setPlaybackState(String playbackState) {
        YoutubeActivity.playbackState = playbackState;
        return playbackState;
    }
    
    static YouTubePlayer getPlayer(YoutubeActivity activity) {
        return activity.player;
    }
    
    static YouTubePlayer setPlayer(YoutubeActivity activity, YouTubePlayer player) {
        activity.player = player;
        return player;
    }
    
    static String formatActivityTime(YoutubeActivity activity, int ms) {
        return activity.formatTime(ms);
    }
    
    static String getPlayerState() {
        return playerState;
    }
    
    static String setPlayerState(String playerState) {
        YoutubeActivity.playerState = playerState;
        return playerState;
    }
    
    private String formatTime(int ms) {
        String hoursStr;
        int seconds = ms / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;
        StringBuilder builder = new StringBuilder();
        if (hours != 0) {
            hoursStr = hours + ":";
        } else {
            hoursStr = "";
        }
        builder.append(hoursStr);
        builder.append(String.format("%02d:%02d", minutes % 60, seconds % 60));
        return builder.toString();
    }
    
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return findViewById(R.id.youtube_view);
    }
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.youtubeview);
        ((YouTubePlayerView)findViewById(R.id.youtube_view)).initialize(DeveloperKey.DEVELOPER_KEY, this);
        playerStateChangeListener = new MyPlayerStateChangeListener(this);
        playbackEventListener = new MyPlaybackEventListener(this);
    }
    
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean unk) {
        this.player = player;
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        player.loadVideo(VIDEO_ID);
        player.setFullscreen(true);
        player.setShowFullscreenButton(false);
        player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
    }
}
