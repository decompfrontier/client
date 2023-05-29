package sg.gumi.bravefrontier.webview;

import android.util.Log;
import android.view.View;
import static android.os.Build.*;

final public class BFWebView implements View.OnTouchListener, View.OnClickListener {
    final private static String CACHE_DATABASE_FILE = "webviewCache.db";
    final private static String DATABASE_FILE = "webview.db";
    private static sg.gumi.bravefrontier.webview.BFWebView instance;
    private static Boolean usableWebViewDatabase;
    private android.widget.ImageButton browserButton;
    private org.cocos2dx.lib.Cocos2dxActivity cocos2dxActivity;
    private sg.gumi.bravefrontier.webview.BFYoutubeJsInterface jsInterface;
    private float mDownX;
    private int phoneHeight;
    private int phoneWidth;
    private boolean visible;
    private android.webkit.WebView webView;
    private android.widget.RelativeLayout webViewContainer;
    
    static {
    }
    
    private BFWebView() {
        this.cocos2dxActivity = null;
        this.webView = null;
        this.browserButton = null;
        this.webViewContainer = null;
        this.phoneWidth = 0;
        this.phoneHeight = 0;
        this.mDownX = 0.0f;
        this.visible = false;
        this.jsInterface = null;
    }
    
    static void access$100(sg.gumi.bravefrontier.webview.BFWebView a, Object a0) {
        a.showWebViewTask(a0);
    }
    
    static void access$200(sg.gumi.bravefrontier.webview.BFWebView a, Object a0) {
        a.setWebViewVisibilityTask(a0);
    }
    
    static void access$300(sg.gumi.bravefrontier.webview.BFWebView a) {
        a.closeWebViewTaskStep1();
    }
    
    static void access$400(sg.gumi.bravefrontier.webview.BFWebView a) {
        a.closeWebViewTaskStep2();
    }
    
    static void access$500(sg.gumi.bravefrontier.webview.BFWebView a, Object a0) {
        a.playYoutubeVideoTask(a0);
    }
    
    static void access$600(sg.gumi.bravefrontier.webview.BFWebView a, Object a0) {
        a.showBrowserButtonTask(a0);
    }
    
    static void access$700(sg.gumi.bravefrontier.webview.BFWebView a, Object a0) {
        a.setBrowserButtonVisibilityTask(a0);
    }
    
    static void access$800(sg.gumi.bravefrontier.webview.BFWebView a) {
        a.closeBrowserButtonTask();
    }
    
    public static boolean canLaunchUrl(String s) {
        android.content.Intent a = new android.content.Intent("android.intent.action.VIEW");
        a.setData(android.net.Uri.parse(s));
        return sg.gumi.bravefrontier.BraveFrontier.getAppContext().getPackageManager().resolveActivity(a, 65536) != null;
    }
    
    private void closeBrowserButtonTask() {
        android.widget.ImageButton a = this.browserButton;
        if (a != null) {
            a.setVisibility(8);
            this.browserButton.setLayoutParams((android.view.ViewGroup$LayoutParams)sg.gumi.bravefrontier.webview.BFWebView.createHiddenLayoutParams());
        }
    }
    
    public static void closeWebView() {
        sg.gumi.bravefrontier.webview.BFWebView.getInstance().closeWebViewHelper();
    }
    
    private void closeWebViewHelper() {
        if (this.webView == null) {
            sg.gumi.bravefrontier.webview.BFWebView$BFWebViewTask a = new sg.gumi.bravefrontier.webview.BFWebView$BFWebViewTask(this, (sg.gumi.bravefrontier.webview.BFWebView$1)null);
            a.taskType = 103;
            ((android.app.Activity)this.cocos2dxActivity).runOnUiThread((Runnable)(Object)a);
        } else {
            sg.gumi.bravefrontier.webview.BFWebView$BFWebViewTask a0 = new sg.gumi.bravefrontier.webview.BFWebView$BFWebViewTask(this, (sg.gumi.bravefrontier.webview.BFWebView$1)null);
            a0.taskType = 3;
            ((android.app.Activity)this.cocos2dxActivity).runOnUiThread((Runnable)(Object)a0);
            sg.gumi.bravefrontier.webview.BFWebView$BFWebViewTask a1 = new sg.gumi.bravefrontier.webview.BFWebView$BFWebViewTask(this, (sg.gumi.bravefrontier.webview.BFWebView$1)null);
            a1.taskType = 4;
            ((android.app.Activity)this.cocos2dxActivity).runOnUiThread((Runnable)(Object)a1);
        }
    }
    
