package sg.gumi.bravefrontier;

import android.content.Context;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

abstract public class YouTubeFailureRecoveryActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    final private static int RECOVERY_DIALOG_REQUEST = 1;
    
    public YouTubeFailureRecoveryActivity() {
    }
    
    abstract protected YouTubePlayer.Provider getYouTubePlayerProvider();
    
    
    protected void onActivityResult(int i, int i0, android.content.Intent a) {
        if (i == 1) {
            getYouTubePlayerProvider().initialize(DeveloperKey.DEVELOPER_KEY, this);
        }
    }
    
    public void onInitializationFailure(YouTubePlayer.Provider a, YouTubeInitializationResult result) {
        if (result.isUserRecoverableError()) {
            result.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String fmt = getString(R.string.error_player);
            Toast.makeText(this, String.format(fmt, result), Toast.LENGTH_LONG).show();
        }
    }
}
