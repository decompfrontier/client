package sg.gumi.bravefrontier;

class Facebook$1 implements com.facebook.FacebookCallback {
    final sg.gumi.bravefrontier.Facebook this$0;
    
    Facebook$1(sg.gumi.bravefrontier.Facebook a) {
        this.this$0 = a;
        super();
    }
    
    public void onCancel() {
        new sg.gumi.bravefrontier.Facebook$FacebookNativeCallback(2, (Object)"Canceled").startCall();
    }
    
    public void onError(com.facebook.FacebookException a) {
        new sg.gumi.bravefrontier.Facebook$FacebookNativeCallback(2, (Object)a.toString()).startCall();
    }
    
    public void onSuccess(com.facebook.login.LoginResult a) {
        new sg.gumi.bravefrontier.Facebook$FacebookNativeCallback(1).startCall();
    }
    
    public void onSuccess(Object a) {
        this.onSuccess((com.facebook.login.LoginResult)a);
    }
}