    private void closeWebViewTaskStep1() {
        this.webView.clearMatches();
        this.webView.clearFormData();
        this.webView.clearAnimation();
        this.webView.clearDisappearingChildren();
        this.webView.clearFocus();
        this.webView.clearHistory();
        this.webView.loadUrl("about:blank");
    }
    
    private void closeWebViewTaskStep2() {
        this.webView.setLayoutParams((android.view.ViewGroup$LayoutParams)sg.gumi.bravefrontier.webview.BFWebView.createHiddenLayoutParams());
    }
    
    private static android.widget.RelativeLayout$LayoutParams createHiddenLayoutParams() {
        android.widget.RelativeLayout$LayoutParams a = new android.widget.RelativeLayout$LayoutParams(0, 0);
        a.alignWithParent = true;
        a.addRule(9);
        a.addRule(10);
        a.leftMargin = 0;
        a.topMargin = 0;
        return a;
    }
    
    private void createWebViewContainer(android.view.View a) {
        android.widget.RelativeLayout a0 = this.webViewContainer;
        if (a0 != null) {
            ((android.widget.FrameLayout)(Object)a0.getParent()).removeView((android.view.View)this.webViewContainer);
            ((android.widget.FrameLayout)((android.app.Activity)this.cocos2dxActivity).findViewById(16908290)).addView((android.view.View)this.webViewContainer);
        } else {
            android.widget.RelativeLayout a1 = new android.widget.RelativeLayout((android.content.Context)this.cocos2dxActivity);
            this.webViewContainer = a1;
            a1.setBackgroundColor(0);
            this.webViewContainer.addView(a);
            this.webViewContainer.setLayoutParams((android.view.ViewGroup$LayoutParams)new android.widget.FrameLayout$LayoutParams(-1, -1));
            ((android.widget.FrameLayout)((android.app.Activity)this.cocos2dxActivity).findViewById(16908290)).addView((android.view.View)this.webViewContainer);
        }
    }
    
    public static sg.gumi.bravefrontier.webview.BFWebView getInstance() {
        if (instance == null) {
            instance = new sg.gumi.bravefrontier.webview.BFWebView();
        }
        return instance;
    }
    
    public static void initialize(org.cocos2dx.lib.Cocos2dxActivity a) {
        sg.gumi.bravefrontier.webview.BFWebView.getInstance().initializeHelper(a);
    }
    
    private void initializeHelper(org.cocos2dx.lib.Cocos2dxActivity a) {
        this.cocos2dxActivity = a;
        if (!sg.gumi.bravefrontier.webview.BFWebView.isUsableWebViewDatabase((android.content.Context)a)) {
            return;
        }
        sg.gumi.bravefrontier.webview.BFWebView.tryWebViewClearCache((android.content.Context)a);
        label0: {
            try {
                android.util.DisplayMetrics a0 = sg.gumi.bravefrontier.BraveFrontier.getAppContext().getResources().getDisplayMetrics();
                this.phoneWidth = a0.widthPixels;
                this.phoneHeight = a0.heightPixels;
                if (this.webView == null) {
                    this.webView = new android.webkit.WebView((android.content.Context)a);
                }
                this.webView.setLayoutParams((android.view.ViewGroup$LayoutParams)sg.gumi.bravefrontier.webview.BFWebView.createHiddenLayoutParams());
                this.webView.getSettings().setJavaScriptEnabled(true);
                this.webView.requestFocus(130);
                this.webView.getSettings().setNeedInitialFocus(false);
                if (android.os.Build$VERSION.SDK_INT > 15) {
                    this.webView.clearCache(true);
                }
                this.webView.setWebViewClient((android.webkit.WebViewClient)new sg.gumi.bravefrontier.webview.BFWebViewClient());
                if (sg.gumi.util.BFConfig.PLATFORM == sg.gumi.util.BFConfig.PLATFORM_AMAZON) {
                    sg.gumi.bravefrontier.webview.BFYoutubeJsInterface a1 = new sg.gumi.bravefrontier.webview.BFYoutubeJsInterface();
                    this.jsInterface = a1;
                    a1.setAsJsInterface(this.webView);
                }
                this.webView.getSettings().setUseWideViewPort(false);
                this.webView.setHorizontalScrollBarEnabled(false);
                this.webView.setOnTouchListener((android.view.View$OnTouchListener)(Object)this);
                this.createWebViewContainer((android.view.View)this.webView);
            } catch(Throwable ignoredException) {
                break label0;
            }
            return;
        }
        this.webView = null;
    }
    
