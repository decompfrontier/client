package sg.gumi.bravefrontier;

class GameHelper$1 implements com.google.android.gms.tasks.OnSuccessListener {
    final sg.gumi.bravefrontier.GameHelper this$0;
    
    GameHelper$1(sg.gumi.bravefrontier.GameHelper a) {
        this.this$0 = a;
        super();
    }
    
    public void onSuccess(android.content.Intent a) {
        this.this$0.mActivity.startActivityForResult(a, sg.gumi.bravefrontier.GameHelper.access$000());
    }
    
    public void onSuccess(Object a) {
        this.onSuccess((android.content.Intent)a);
    }
}
