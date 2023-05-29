package sg.gumi.bravefrontier.video;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.VideoView;
import org.cocos2dx.lib.Cocos2dxActivity;
import sg.gumi.bravefrontier.BraveFrontierJNI;
import sg.gumi.bravefrontier.R;

public class BFVideoView extends VideoView {
    private static BFVideoView instance;
    private Cocos2dxActivity mActivity;
    private int mForceHeight;
    private int mForceWidth;
    private VideoEngine mVideoEngine;

    public BFVideoView(Context context, AttributeSet set) {
        super(context, set);
        mActivity = null;
        mForceHeight = 0;
        mForceWidth = 0;
        mVideoEngine = null;
    }
    
    public static void clearInstance() {
        if (instance != null) {
            FrameLayout frameLayout = instance.mActivity.findViewById(R.id.content);
            View view = instance.mActivity.findViewById(R.id.layout_videoscreen);
            frameLayout.requestFocus();
            frameLayout.setFocusableInTouchMode(true);
            frameLayout.removeView(view);
            frameLayout.requestFocus();
            frameLayout.setFocusableInTouchMode(true);
            instance = null;
        }
    }
    
    public static BFVideoView getInstance(Cocos2dxActivity activity) {
        if (instance == null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.video_activity, null);
            ((FrameLayout)activity.findViewById(R.id.content)).addView(view);
            BFVideoView videoView = view.findViewById(R.id.video_view);
            instance = videoView;
            videoView.Initialize(activity);
        }
        return instance;
    }
    
    public static boolean isInstance() {
        return instance != null;
    }
    
    public void Initialize(Cocos2dxActivity activity) {
        mActivity = activity;
        setZOrderOnTop(true);
    }
    
    public void finishVideo(boolean skipped) {
        if (skipped) {
            BraveFrontierJNI.videoSkippedCallback();
        } else {
            BraveFrontierJNI.videoFinishedCallback();
        }
        mVideoEngine = null;
        BFVideoView.clearInstance();
    }
    
    protected void onMeasure(int width, int height) {
        this.setMeasuredDimension(mForceWidth, mForceHeight);
    }
    
    public void onPause() {
        if (mVideoEngine != null) {
            mVideoEngine.onPause();
        }
    }
    
    public void onPrepared() {
        BraveFrontierJNI.videoPreparedCallback();
    }
    
    public void onResume() {
        if (mVideoEngine != null) {
            mVideoEngine.onResume();
        }
    }
    
    public void playVideo(String fileName, boolean closeOnTouch) {
        Log.d("Video Activity File Name", fileName);
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mVideoEngine = new VideoEngine(mActivity, this, fileName, size.x, size.y);
        mVideoEngine.setVideoCloseOnTouch(closeOnTouch);
        mVideoEngine.start();
    }
    
    public void setDimensions(int width, int height) {
        mForceHeight = height;
        mForceWidth = width;
    }
}
