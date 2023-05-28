package sg.gumi.bravefrontier.webview;

class BFWebViewClient$1 implements Runnable {
    final sg.gumi.bravefrontier.webview.BFWebViewClient this$0;
    final android.webkit.WebView val$webView;
    
    BFWebViewClient$1(sg.gumi.bravefrontier.webview.BFWebViewClient a, android.webkit.WebView a0) {
        this.this$0 = a;
        this.val$webView = a0;
        super();
    }
    
    public void run() {
        try {
            this.val$webView.scrollBy(0, 1);
        } catch(Throwable ignoredException) {
        }
    }
}
