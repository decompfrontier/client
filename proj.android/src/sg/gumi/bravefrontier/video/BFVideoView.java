package sg.gumi.bravefrontier.video;

public class BFVideoView extends android.widget.VideoView {
    private static sg.gumi.bravefrontier.video.BFVideoView instance;
    private org.cocos2dx.lib.Cocos2dxActivity mActivity;
    private int mForceHeight;
    private int mForceWidth;
    private sg.gumi.bravefrontier.video.VideoEngine mVideoEngine;
    
    static {
    }
    
    public BFVideoView(android.content.Context a, android.util.AttributeSet a0) {
        super(a, a0);
        this.mActivity = null;
        this.mForceHeight = 0;
        this.mForceWidth = 0;
        this.mVideoEngine = null;
    }
    
    public static void clearInstance() {
        sg.gumi.bravefrontier.video.BFVideoView a = instance;
        if (a != null) {
            android.widget.FrameLayout a0 = (android.widget.FrameLayout)((android.app.Activity)a.mActivity).findViewById(16908290);
            android.view.View a1 = ((android.app.Activity)instance.mActivity).findViewById(2131165318);
            a0.requestFocus();
            a0.setFocusableInTouchMode(true);
            a0.removeView(a1);
            a0.requestFocus();
            a0.setFocusableInTouchMode(true);
            instance = null;
        }
    }
    
    public static sg.gumi.bravefrontier.video.BFVideoView getInstance(org.cocos2dx.lib.Cocos2dxActivity a) {
        if (instance == null) {
            android.view.View a0 = android.view.LayoutInflater.from((android.content.Context)a).inflate(2131361847, (android.view.ViewGroup)null);
            ((android.widget.FrameLayout)((android.app.Activity)a).findViewById(16908290)).addView(a0);
            sg.gumi.bravefrontier.video.BFVideoView a1 = (sg.gumi.bravefrontier.video.BFVideoView)a0.findViewById(2131165402);
            instance = a1;
            a1.Initialize(a);
        }
        return instance;
    }
    
    public static boolean isInstance() {
        return instance != null;
    }
    
    public void Initialize(org.cocos2dx.lib.Cocos2dxActivity a) {
        this.mActivity = a;
        ((android.widget.VideoView)this).setZOrderOnTop(true);
    }
    
    public void finishVideo(boolean b) {
        if (b) {
            sg.gumi.bravefrontier.BraveFrontierJNI.videoSkippedCallback();
        } else {
            sg.gumi.bravefrontier.BraveFrontierJNI.videoFinishedCallback();
        }
        this.mVideoEngine = null;
        sg.gumi.bravefrontier.video.BFVideoView.clearInstance();
    }
    
    protected void onMeasure(int i, int i0) {
        ((android.widget.VideoView)this).setMeasuredDimension(this.mForceWidth, this.mForceHeight);
    }
    
    public void onPause() {
        sg.gumi.bravefrontier.video.VideoEngine a = this.mVideoEngine;
        if (a != null) {
            a.onPause();
        }
    }
    
    public void onPrepared() {
        sg.gumi.bravefrontier.BraveFrontierJNI.videoPreparedCallback();
    }
    
    public void onResume() {
        sg.gumi.bravefrontier.video.VideoEngine a = this.mVideoEngine;
        if (a != null) {
            a.onResume();
        }
    }
    
    public void playVideo(String s, boolean b) {
        android.util.Log.d("Video Activity File Name", s);
        android.view.Display a = ((android.app.Activity)this.mActivity).getWindowManager().getDefaultDisplay();
        android.graphics.Point a0 = new android.graphics.Point();
        a.getSize(a0);
        int i = a0.x;
        int i0 = a0.y;
        sg.gumi.bravefrontier.video.VideoEngine a1 = new sg.gumi.bravefrontier.video.VideoEngine(this.mActivity, this, s, i, i0);
        this.mVideoEngine = a1;
        a1.setVideoCloseOnTouch(b);
        this.mVideoEngine.start();
    }
    
    public void setDimensions(int i, int i0) {
        this.mForceHeight = i0;
        this.mForceWidth = i;
    }
}
