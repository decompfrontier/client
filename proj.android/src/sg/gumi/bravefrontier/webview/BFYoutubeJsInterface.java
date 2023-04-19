package sg.gumi.bravefrontier.webview;

final public class BFYoutubeJsInterface implements android.view.View$OnKeyListener {
    final private static int PLAYER_STATE_ENDED = 2;
    final private static int PLAYER_STATE_IDLE = 0;
    final private static int PLAYER_STATE_STARTED = 1;
    private int playerState;
    private String youtubeVideoId;
    
    public BFYoutubeJsInterface() {
        this.youtubeVideoId = null;
        this.playerState = 2;
    }
    
    private void closeWebView() {
        this.playerState = 2;
        sg.gumi.bravefrontier.webview.BFWebView.setWebViewVisible(false);
        this.youtubeVideoId = null;
    }
    
    public String getYoutubeVideoId() {
        String s = this.youtubeVideoId;
        if (s == null) {
            s = "";
        }
        return s;
    }
    
    void initializeYoutubeVideo(String s) {
        this.youtubeVideoId = s;
        this.playerState = 0;
    }
    
    public boolean onKey(android.view.View a, int i, android.view.KeyEvent a0) {
        Throwable a1 = null;
        int i0 = a0.getAction();
        label0: {
            if (i0 == 0 && i == 4) {
                /*monenter(this)*/;
                label5: {
                    label2: {
                        label4: try {
                            int i1 = this.playerState;
                            label3: {
                                if (i1 != 1) {
                                    break label3;
                                }
                                this.closeWebView();
                                sg.gumi.bravefrontier.BraveFrontierJNI.videoSkippedCallback();
                                /*monexit(this)*/;
                                break label4;
                            }
                            label1: {
                                if (i1 != 2) {
                                    break label1;
                                }
                                /*monexit(this)*/;
                                break label2;
                            }
                            /*monexit(this)*/;
                            break label5;
                        } catch(Throwable a2) {
                            a1 = a2;
                            break label0;
                        }
                        return true;
                    }
                    return false;
                }
                return true;
            }
            return false;
        }
        while(true) {
            try {
                /*monexit(this)*/;
            } catch(IllegalMonitorStateException | NullPointerException a3) {
                Throwable a4 = a3;
                a1 = a4;
                continue;
            }
            throw a1;
        }
    }
    
    public void onVideoEnded() {
        /*monenter(this)*/;
        try {
            this.closeWebView();
            sg.gumi.bravefrontier.BraveFrontierJNI.videoFinishedCallback();
            /*monexit(this)*/;
        } catch(Throwable a) {
            Throwable a0 = a;
            while(true) {
                try {
                    /*monexit(this)*/;
                } catch(IllegalMonitorStateException | NullPointerException a1) {
                    Throwable a2 = a1;
                    a0 = a2;
                    continue;
                }
                throw a0;
            }
        }
    }
    
    public void onVideoError() {
        /*monenter(this)*/;
        try {
            this.closeWebView();
            sg.gumi.bravefrontier.BraveFrontierJNI.videoSkippedCallback();
            /*monexit(this)*/;
        } catch(Throwable a) {
            Throwable a0 = a;
            while(true) {
                try {
                    /*monexit(this)*/;
                } catch(IllegalMonitorStateException | NullPointerException a1) {
                    Throwable a2 = a1;
                    a0 = a2;
                    continue;
                }
                throw a0;
            }
        }
    }
    
    public void onVideoPaused() {
        /*monenter(this)*/;
        try {
            this.closeWebView();
            sg.gumi.bravefrontier.BraveFrontierJNI.videoSkippedCallback();
            /*monexit(this)*/;
        } catch(Throwable a) {
            Throwable a0 = a;
            while(true) {
                try {
                    /*monexit(this)*/;
                } catch(IllegalMonitorStateException | NullPointerException a1) {
                    Throwable a2 = a1;
                    a0 = a2;
                    continue;
                }
                throw a0;
            }
        }
    }
    
    public void onVideoStarted() {
        /*monenter(this)*/;
        try {
            this.playerState = 1;
            sg.gumi.bravefrontier.BraveFrontierJNI.videoPreparedCallback();
            /*monexit(this)*/;
        } catch(Throwable a) {
            Throwable a0 = a;
            while(true) {
                try {
                    /*monexit(this)*/;
                } catch(IllegalMonitorStateException | NullPointerException a1) {
                    Throwable a2 = a1;
                    a0 = a2;
                    continue;
                }
                throw a0;
            }
        }
    }
    
    void setAsJsInterface(android.webkit.WebView a) {
        a.setWebChromeClient(new android.webkit.WebChromeClient());
        a.addJavascriptInterface((Object)this, "bfJsInterface");
        a.setOnKeyListener((android.view.View$OnKeyListener)(Object)this);
    }
    
    void stopYoutubeVideo() {
        /*monenter(this)*/;
        label2: {
            label1: try {
                int i = this.playerState;
                label0: {
                    if (i == 2) {
                        break label0;
                    }
                    this.closeWebView();
                    sg.gumi.bravefrontier.BraveFrontierJNI.videoSkippedCallback();
                    /*monexit(this)*/;
                    break label1;
                }
                /*monexit(this)*/;
                break label2;
            } catch(Throwable a) {
                Throwable a0 = a;
                while(true) {
                    try {
                        /*monexit(this)*/;
                    } catch(IllegalMonitorStateException | NullPointerException a1) {
                        Throwable a2 = a1;
                        a0 = a2;
                        continue;
                    }
                    throw a0;
                }
            }
            return;
        }
    }
}
