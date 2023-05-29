package sg.gumi.bravefrontier.video;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import org.cocos2dx.lib.Cocos2dxActivity;
import sg.gumi.bravefrontier.BraveFrontier;

public class VideoEngine implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, View.OnTouchListener {
    final private static String TAG = "VideoEngine";
    Cocos2dxActivity mActivity;
    BraveFrontier mCallerClass;
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
    BFVideoView mVideoView;
    
    public VideoEngine(Cocos2dxActivity activity, Class nextClass, boolean preActivityFinished, BFVideoView videoView, String fileName, int width, int height) {
        mIsPaused = false;
        mNextClass = null;
        mCallerClass = null;
        mIsPreActivityFinished = false;
        mLastPosition = 0;
        mIsPrepared = false;
        mIsVideoPlaying = false;
        mIsCloseOnTouch = false;
        mForceHeight = 0;
        mForceWidth = 0;
        mActivity = activity;
        mNextClass = nextClass;
        mIsPreActivityFinished = preActivityFinished;
        mVideoView = videoView;
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnTouchListener(this);
        mFileName = fileName;
        AudioManager audioManager = (AudioManager)activity.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(3, audioManager.getStreamVolume(3), 0);
        mForceWidth = width;
        mForceHeight = height;
        mIsPrepared = true;
    }
    
    public VideoEngine(Cocos2dxActivity activity, BFVideoView videoView, LinearLayout layout, String fileName, int width, int height) {
        mIsPaused = false;
        mNextClass = null;
        mCallerClass = null;
        mIsPreActivityFinished = false;
        mLastPosition = 0;
        mIsPrepared = false;
        mIsVideoPlaying = false;
        mIsCloseOnTouch = false;
        mForceHeight = 0;
        mForceWidth = 0;
        mActivity = activity;
        mVideoView = videoView;
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnTouchListener(this);
        mFileName = fileName;
        android.media.AudioManager audioManager = ((AudioManager)activity.getSystemService(Context.AUDIO_SERVICE));
        audioManager.setStreamVolume(3, audioManager.getStreamVolume(3), 0);
        mForceWidth = width;
        mForceHeight = height;
        mIsPrepared = true;
    }
    
    public VideoEngine(Cocos2dxActivity activity, BFVideoView videoView, String fileName, int width, int height) {
        mIsPaused = false;
        mNextClass = null;
        mCallerClass = null;
        mIsPreActivityFinished = false;
        mLastPosition = 0;
        mIsPrepared = false;
        mIsVideoPlaying = false;
        mIsCloseOnTouch = false;
        mForceHeight = 0;
        mForceWidth = 0;
        mVideoView = videoView;
        mActivity = activity;
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnTouchListener(this);
        mFileName = fileName;
        mForceWidth = width;
        mForceHeight = height;
        mIsPrepared = true;
    }
    
    public boolean getPausedStatus() {
        return mIsPaused;
    }
    
    public void onCompletion(MediaPlayer player) {
       BFVideoView.getInstance(mActivity).finishVideo(false);
    }
    
    public void onDestroy() {
        Log.w(TAG, "On Destroy called");
        if (mVideoView != null) {
            if (mVideoView.isPlaying()) {
                mVideoView.stopPlayback();
            }
            mVideoView = null;
            mActivity = null;
            mNextClass = null;
        }
    }
    
    public void onLowMemory() {
        Log.e("onLowMemory", "lowMemory at VideoEngine");
    }
    
    public void onPause() {
        if (mIsPrepared && !mIsPaused) {
            mVideoView.pause();
            mIsPaused = true;
        }
    }
    
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.i("Video Engine", "PREPARED");
        BFVideoView.getInstance(mActivity).onPrepared();
        mediaPlayer.start();
        mIsPaused = false;
        mIsVideoPlaying = true;
    }
    
    public void onResume() {
        if (mIsPrepared && mIsPaused) {
            mVideoView.start();
            mIsPaused = false;
        }
    }
    
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() != 1) {
            return true;
        }
        if (mIsCloseOnTouch && mIsVideoPlaying) {
            Log.i("Video Engine", "TOUCHED");
            if (mVideoView.isPlaying()) {
                mVideoView.stopPlayback();
            }
            BFVideoView.getInstance(mActivity).finishVideo(true);
        }
        return false;
    }
    
    public void setPausedStatus(boolean paused) {
        mIsPaused = paused;
    }
    
    public void setVideoCloseOnTouch(boolean set) {
        mIsCloseOnTouch = set;
    }
    
    public void start() {
        mVideoView.setVideoURI(Uri.parse(mFileName));
        mVideoView.setDimensions(mForceWidth, mForceHeight);
    }
}
