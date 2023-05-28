package sg.gumi.bravefrontier.video;

public class VideoEngine implements android.media.MediaPlayer$OnCompletionListener, android.media.MediaPlayer$OnPreparedListener, android.view.View$OnTouchListener {
    final private static String TAG = "VideoEngine";
    org.cocos2dx.lib.Cocos2dxActivity mActivity;
    sg.gumi.bravefrontier.BraveFrontier mCallerClass;
    String mFileName;
    private int mForceHeight;
    private int mForceWidth;
    boolean mIsCloseOnTouch;
    boolean mIsPaused;
    boolean mIsPreActivityFinished;
    boolean mIsPrepared;
    boolean mIsVideoPlaying;
    int mLastPosition;
    Class mNextClass;
    sg.gumi.bravefrontier.video.BFVideoView mVideoView;
    
    public VideoEngine(org.cocos2dx.lib.Cocos2dxActivity a, Class a0, boolean b, sg.gumi.bravefrontier.video.BFVideoView a1, String s, int i, int i0) {
        this.mIsPaused = false;
        this.mNextClass = null;
        this.mCallerClass = null;
        this.mIsPreActivityFinished = false;
        this.mLastPosition = 0;
        this.mIsPrepared = false;
        this.mIsVideoPlaying = false;
        this.mIsCloseOnTouch = false;
        this.mForceHeight = 0;
        this.mForceWidth = 0;
        this.mActivity = a;
        this.mNextClass = a0;
        this.mIsPreActivityFinished = b;
        this.mVideoView = a1;
        ((android.widget.VideoView)a1).setOnCompletionListener((android.media.MediaPlayer$OnCompletionListener)(Object)this);
        ((android.widget.VideoView)this.mVideoView).setOnPreparedListener((android.media.MediaPlayer$OnPreparedListener)(Object)this);
        ((android.widget.VideoView)this.mVideoView).setOnTouchListener((android.view.View$OnTouchListener)(Object)this);
        this.mFileName = s;
        android.media.AudioManager a2 = (android.media.AudioManager)((android.app.Activity)a).getSystemService("audio");
        a2.setStreamVolume(3, a2.getStreamVolume(3), 0);
        this.mForceWidth = i;
        this.mForceHeight = i0;
        this.mIsPrepared = true;
    }
    
    public VideoEngine(org.cocos2dx.lib.Cocos2dxActivity a, sg.gumi.bravefrontier.video.BFVideoView a0, android.widget.LinearLayout a1, String s, int i, int i0) {
        this.mIsPaused = false;
        this.mNextClass = null;
        this.mCallerClass = null;
        this.mIsPreActivityFinished = false;
        this.mLastPosition = 0;
        this.mIsPrepared = false;
        this.mIsVideoPlaying = false;
        this.mIsCloseOnTouch = false;
        this.mForceHeight = 0;
        this.mForceWidth = 0;
        this.mActivity = a;
        this.mVideoView = a0;
        ((android.widget.VideoView)a0).setOnCompletionListener((android.media.MediaPlayer$OnCompletionListener)(Object)this);
        ((android.widget.VideoView)this.mVideoView).setOnPreparedListener((android.media.MediaPlayer$OnPreparedListener)(Object)this);
        ((android.widget.VideoView)this.mVideoView).setOnTouchListener((android.view.View$OnTouchListener)(Object)this);
        this.mFileName = s;
        android.media.AudioManager a2 = (android.media.AudioManager)((android.app.Activity)a).getSystemService("audio");
        a2.setStreamVolume(3, a2.getStreamVolume(3), 0);
        this.mForceWidth = i;
        this.mForceHeight = i0;
        this.mIsPrepared = true;
    }
    
    public VideoEngine(org.cocos2dx.lib.Cocos2dxActivity a, sg.gumi.bravefrontier.video.BFVideoView a0, String s, int i, int i0) {
        this.mIsPaused = false;
        this.mNextClass = null;
        this.mCallerClass = null;
        this.mIsPreActivityFinished = false;
        this.mLastPosition = 0;
        this.mIsPrepared = false;
        this.mIsVideoPlaying = false;
        this.mIsCloseOnTouch = false;
        this.mForceHeight = 0;
        this.mForceWidth = 0;
        this.mVideoView = a0;
        this.mActivity = a;
        ((android.widget.VideoView)a0).setOnCompletionListener((android.media.MediaPlayer$OnCompletionListener)(Object)this);
        ((android.widget.VideoView)this.mVideoView).setOnPreparedListener((android.media.MediaPlayer$OnPreparedListener)(Object)this);
        ((android.widget.VideoView)this.mVideoView).setOnTouchListener((android.view.View$OnTouchListener)(Object)this);
        this.mFileName = s;
        this.mForceWidth = i;
        this.mForceHeight = i0;
        this.mIsPrepared = true;
    }
    
    public boolean getPausedStatus() {
        return this.mIsPaused;
    }
    
    public void onCompletion(android.media.MediaPlayer a) {
        sg.gumi.bravefrontier.video.BFVideoView.getInstance(this.mActivity).finishVideo(false);
    }
    
    public void onDestroy() {
        android.util.Log.w("VideoEngine", "On Destroy called");
        sg.gumi.bravefrontier.video.BFVideoView a = this.mVideoView;
        if (a != null) {
            if (((android.widget.VideoView)a).isPlaying()) {
                ((android.widget.VideoView)this.mVideoView).stopPlayback();
            }
            this.mVideoView = null;
            this.mActivity = null;
            this.mNextClass = null;
        }
    }
    
    public void onLowMemory() {
        android.util.Log.e("onLowMemory", "lowMemory at VideoEngine");
    }
    
    public void onPause() {
        if (this.mIsPrepared && !this.mIsPaused) {
            ((android.widget.VideoView)this.mVideoView).pause();
            this.mIsPaused = true;
        }
    }
    
    public void onPrepared(android.media.MediaPlayer a) {
        android.util.Log.i("Video Engine", "PREPARED");
        sg.gumi.bravefrontier.video.BFVideoView.getInstance(this.mActivity).onPrepared();
        a.start();
        this.mIsPaused = false;
        this.mIsVideoPlaying = true;
    }
    
    public void onResume() {
        if (this.mIsPrepared && this.mIsPaused) {
            ((android.widget.VideoView)this.mVideoView).start();
            this.mIsPaused = false;
        }
    }
    
    public boolean onTouch(android.view.View a, android.view.MotionEvent a0) {
        if (a0.getAction() != 1) {
            return true;
        }
        if (this.mIsCloseOnTouch && this.mIsVideoPlaying) {
            android.util.Log.i("Video Engine", "TOUCHED");
            if (((android.widget.VideoView)this.mVideoView).isPlaying()) {
                ((android.widget.VideoView)this.mVideoView).stopPlayback();
            }
            sg.gumi.bravefrontier.video.BFVideoView.getInstance(this.mActivity).finishVideo(true);
        }
        return false;
    }
    
    public void setPausedStatus(boolean b) {
        this.mIsPaused = b;
    }
    
    public void setVideoCloseOnTouch(boolean b) {
        this.mIsCloseOnTouch = b;
    }
    
    public void start() {
        android.net.Uri a = android.net.Uri.parse(this.mFileName);
        ((android.widget.VideoView)this.mVideoView).setVideoURI(a);
        this.mVideoView.setDimensions(this.mForceWidth, this.mForceHeight);
    }
}
