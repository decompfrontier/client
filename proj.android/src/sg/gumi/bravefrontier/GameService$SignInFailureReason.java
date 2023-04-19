package sg.gumi.bravefrontier;

public class GameService$SignInFailureReason {
    final public static int NO_ACTIVITY_RESULT_CODE = -100;
    private int mActivityResultCode;
    private int mServiceErrorCode;
    
    public GameService$SignInFailureReason(int i) {
        this(i, -100);
    }
    
    public GameService$SignInFailureReason(int i, int i0) {
        this.mServiceErrorCode = 0;
        this.mActivityResultCode = -100;
        this.mServiceErrorCode = i;
        this.mActivityResultCode = i0;
    }
    
    final public int getActivityResultCode() {
        return this.mActivityResultCode;
    }
    
    final public int getServiceErrorCode() {
        return this.mServiceErrorCode;
    }
}
