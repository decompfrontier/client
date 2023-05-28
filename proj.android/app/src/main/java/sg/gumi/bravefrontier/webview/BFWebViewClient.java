package sg.gumi.bravefrontier.webview;

public class BFWebViewClient extends android.webkit.WebViewClient {
    final private static String BRAVE_CALL = "bfcall://";
    final private static String CLOSE_WEBVIEW = "closewebview";
    
    public BFWebViewClient() {
    }
    
    public static android.content.Intent newEmailIntent(android.content.Context a, String s, String s0, String s1, String s2) {
        android.content.Intent a0 = new android.content.Intent("android.intent.action.SEND");
        String[] a1 = new String[1];
        a1[0] = s;
        a0.putExtra("android.intent.extra.EMAIL", a1);
        a0.putExtra("android.intent.extra.TEXT", s1);
        a0.putExtra("android.intent.extra.SUBJECT", s0);
        a0.putExtra("android.intent.extra.CC", s2);
        a0.setType("message/rfc822");
        return a0;
    }
    
    native public void callBraveMethode(String arg);
    
    
    public void onPageFinished(android.webkit.WebView a, String s) {
        ((android.webkit.WebViewClient)this).onPageFinished(a, s);
        a.postDelayed((Runnable)(Object)new sg.gumi.bravefrontier.webview.BFWebViewClient$1(this, a), 100L);
    }
    
    public void onPageStarted(android.webkit.WebView a, String s, android.graphics.Bitmap a0) {
    }
    
    public void onReceivedError(android.webkit.WebView a, int i, String s, String s0) {
        ((android.webkit.WebViewClient)this).onReceivedError(a, i, s, s0);
        sg.gumi.bravefrontier.webview.BFWebView.getInstance().stopYoutubeVideo();
    }
    
    public void onReceivedError(android.webkit.WebView a, android.webkit.WebResourceRequest a0, android.webkit.WebResourceError a1) {
        if (a0.isForMainFrame()) {
            ((android.webkit.WebViewClient)this).onReceivedError(a, a0, a1);
            sg.gumi.bravefrontier.webview.BFWebView.getInstance().stopYoutubeVideo();
        }
    }
    
    public boolean shouldOverrideUrlLoading(android.webkit.WebView a, android.webkit.WebResourceRequest a0) {
        String s = a0.getUrl().toString();
        if (s.startsWith("mailto:")) {
            android.net.MailTo a1 = android.net.MailTo.parse(s);
            android.content.Intent a2 = sg.gumi.bravefrontier.webview.BFWebViewClient.newEmailIntent((android.content.Context)sg.gumi.bravefrontier.BraveFrontier.getActivity(), a1.getTo(), a1.getSubject(), a1.getBody(), a1.getCc());
            ((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity()).startActivity(a2);
            a.reload();
            return true;
        }
        if (s.startsWith("bfcall://")) {
            this.callBraveMethode(s.replace((CharSequence)(Object)"bfcall://", (CharSequence)(Object)""));
        }
        a.loadUrl(s);
        return true;
    }
    
    public boolean shouldOverrideUrlLoading(android.webkit.WebView a, String s) {
        if (s.startsWith("mailto:")) {
            android.net.MailTo a0 = android.net.MailTo.parse(s);
            android.content.Intent a1 = sg.gumi.bravefrontier.webview.BFWebViewClient.newEmailIntent((android.content.Context)sg.gumi.bravefrontier.BraveFrontier.getActivity(), a0.getTo(), a0.getSubject(), a0.getBody(), a0.getCc());
            ((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity()).startActivity(a1);
            a.reload();
            return true;
        }
        if (s.startsWith("bfcall://")) {
            this.callBraveMethode(s.replace((CharSequence)(Object)"bfcall://", (CharSequence)(Object)""));
        }
        a.loadUrl(s);
        return true;
    }
}