    public static boolean isUsableWebViewDatabase(android.content.Context a) {
        if (usableWebViewDatabase == null) {
            int i = android.os.Build$VERSION.SDK_INT;
            if (i >= 15) {
                if (i != 15) {
                    usableWebViewDatabase = Boolean.TRUE;
                } else {
                    usableWebViewDatabase = Boolean.FALSE;
                }
            } else {
                boolean b = false;
                boolean b0 = sg.gumi.bravefrontier.webview.BFWebView.tryCreateWebViewDatabase(a, "webview.db");
                boolean b1 = sg.gumi.bravefrontier.webview.BFWebView.tryCreateWebViewDatabase(a, "webviewCache.db");
                label2: {
                    label0: {
                        label1: {
                            if (!b0) {
                                break label1;
                            }
                            if (b1) {
                                break label0;
                            }
                        }
                        b = false;
                        break label2;
                    }
                    b = true;
                }
                usableWebViewDatabase = Boolean.valueOf(b);
            }
        }
        return usableWebViewDatabase.booleanValue();
    }
    
    public static boolean isWebViewAvailable() {
        return sg.gumi.bravefrontier.webview.BFWebView.getInstance().webView != null;
    }
    
    public static boolean isWebViewVisible() {
        return sg.gumi.bravefrontier.webview.BFWebView.getInstance().visible;
    }
    
