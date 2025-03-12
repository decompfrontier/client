package sg.gumi.bravefrontier.webview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import org.cocos2dx.lib.Cocos2dxActivity;
import sg.gumi.bravefrontier.BraveFrontier;
import sg.gumi.bravefrontier.BraveFrontierJNI;
import sg.gumi.bravefrontier.R;
import sg.gumi.util.BFConfig;

import java.util.HashMap;

import static android.os.Build.*;

final public class BFWebView implements View.OnTouchListener, View.OnClickListener {
    final private static String CACHE_DATABASE_FILE = "webviewCache.db";
    final private static String DATABASE_FILE = "webview.db";
    private static BFWebView instance;
    private static Boolean usableWebViewDatabase;
    private ImageButton browserButton;
    private Cocos2dxActivity cocos2dxActivity;
    private BFYoutubeJsInterface jsInterface;
    private float mDownX;
    private int phoneHeight;
    private int phoneWidth;
    private boolean visible;
    private WebView webView;
    private RelativeLayout webViewContainer;

    static class UnkClass {
    }


    final static class BFWebViewTask implements Runnable {
        final static int TASK_TYPE_CLOSE_BROWSER_BUTTON = 103;
        final static int TASK_TYPE_CLOSE_WEBVIEW_STEP_1 = 3;
        final static int TASK_TYPE_CLOSE_WEBVIEW_STEP_2 = 4;
        final static int TASK_TYPE_PLAY_YOUTUBE_VIDEO = 5;
        final static int TASK_TYPE_SET_BROWSER_BUTTON_VISIBILITY = 102;
        final static int TASK_TYPE_SET_WEBVIEW_VISIBILITY = 2;
        final static int TASK_TYPE_SHOW_BROWSER_BUTTON = 101;
        final static int TASK_TYPE_SHOW_WEBVIEW = 1;
        Object param;
        int taskType;
        final BFWebView webView;

        private BFWebViewTask(BFWebView webView) {
            super();
            this.webView = webView;
            this.param = null;
        }

        BFWebViewTask(BFWebView webView, UnkClass param) {
            this(webView);
        }

        public void run() {
            if (taskType == TASK_TYPE_SHOW_WEBVIEW) {
                BFWebView.showWebViewTask(webView, param);
            } else if (taskType == TASK_TYPE_SET_WEBVIEW_VISIBILITY) {
                BFWebView.setWebViewVisibilityTask(webView, param);
            } else if (taskType == TASK_TYPE_CLOSE_WEBVIEW_STEP_1) {
                BFWebView.closeWebViewTaskStep1(webView);
            } else if (taskType == TASK_TYPE_CLOSE_WEBVIEW_STEP_2) {
                BFWebView.closeWebViewTaskStep2(webView);
            } else if (taskType == TASK_TYPE_PLAY_YOUTUBE_VIDEO) {
                BFWebView.playYoutubeVideo(webView, param);
            } else {
                switch(taskType) {
                    case TASK_TYPE_CLOSE_BROWSER_BUTTON: {
                        BFWebView.closeBrowserButtonTask(webView);
                        break;
                    }
                    case TASK_TYPE_SET_BROWSER_BUTTON_VISIBILITY: {
                        BFWebView.setBrowserButtonVisibilityTask(webView, param);
                        break;
                    }
                    case TASK_TYPE_SHOW_BROWSER_BUTTON: {
                        BFWebView.showBrowserButtonTask(webView, param);
                        break;
                    }
                }
            }
        }
    }

    
    private BFWebView() {
        cocos2dxActivity = null;
        webView = null;
        browserButton = null;
        webViewContainer = null;
        phoneWidth = 0;
        phoneHeight = 0;
        mDownX = 0.0f;
        visible = false;
        jsInterface = null;
    }
    
    static void showWebViewTask(BFWebView webView, Object param) {
        webView.showWebViewTask(param);
    }
    
    static void setWebViewVisibilityTask(BFWebView webView, Object param) {
        webView.setWebViewVisibilityTask(param);
    }
    
