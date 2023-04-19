package sg.gumi.bravefrontier;

final public class Facebook$FacebookNativeCallback implements Runnable {
    final public static int GET_FRIEND_LIST_FAIL = 4;
    final public static int GET_FRIEND_LIST_PERMISSION = 7;
    final public static int GET_FRIEND_LIST_RETRY = 8;
    final public static int GET_FRIEND_LIST_SUCCESS = 3;
    final public static int INVITE_FRIEND_FAIL = 6;
    final public static int INVITE_FRIEND_SUCCESS = 5;
    final public static int LOGIN_FAIL = 2;
    final public static int LOGIN_SUCCESS = 1;
    final int callback;
    final Object param;
    
    public Facebook$FacebookNativeCallback(int i) {
        this.callback = i;
        this.param = null;
    }
    
    public Facebook$FacebookNativeCallback(int i, Object a) {
        this.callback = i;
        this.param = a;
    }
    
    public void run() {
        if (!org.cocos2dx.lib.Cocos2dxHelper.isNativeLibraryLoaded()) {
            return;
        }
        int i = this.callback;
        if (i == 1) {
            sg.gumi.bravefrontier.Facebook.onLoginSuccess();
        } else if (i == 2) {
            Object a = this.param;
            sg.gumi.bravefrontier.Facebook.onLoginFail((a == null) ? "" : a.toString());
        }
    }
    
    public void startCall() {
        ((android.opengl.GLSurfaceView)((org.cocos2dx.lib.Cocos2dxActivity)sg.gumi.bravefrontier.BraveFrontier.getActivity()).getGLView()).queueEvent((Runnable)(Object)this);
    }
}
