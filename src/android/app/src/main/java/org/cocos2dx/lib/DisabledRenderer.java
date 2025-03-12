package org.cocos2dx.lib;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
    Null render for cocos
 @note __DECOMP__, IT DOES NOT EXIST IN THE NORMAL COCOS2DX!!!

 */
final public class DisabledRenderer extends Cocos2dxRenderer {

    public String getContentText() {
        return "";
    }
    
    public void handleActionCancel(int[] pIDs, float[] pXs, float[] pYs) {
    }
    
    public void handleActionDown(int pID, float pX, float pY) {
    }
    
    public void handleActionMove(int[] pIDs, float[] a0, float[] pYs) {
    }
    
    public void handleActionUp(int pID, float pX, float pY) {
    }
    
    public void handleDeleteBackward() {
    }
    
    public void handleInsertText(String pText) {
    }
    
    public void handleKeyDown(int pKeyCode) {
    }
    
    public void handleOnPause() {
    }
    
    public void handleOnResume() {
    }
    
    public void onDrawFrame(GL10 gl) {
    }
    
    public void onSurfaceChanged(GL10 pGL10, int pWidth, int pHeight) {
    }
    
    public void onSurfaceCreated(GL10 pGL10, EGLConfig pEGLConfig) {
    }
}