    static void closeWebViewTaskStep1(BFWebView webView) {
        webView.closeWebViewTaskStep1();
    }
    
    static void closeWebViewTaskStep2(BFWebView webView) {
        webView.closeWebViewTaskStep2();
    }
    
    static void playYoutubeVideo(BFWebView webView, Object param) {
        webView.playYoutubeVideoTask(param);
    }
    
    static void showBrowserButtonTask(BFWebView webView, Object param) {
        webView.showBrowserButtonTask(param);
    }
    
    static void setBrowserButtonVisibilityTask(BFWebView webView, Object param) {
        webView.setBrowserButtonVisibilityTask(param);
    }
    
    static void closeBrowserButtonTask(BFWebView webView) {
        webView.closeBrowserButtonTask();
    }
    
    public static boolean canLaunchUrl(String url) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(url));
        return BraveFrontier.getAppContext().getPackageManager().resolveActivity(intent, 65536) != null;
    }
    
    private void closeBrowserButtonTask() {
        if (browserButton != null) {
            browserButton.setVisibility(View.GONE);
            browserButton.setLayoutParams(BFWebView.createHiddenLayoutParams());
        }
    }
    
    public static void closeWebView() {
        BFWebView.getInstance().closeWebViewHelper();
    }
    
    private void closeWebViewHelper() {
        if (webView == null) {
            BFWebViewTask webView = new BFWebViewTask(this, null);
            webView.taskType = 103;
            cocos2dxActivity.runOnUiThread(webView);
        } else {
            BFWebViewTask viewTask1 = new BFWebViewTask(this, null);
            viewTask1.taskType = 3;
            cocos2dxActivity.runOnUiThread(viewTask1);
            BFWebViewTask viewTask = new BFWebViewTask(this, null);
            viewTask.taskType = 4;
            cocos2dxActivity.runOnUiThread(viewTask);
        }
    }
    
    private void closeWebViewTaskStep1() {
        webView.clearMatches();
        webView.clearFormData();
        webView.clearAnimation();
        webView.clearDisappearingChildren();
        webView.clearFocus();
        webView.clearHistory();
        webView.loadUrl("about:blank");
    }
    
    private void closeWebViewTaskStep2() {
        webView.setLayoutParams(BFWebView.createHiddenLayoutParams());
    }
    
    private static RelativeLayout.LayoutParams createHiddenLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
        params.alignWithParent = true;
        params.addRule(9);
        params.addRule(10);
        params.leftMargin = 0;
        params.topMargin = 0;
        return params;
    }
    
    private void createWebViewContainer(View view) {
        if (webViewContainer != null) {
            ((FrameLayout)webViewContainer.getParent()).removeView(webViewContainer);
            ((FrameLayout)cocos2dxActivity.findViewById(R.id.content)).addView(webViewContainer);
        } else {
            webViewContainer = new RelativeLayout(cocos2dxActivity);
            webViewContainer.setBackgroundColor(0);
            webViewContainer.addView(view);
            webViewContainer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            ((android.widget.FrameLayout)(cocos2dxActivity.findViewById(R.id.content))).addView(webViewContainer);
        }
    }
    
    public static BFWebView getInstance() {
        if (instance == null) {
            instance = new BFWebView();
        }
        return instance;
    }
    
    public static void initialize(Cocos2dxActivity activity) {
        BFWebView.getInstance().initializeHelper(activity);
    }
    
    private void initializeHelper(Cocos2dxActivity activity) {
        cocos2dxActivity = activity;
        if (!BFWebView.isUsableWebViewDatabase(activity)) {
            return;
        }
        BFWebView.tryWebViewClearCache(activity);
        try {
            DisplayMetrics metrics = BraveFrontier.getAppContext().getResources().getDisplayMetrics();
            phoneWidth = metrics.widthPixels;
            phoneHeight = metrics.heightPixels;
            if (webView == null) {
                webView = new WebView(activity);
            }
            webView.setLayoutParams(BFWebView.createHiddenLayoutParams());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.requestFocus(130);
            webView.getSettings().setNeedInitialFocus(false);
            if (Build.VERSION.SDK_INT > 15) {
                webView.clearCache(true);
            }
            webView.setWebViewClient(new BFWebViewClient());
            if (BFConfig.PLATFORM == BFConfig.PLATFORM_AMAZON) {
                jsInterface = new BFYoutubeJsInterface();
                jsInterface.setAsJsInterface(webView);
            }
            webView.getSettings().setUseWideViewPort(false);
            webView.setHorizontalScrollBarEnabled(false);
            webView.setOnTouchListener(this);
            createWebViewContainer(webView);
        } catch(Throwable ignoredException) {
            webView = null;
        }
    }
    
    public static boolean isUsableWebViewDatabase(Context context) {
        if (usableWebViewDatabase == null) {
            if (Build.VERSION.SDK_INT >= 15) {
                if (Build.VERSION.SDK_INT != 15) {
                    usableWebViewDatabase = Boolean.TRUE;
                } else {
                    usableWebViewDatabase = Boolean.FALSE;
                }
            } else {
                if (!BFWebView.tryCreateWebViewDatabase(context, DATABASE_FILE)) {
                    usableWebViewDatabase = false;
                    return usableWebViewDatabase;
                }
                if (BFWebView.tryCreateWebViewDatabase(context, CACHE_DATABASE_FILE)) {
                    usableWebViewDatabase = true;
                    return usableWebViewDatabase;
                }

                usableWebViewDatabase = false;
            }
        }
        return usableWebViewDatabase;
    }
    
    public static boolean isWebViewAvailable() {
        return BFWebView.getInstance().webView != null;
    }
    
    public static boolean isWebViewVisible() {
        return BFWebView.getInstance().visible;
    }
    
    public static boolean launchNewApplication(String url) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            if (BraveFrontier.getAppContext().getPackageManager().resolveActivity(intent, 65536) == null) {
                return false;
            }
            BFWebView.getInstance().cocos2dxActivity.startActivity(intent);
        } catch(Throwable ignoredException) {
            return false;
        }
        return true;
    }
    
    public static void launchNewBrowser(String url) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            BFWebView.getInstance().cocos2dxActivity.startActivity(intent);
        } catch(Throwable ex) {
            ex.printStackTrace();
        }
    }
    
    public static void playYoutubeVideo(String url) {
        BFWebView.getInstance().playYoutubeVideoHelper(url);
    }
    
    private void playYoutubeVideoHelper(String url) {
        Log.d("BFWebView", "play youtube video: " + url);
        float calcW = (float)(phoneWidth / 320.0);
        float calcH = Math.min(calcW, (float)(phoneHeight / 480.0));
        int viewWidth = (int)(((float)phoneWidth - 320f * calcH) / 2f);
        int viewHeight = (int)(((float)phoneHeight - calcH * 480f) / 2f);

        visible = true;
        BFWebViewTask webViewTask = new BFWebViewTask(this, null);
        Object[] params = new Object[5];
        params[0] = url;
        params[1] = viewWidth;
        params[2] = viewHeight;
        params[3] = phoneWidth - viewWidth * 2;
        params[4] = phoneHeight - viewHeight * 2;
        webViewTask.param = params;
        webViewTask.taskType = 5;
        cocos2dxActivity.runOnUiThread(webViewTask);
    }
    
    private void playYoutubeVideoTask(Object param) {
        if (webView == null) {
            BraveFrontierJNI.videoSkippedCallback();
            return;
        }
        if (!BraveFrontierJNI.existsBundleFile("YT_Player_Android.html")) {
            BraveFrontierJNI.videoSkippedCallback();
            return;
        }
        Object[] paramConv = (Object[])param;
        if (jsInterface != null) {
            jsInterface.initializeYoutubeVideo((String)paramConv[0]);
        }
        webView.loadUrl("file:///android_asset/YT_Player_Android.html");
        webView.setBackgroundColor(-16777216);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)paramConv[3], (int)paramConv[4]);
        layoutParams.alignWithParent = true;
        layoutParams.addRule(9);
        layoutParams.addRule(10);
        layoutParams.leftMargin = (int)paramConv[1];
        layoutParams.topMargin = (int)paramConv[2];
        webView.setLayoutParams(layoutParams);
        webView.setInitialScale((int)((float)this.phoneWidth / 320f * 100f));
        webView.setVisibility(View.VISIBLE);
        webView.requestFocus();
    }
    
    private void setBrowserButtonVisibilityTask(Object param) {
        boolean isVisible = param == Boolean.TRUE;

        FrameLayout frameLayout = cocos2dxActivity.findViewById(R.id.content);
        if (!isVisible) {
            frameLayout.requestFocus();
            frameLayout.setFocusableInTouchMode(true);
        }
        visible = isVisible;
        ImageButton imgButton = this.browserButton;
        if (imgButton != null) {
            imgButton.setVisibility(isVisible ? View.VISIBLE : View.GONE);
            if (!isVisible) {
                frameLayout.requestFocus();
                frameLayout.setFocusableInTouchMode(true);
                browserButton.setLayoutParams(BFWebView.createHiddenLayoutParams());
            }
        }
    }
    
    private void setWebViewVisibilityTask(Object param) {
        boolean visible = param == Boolean.TRUE;
        FrameLayout frameLayout = this.cocos2dxActivity.findViewById(R.id.content);
        if (!visible) {
            frameLayout.requestFocus();
            frameLayout.setFocusableInTouchMode(true);
        }
        visible = visible;
        webView.setVisibility(visible ? View.VISIBLE : View.GONE);
        if (!visible) {
            frameLayout.requestFocus();
            frameLayout.setFocusableInTouchMode(true);
            webView.setLayoutParams(BFWebView.createHiddenLayoutParams());
        }
    }
    
    public static void setWebViewVisible(boolean visible) {
        BFWebView.getInstance().setWebViewVisibleHelper(visible);
    }
    
    private void setWebViewVisibleHelper(boolean b) {
        BFWebViewTask viewTask = new BFWebViewTask(this, null);
        viewTask.param = b;
        viewTask.taskType = 2;
        if (webView == null) {
            viewTask.taskType = 102;
        }
        cocos2dxActivity.runOnUiThread(viewTask);
    }
    
    private void showBrowserButtonTask(Object param) {
        Object[] paramList = (Object[])param;
        if (browserButton == null) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse((String)paramList[0]));
                PackageManager pm = BraveFrontier.getAppContext().getPackageManager();
                ResolveInfo resolveInfo = pm.resolveActivity(intent, 65536);
                if (resolveInfo != null) {
                    Drawable icon = resolveInfo.loadIcon(pm);
                    if (icon != null) {
                        browserButton = new ImageButton(cocos2dxActivity);
                        browserButton.setImageDrawable(icon);
                    }
                }
            } catch(Throwable ignoredException) {
            }
            if (browserButton == null) {
                browserButton = new ImageButton(cocos2dxActivity);
                browserButton.setImageResource(android.R.drawable.ic_menu_set_as);
            }
            browserButton.setLayoutParams(BFWebView.createHiddenLayoutParams());
            createWebViewContainer(browserButton);
        }
        if (browserButton != null) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.alignWithParent = true;
            layoutParams.addRule(13);
            browserButton.setLayoutParams(layoutParams);
            String page = (String)paramList[0];
            if (page == null) {
                page = "about:blank";
            }
            if (page.isEmpty()) {
                page = "about:blank";
            }
            browserButton.setTag(page);
            browserButton.setOnClickListener(this);
            browserButton.setVisibility(View.VISIBLE);
        }
    }
    
    public static void showWebView(String url, float f, float f0, float f1, float f2) {
        BFWebView.getInstance().showWebViewHelper(url, f, f0, f1, f2);
    }
    
    private void showWebViewHelper(String url, float f, float f0, float f1, float f2) {
        Log.d("BFWebView", "Showing webview: " + url);
        float calcW = (float)(phoneWidth / 320.0);
        float calcH = Math.min(calcW, (float)(phoneHeight / 480.0));
        int i = (int)(((float)this.phoneWidth - 320f * calcH) / 2f);
        int i0 = (int)(((float)this.phoneHeight - 480f * calcH) / 2f);
        float topLeft = (float)(int)(f * calcH);
        if (VERSION.SDK_INT < 19) {
            topLeft = topLeft + (float)i;
        }
        float f6 = (float)(int)(f1 * calcH);
        float topRight = (float)(int)(f0 * calcH);
        if (VERSION.SDK_INT < 19) {
            topRight = topRight + (float)i0;
        }
        float f8 = (float)(int)(f2 * calcH);
        this.visible = true;
        BFWebViewTask a0 = new BFWebViewTask(this, null);
        Object[] params = new Object[5];
        params[0] = url;
        params[1] = (int)topLeft;
        params[2] = (int)topRight;
        params[3] = (int) f6;
        params[4] = (int) f8;
        a0.param = params;
        a0.taskType = 1;
        if (this.webView == null) {
            a0.taskType = 101;
        }
        cocos2dxActivity.runOnUiThread((Runnable)(Object)a0);
    }
    
    private void showWebViewTask(Object param) {
        Object[] params = (Object[])param;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("X-Device-Platform", "Android");
        webView.loadUrl((String)params[0], headers);
        webView.setBackgroundColor(-16777216);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)params[3], (int)params[4]);
        layoutParams.alignWithParent = true;
        layoutParams.addRule(9);
        layoutParams.addRule(10);
        layoutParams.leftMargin = (int)params[1];
        layoutParams.topMargin = (int)params[2];
        this.webView.setLayoutParams(layoutParams);
        this.webView.setInitialScale((int)((float)this.phoneWidth / 320f * 100f));
        this.webView.setVisibility(View.VISIBLE);
    }
    
    private static boolean tryCreateWebViewDatabase(Context context, String fileName) {
        SQLiteDatabase db = null;
        try {
            db = context.openOrCreateDatabase(fileName, 0, null);
        } catch(Throwable ignoredException) {
            try {
                context.deleteDatabase(fileName);
            } catch(Throwable ignoredException0) {
            }
            try {
                context.deleteFile(fileName);
            } catch(Throwable ignoredException1) {
            }
            try {
                db = context.openOrCreateDatabase(fileName, 0, null);
            } catch(Throwable ignoredException2) {
            }
        }

        if (db != null) {
            try {
                db.close();
            } catch(Throwable ignoredException3) {
                return false;
            }
        }
        return true;
    }
    
    private static void tryWebViewClearCache(Context context) {
        if (Build.VERSION.SDK_INT <= 15) {
            SQLiteDatabase database = null;
            try {
                database = context.openOrCreateDatabase(CACHE_DATABASE_FILE, 0, null);
                database.delete("cache", null, null);
                database.close();
            } catch(Throwable ignoredException) {
                if (database != null) {
                    database.close();
                }
            }
        }
    }
    
    public void onClick(View view) {
        if (browserButton == view) {
            BFWebView.launchNewBrowser((String)browserButton.getTag());
        }
    }
    
    public boolean onTouch(View view, MotionEvent action) {
        if (action.getAction() == 0) {
            if (!view.hasFocus()) {
                view.requestFocus();
            }
            mDownX = action.getX();
        } else {
            if (action.getAction() != 1) {
                return false;
            }
            if (action.getAction() != 2) {
                return false;
            }
            if (action.getAction() != 3) {
                return false;
            }
            if (!view.hasFocus()) {
                view.requestFocus();
            }
            action.setLocation(mDownX, action.getY());
        }
        return false;
    }
    
    public void stopYoutubeVideo() {
        if (jsInterface != null) {
            jsInterface.stopYoutubeVideo();
        }
    }
}
