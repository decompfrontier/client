package sg.gumi.bravefrontier;

abstract public class YouTubeFailureRecoveryActivity extends com.google.android.youtube.player.YouTubeBaseActivity implements com.google.android.youtube.player.YouTubePlayer$OnInitializedListener {
    final private static int RECOVERY_DIALOG_REQUEST = 1;
    
    public YouTubeFailureRecoveryActivity() {
    }
    
    abstract protected com.google.android.youtube.player.YouTubePlayer$Provider getYouTubePlayerProvider();
    
    
    protected void onActivityResult(int i, int i0, android.content.Intent a) {
        if (i == 1) {
            this.getYouTubePlayerProvider().initialize("AIzaSyBpm9ijwTe3-T-4UsW2Z4XsuouGusEDPIE", (com.google.android.youtube.player.YouTubePlayer$OnInitializedListener)(Object)this);
        }
    }
    
    public void onInitializationFailure(com.google.android.youtube.player.YouTubePlayer$Provider a, com.google.android.youtube.player.YouTubeInitializationResult a0) {
        if (a0.isUserRecoverableError()) {
            a0.getErrorDialog((android.app.Activity)this, 1).show();
        } else {
            String s = ((android.app.Activity)this).getString(2131558539);
            Object[] a1 = new Object[1];
            a1[0] = ((Enum)a0).toString();
            android.widget.Toast.makeText((android.content.Context)this, (CharSequence)(Object)String.format(s, a1), 1).show();
        }
    }
}