    public static boolean launchNewApplication(String url) {
        label0: {
            try {
                android.content.Intent a = new android.content.Intent("android.intent.action.VIEW");
                a.setData(android.net.Uri.parse(url));
                if (sg.gumi.bravefrontier.BraveFrontier.getAppContext().getPackageManager().resolveActivity(a, 65536) == null) {
                    break label0;
                }
                ((android.app.Activity)sg.gumi.bravefrontier.webview.BFWebView.getInstance().cocos2dxActivity).startActivity(a);
            } catch(Throwable ignoredException) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    public static void launchNewBrowser(String url) {
        try {
            android.content.Intent a = new android.content.Intent("android.intent.action.VIEW");
            a.setData(android.net.Uri.parse(url));
            ((android.app.Activity)sg.gumi.bravefrontier.webview.BFWebView.getInstance().cocos2dxActivity).startActivity(a);
        } catch(Throwable a0) {
            a0.printStackTrace();
        }
    }
    
    public static void playYoutubeVideo(String url) {
        sg.gumi.bravefrontier.webview.BFWebView.getInstance().playYoutubeVideoHelper(url);
    }
    
    private void playYoutubeVideoHelper(String s) {
        StringBuilder a = new StringBuilder();
        a.append("play youtube video: ");
        a.append(s);
        android.util.Log.d("BFWebView", a.toString());
        double d = (double)this.phoneWidth;
        Double.isNaN(d);
        float f = (float)(d / 320.0);
        double d0 = (double)this.phoneHeight;
        Double.isNaN(d0);
        float f0 = Math.min(f, (float)(d0 / 480.0));
        int i = this.phoneWidth;
        int i0 = (int)(((float)i - 320f * f0) / 2f);
        int i1 = this.phoneHeight;
        int i2 = (int)(((float)i1 - f0 * 480f) / 2f);
        this.visible = true;
        sg.gumi.bravefrontier.webview.BFWebView$BFWebViewTask a0 = new sg.gumi.bravefrontier.webview.BFWebView$BFWebViewTask(this, (sg.gumi.bravefrontier.webview.BFWebView$1)null);
        Object[] a1 = new Object[5];
        a1[0] = s;
        a1[1] = Integer.valueOf(i0);
        a1[2] = Integer.valueOf(i2);
        a1[3] = Integer.valueOf(i - i0 * 2);
        a1[4] = Integer.valueOf(i1 - i2 * 2);
        a0.param = a1;
        a0.taskType = 5;
        ((android.app.Activity)this.cocos2dxActivity).runOnUiThread((Runnable)(Object)a0);
    }
    
    private void playYoutubeVideoTask(Object a) {
        if (this.webView == null) {
            sg.gumi.bravefrontier.BraveFrontierJNI.videoSkippedCallback();
            return;
        }
        if (!sg.gumi.bravefrontier.BraveFrontierJNI.existsBundleFile("YT_Player_Android.html")) {
            sg.gumi.bravefrontier.BraveFrontierJNI.videoSkippedCallback();
            return;
        }
        Object[] a0 = (Object[])a;
        sg.gumi.bravefrontier.webview.BFYoutubeJsInterface a1 = this.jsInterface;
        if (a1 != null) {
            a1.initializeYoutubeVideo((String)a0[0]);
        }
        this.webView.loadUrl("file:///android_asset/YT_Player_Android.html");
        this.webView.setBackgroundColor(-16777216);
        android.widget.RelativeLayout$LayoutParams a2 = new android.widget.RelativeLayout$LayoutParams(((Integer)a0[3]).intValue(), ((Integer)a0[4]).intValue());
        a2.alignWithParent = true;
        a2.addRule(9);
        a2.addRule(10);
        a2.leftMargin = ((Integer)a0[1]).intValue();
        a2.topMargin = ((Integer)a0[2]).intValue();
        this.webView.setLayoutParams((android.view.ViewGroup$LayoutParams)a2);
        this.webView.setInitialScale((int)((float)this.phoneWidth / 320f * 100f));
        this.webView.setVisibility(0);
        this.webView.requestFocus();
    }
    
    private void setBrowserButtonVisibilityTask(Object a) {
        boolean b = a == Boolean.TRUE;
        android.widget.FrameLayout a0 = (android.widget.FrameLayout)((android.app.Activity)this.cocos2dxActivity).findViewById(16908290);
        if (!b) {
            a0.requestFocus();
            a0.setFocusableInTouchMode(true);
        }
        this.visible = b;
        android.widget.ImageButton a1 = this.browserButton;
        if (a1 != null) {
            a1.setVisibility(b ? 0 : 8);
            if (!b) {
                a0.requestFocus();
                a0.setFocusableInTouchMode(true);
                this.browserButton.setLayoutParams((android.view.ViewGroup$LayoutParams)sg.gumi.bravefrontier.webview.BFWebView.createHiddenLayoutParams());
            }
        }
    }
    
    private void setWebViewVisibilityTask(Object a) {
        boolean b = a == Boolean.TRUE;
        android.widget.FrameLayout a0 = (android.widget.FrameLayout)((android.app.Activity)this.cocos2dxActivity).findViewById(16908290);
        if (!b) {
            a0.requestFocus();
            a0.setFocusableInTouchMode(true);
        }
        this.visible = b;
        this.webView.setVisibility(b ? 0 : 8);
        if (!b) {
            a0.requestFocus();
            a0.setFocusableInTouchMode(true);
            this.webView.setLayoutParams((android.view.ViewGroup$LayoutParams)sg.gumi.bravefrontier.webview.BFWebView.createHiddenLayoutParams());
        }
    }
    
    public static void setWebViewVisible(boolean visible) {
        sg.gumi.bravefrontier.webview.BFWebView.getInstance().setWebViewVisibleHelper(visible);
    }
    
    private void setWebViewVisibleHelper(boolean b) {
        sg.gumi.bravefrontier.webview.BFWebView$BFWebViewTask a = new sg.gumi.bravefrontier.webview.BFWebView$BFWebViewTask(this, (sg.gumi.bravefrontier.webview.BFWebView$1)null);
        a.param = Boolean.valueOf(b);
        a.taskType = 2;
        if (this.webView == null) {
            a.taskType = 102;
        }
        ((android.app.Activity)this.cocos2dxActivity).runOnUiThread((Runnable)(Object)a);
    }
    
    private void showBrowserButtonTask(Object a) {
        Object[] a0 = (Object[])a;
        if (this.browserButton == null) {
            try {
                android.content.Intent a1 = new android.content.Intent("android.intent.action.VIEW");
                a1.setData(android.net.Uri.parse((String)a0[0]));
                android.content.pm.PackageManager a2 = sg.gumi.bravefrontier.BraveFrontier.getAppContext().getPackageManager();
                android.content.pm.ResolveInfo a3 = a2.resolveActivity(a1, 65536);
                if (a3 != null) {
                    android.graphics.drawable.Drawable a4 = a3.loadIcon(a2);
                    if (a4 != null) {
                        android.widget.ImageButton a5 = new android.widget.ImageButton((android.content.Context)this.cocos2dxActivity);
                        this.browserButton = a5;
                        a5.setImageDrawable(a4);
                    }
                }
            } catch(Throwable ignoredException) {
            }
            if (this.browserButton == null) {
                android.widget.ImageButton a6 = new android.widget.ImageButton((android.content.Context)this.cocos2dxActivity);
                this.browserButton = a6;
                a6.setImageResource(17301585);
            }
            this.browserButton.setLayoutParams((android.view.ViewGroup$LayoutParams)sg.gumi.bravefrontier.webview.BFWebView.createHiddenLayoutParams());
            this.createWebViewContainer((android.view.View)this.browserButton);
        }
        if (this.browserButton != null) {
            android.widget.RelativeLayout$LayoutParams a7 = new android.widget.RelativeLayout$LayoutParams(-2, -2);
            a7.alignWithParent = true;
            a7.addRule(13);
            this.browserButton.setLayoutParams((android.view.ViewGroup$LayoutParams)a7);
            String s = (String)a0[0];
            label0: {
                label1: {
                    if (s == null) {
                        break label1;
                    }
                    if (!s.isEmpty()) {
                        break label0;
                    }
                }
                s = "about:blank";
            }
            this.browserButton.setTag((Object)s);
            this.browserButton.setOnClickListener((android.view.View$OnClickListener)(Object)this);
            this.browserButton.setVisibility(0);
        }
    }
    
    public static void showWebView(String s, float f, float f0, float f1, float f2) {
        sg.gumi.bravefrontier.webview.BFWebView.getInstance().showWebViewHelper(s, f, f0, f1, f2);
    }
    
    private void showWebViewHelper(String s, float f, float f0, float f1, float f2) {
        StringBuilder a = new StringBuilder();
        a.append("Showing webview: ");
        a.append(s);
        Log.d("BFWebView", a.toString());
        double d = (double)this.phoneWidth;
        Double.isNaN(d);
        float f3 = (float)(d / 320.0);
        double d0 = (double)this.phoneHeight;
        Double.isNaN(d0);
        float f4 = Math.min(f3, (float)(d0 / 480.0));
        int i = (int)(((float)this.phoneWidth - 320f * f4) / 2f);
        int i0 = (int)(((float)this.phoneHeight - 480f * f4) / 2f);
        float f5 = (float)(int)(f * f4);
        if (VERSION.SDK_INT < 19) {
            f5 = f5 + (float)i;
        }
        float f6 = (float)(int)(f1 * f4);
        float f7 = (float)(int)(f0 * f4);
        if (VERSION.SDK_INT < 19) {
            f7 = f7 + (float)i0;
        }
        float f8 = (float)(int)(f2 * f4);
        this.visible = true;
        sg.gumi.bravefrontier.webview.BFWebView$BFWebViewTask a0 = new sg.gumi.bravefrontier.webview.BFWebView$BFWebViewTask(this, (sg.gumi.bravefrontier.webview.BFWebView$1)null);
        Object[] a1 = new Object[5];
        a1[0] = s;
        a1[1] = Integer.valueOf((int)f5);
        a1[2] = Integer.valueOf((int)f7);
        a1[3] = Integer.valueOf((int)f6);
        a1[4] = Integer.valueOf((int)f8);
        a0.param = a1;
        a0.taskType = 1;
        if (this.webView == null) {
            a0.taskType = 101;
        }
        cocos2dxActivity.runOnUiThread((Runnable)(Object)a0);
    }
    
    private void showWebViewTask(Object a) {
        Object[] a0 = (Object[])a;
        java.util.HashMap a1 = new java.util.HashMap();
        ((java.util.Map)(Object)a1).put((Object)"X-Device-Platform", (Object)"Android");
        this.webView.loadUrl((String)a0[0], (java.util.Map)(Object)a1);
        this.webView.setBackgroundColor(-16777216);
        android.widget.RelativeLayout$LayoutParams a2 = new android.widget.RelativeLayout$LayoutParams(((Integer)a0[3]).intValue(), ((Integer)a0[4]).intValue());
        a2.alignWithParent = true;
        a2.addRule(9);
        a2.addRule(10);
        a2.leftMargin = ((Integer)a0[1]).intValue();
        a2.topMargin = ((Integer)a0[2]).intValue();
        this.webView.setLayoutParams((android.view.ViewGroup$LayoutParams)a2);
        this.webView.setInitialScale((int)((float)this.phoneWidth / 320f * 100f));
        this.webView.setVisibility(0);
    }
    
    private static boolean tryCreateWebViewDatabase(android.content.Context a, String s) {
        android.database.sqlite.SQLiteDatabase a0 = null;
        try {
            a0 = a.openOrCreateDatabase(s, 0, (android.database.sqlite.SQLiteDatabase$CursorFactory)null);
        } catch(Throwable ignoredException) {
            a0 = null;
        }
        if (a0 == null) {
            try {
                a.deleteDatabase(s);
            } catch(Throwable ignoredException0) {
            }
            try {
                a.deleteFile(s);
            } catch(Throwable ignoredException1) {
            }
            try {
                a0 = a.openOrCreateDatabase(s, 0, (android.database.sqlite.SQLiteDatabase$CursorFactory)null);
            } catch(Throwable ignoredException2) {
            }
        }
        label0: if (a0 != null) {
            try {
                a0.close();
            } catch(Throwable ignoredException3) {
                break label0;
            }
            return true;
        }
        return false;
    }
    
    private static void tryWebViewClearCache(android.content.Context a) {
        label0: if (android.os.Build$VERSION.SDK_INT <= 15) {
            try {
                android.database.sqlite.SQLiteDatabase a0 = null;
                label2: {
                    label1: {
                        label3: {
                            try {
                                a0 = a.openOrCreateDatabase("webviewCache.db", 0, (android.database.sqlite.SQLiteDatabase$CursorFactory)null);
                                break label3;
                            } catch(Throwable ignoredException) {
                            }
                            a0 = null;
                            break label1;
                        }
                        if (a0 == null) {
                            break label2;
                        }
                        try {
                            a0.delete("cache", (String)null, (String[])null);
                        } catch(Throwable ignoredException0) {
                            break label1;
                        }
                        break label2;
                    }
                    if (a0 == null) {
                        break label0;
                    }
                    a0.close();
                    break label0;
                }
                if (a0 != null) {
                    a0.close();
                }
            } catch(Throwable ignoredException1) {
            }
        }
    }
    
    public void onClick(android.view.View a) {
        android.widget.ImageButton a0 = this.browserButton;
        if (a0 == a) {
            sg.gumi.bravefrontier.webview.BFWebView.launchNewBrowser((String)a0.getTag());
        }
    }
    
    public boolean onTouch(android.view.View a, android.view.MotionEvent a0) {
        int i = a0.getAction();
        label1: if (i == 0) {
            if (!a.hasFocus()) {
                a.requestFocus();
            }
            this.mDownX = a0.getX();
        } else {
            label0: {
                if (i == 1) {
                    break label0;
                }
                if (i == 2) {
                    break label0;
                }
                if (i == 3) {
                    break label0;
                }
                break label1;
            }
            if (!a.hasFocus()) {
                a.requestFocus();
            }
            a0.setLocation(this.mDownX, a0.getY());
        }
        return false;
    }
    
    public void stopYoutubeVideo() {
        sg.gumi.bravefrontier.webview.BFYoutubeJsInterface a = this.jsInterface;
        if (a != null) {
            a.stopYoutubeVideo();
        }
    }
}
