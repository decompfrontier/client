package sg.gumi.bravefrontier;

final public class GameHelper$GooglePlaySignInFailureReason extends sg.gumi.bravefrontier.GameService$SignInFailureReason {
    public GameHelper$GooglePlaySignInFailureReason(int i) {
        super(i, -100);
    }
    
    public GameHelper$GooglePlaySignInFailureReason(int i, int i0) {
        super(i, i0);
    }
    
    public String toString() {
        String s = null;
        StringBuilder a = new StringBuilder();
        a.append("SignInFailureReason(serviceErrorCode:");
        a.append(sg.gumi.bravefrontier.GameHelper.errorCodeToString(((sg.gumi.bravefrontier.GameService$SignInFailureReason)this).getServiceErrorCode()));
        if (((sg.gumi.bravefrontier.GameService$SignInFailureReason)this).getActivityResultCode() != -100) {
            StringBuilder a0 = new StringBuilder();
            a0.append(",activityResultCode:");
            a0.append(sg.gumi.bravefrontier.GameHelper.activityResponseCodeToString(((sg.gumi.bravefrontier.GameService$SignInFailureReason)this).getActivityResultCode()));
            a0.append(")");
            s = a0.toString();
        } else {
            s = ")";
        }
        a.append(s);
        return a.toString();
    }
}
