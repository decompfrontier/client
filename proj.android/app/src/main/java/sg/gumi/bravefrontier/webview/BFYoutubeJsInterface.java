package sg.gumi.bravefrontier.webview;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import sg.gumi.bravefrontier.BraveFrontierJNI;

/*
    TODO: THE METHODS OF THIS CLASS ARE NOT ACCURATE!!!! MISSING SYMC OBJECTS
 */

final public class BFYoutubeJsInterface implements View.OnKeyListener {
    final private static int PLAYER_STATE_ENDED = 2;
    final private static int PLAYER_STATE_IDLE = 0;
    final private static int PLAYER_STATE_STARTED = 1;
    private int playerState;
    private String youtubeVideoId;
    
    public BFYoutubeJsInterface() {
        youtubeVideoId = null;
        playerState = PLAYER_STATE_ENDED;
    }
    
    private void closeWebView() {
        playerState = PLAYER_STATE_ENDED;
        BFWebView.setWebViewVisible(false);
        youtubeVideoId = null;
    }
    
    public String getYoutubeVideoId() {
        if (youtubeVideoId == null) {
            return "";
        }
        return youtubeVideoId;
    }
    
    void initializeYoutubeVideo(String videoId) {
        youtubeVideoId = videoId;
        playerState = PLAYER_STATE_IDLE;
    }
    
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        /* TODO: INACCURATE!!! */

        if (keyEvent.getAction() == 0 && keyCode == 4) {
            try {
                if (playerState == PLAYER_STATE_STARTED) {
                    closeWebView();
                    BraveFrontierJNI.videoSkippedCallback();
                    return true;
                }

                if (playerState != PLAYER_STATE_ENDED) {
                    return true;
                }
            } catch (Throwable ex) {
                throw ex;
            }
        }
        return false;
    }
    
    public void onVideoEnded() {
        /* TODO: INACCURATE!!! */
        try {
            closeWebView();
            BraveFrontierJNI.videoFinishedCallback();
        } catch(Throwable ex) {
            throw ex;
        }
    }
    
    public void onVideoError() {
        /* TODO: INACCURATE!!! */
        try {
            closeWebView();
            BraveFrontierJNI.videoSkippedCallback();
        } catch(Throwable ex) {
            throw ex;
        }
    }
    
    public void onVideoPaused() {
        /* TODO: INACCURATE!!! */
        try {
            closeWebView();
            BraveFrontierJNI.videoSkippedCallback();
        } catch(Throwable ex) {
            throw ex;
        }
    }
    
    public void onVideoStarted() {
        /* TODO: INACCURATE!!! */
        try {
            this.playerState = PLAYER_STATE_STARTED;
            BraveFrontierJNI.videoPreparedCallback();
        } catch(Throwable ex) {
            throw ex;
        }
    }
    
    @SuppressLint("JavascriptInterface") // TODO: REMOVE THIS
    void setAsJsInterface(WebView webView) {
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(this, "bfJsInterface");
        webView.setOnKeyListener(this);
    }
    
    void stopYoutubeVideo() {
        /* TODO: INACCURATE!!! */
        try {
            if (playerState == PLAYER_STATE_ENDED) {
                return;
            }
            closeWebView();
            sg.gumi.bravefrontier.BraveFrontierJNI.videoSkippedCallback();
        } catch(Throwable ex) {
            throw ex;
        }
    }
}
