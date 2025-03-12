package sg.gumi.bravefrontier.webview;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import sg.gumi.bravefrontier.BraveFrontierJNI;

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
        if (keyEvent.getAction() == 0 && keyCode == 4) {
            synchronized (this)  {
                if (playerState == PLAYER_STATE_STARTED) {
                    closeWebView();
                    BraveFrontierJNI.videoSkippedCallback();
                    return true;
                }

                if (playerState != PLAYER_STATE_ENDED) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void onVideoEnded() {
        synchronized (this) {
            closeWebView();
            BraveFrontierJNI.videoFinishedCallback();
        }
    }
    
    public void onVideoError() {
        synchronized (this) {
            closeWebView();
            BraveFrontierJNI.videoSkippedCallback();
        }
    }
    
    public void onVideoPaused() {
        synchronized (this) {
            closeWebView();
            BraveFrontierJNI.videoSkippedCallback();
        }
    }
    
    public void onVideoStarted() {
        synchronized (this) {
            this.playerState = PLAYER_STATE_STARTED;
            BraveFrontierJNI.videoPreparedCallback();
        }
    }

    @JavascriptInterface
    public void setAsJsInterface(WebView webView) {
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(this, "bfJsInterface");
        webView.setOnKeyListener(this);
    }
    
    void stopYoutubeVideo() {
        synchronized (this) {
            if (playerState == PLAYER_STATE_ENDED) {
                return;
            }
            closeWebView();
            BraveFrontierJNI.videoSkippedCallback();
        }
    }
}
