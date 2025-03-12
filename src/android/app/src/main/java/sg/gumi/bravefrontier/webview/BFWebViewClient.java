package sg.gumi.bravefrontier.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.MailTo;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import sg.gumi.bravefrontier.BraveFrontier;

public class BFWebViewClient extends android.webkit.WebViewClient {

    class OnPageFinished implements Runnable {
        final BFWebViewClient client;
        final WebView webView;

        OnPageFinished(BFWebViewClient client, WebView webView) {
            super();
            this.client = client;
            this.webView = webView;

        }

        public void run() {
            try {
                webView.scrollBy(0, 1);
            } catch(Throwable ignoredException) {
            }
        }
    }

    final private static String BRAVE_CALL = "bfcall://";
    final private static String CLOSE_WEBVIEW = "closewebview";

    
    public static Intent newEmailIntent(Context context, String email, String subject, String text, String cc) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.EMAIL", email);
        intent.putExtra("android.intent.extra.TEXT", text);
        intent.putExtra("android.intent.extra.SUBJECT", subject);
        intent.putExtra("android.intent.extra.CC", cc);
        intent.setType("message/rfc822");
        return intent;
    }
    
    native public void callBraveMethode(String url);
    
    
    public void onPageFinished(WebView webView, String url) {
        super.onPageFinished(webView, url);
        webView.postDelayed(new OnPageFinished(this, webView), 100L);
    }
    
    public void onPageStarted(WebView webView, String url, Bitmap favicon) {
    }
    
    public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
        super.onReceivedError(webView, errorCode, description, failingUrl);
        BFWebView.getInstance().stopYoutubeVideo();
    }
    
    public void onReceivedError(WebView webView, WebResourceRequest forMainFrame, WebResourceError webResourceError) {
        if (forMainFrame.isForMainFrame()) {
            super.onReceivedError(webView, forMainFrame, webResourceError);
            BFWebView.getInstance().stopYoutubeVideo();
        }
    }
    
    public boolean shouldOverrideUrlLoading(WebView webview, WebResourceRequest webResourceRequest) {
        String s = webResourceRequest.getUrl().toString();
        if (s.startsWith("mailto:")) {
            MailTo mail = MailTo.parse(s);
            Intent intent = BFWebViewClient.newEmailIntent(BraveFrontier.getActivity(), mail.getTo(), mail.getSubject(), mail.getBody(), mail.getCc());
            BraveFrontier.getActivity().startActivity(intent);
            webview.reload();
            return true;
        }
        if (s.startsWith(BRAVE_CALL)) {
            callBraveMethode(s.replace(BRAVE_CALL, ""));
        }
        webview.loadUrl(s);
        return true;
    }
    
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if (url.startsWith("mailto:")) {
            MailTo mailto = MailTo.parse(url);
            Intent intent = BFWebViewClient.newEmailIntent(BraveFrontier.getActivity(), mailto.getTo(), mailto.getSubject(), mailto.getBody(), mailto.getCc());
            BraveFrontier.getActivity().startActivity(intent);
            webView.reload();
            return true;
        }
        if (url.startsWith(BRAVE_CALL)) {
            callBraveMethode(url.replace(BRAVE_CALL, ""));
        }
        webView.loadUrl(url);
        return true;
    }
}
