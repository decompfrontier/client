package sg.gumi.bravefrontier;

class BraveFrontier$2 implements com.tapjoy.TJConnectListener {
    final sg.gumi.bravefrontier.BraveFrontier this$0;
    
    BraveFrontier$2(sg.gumi.bravefrontier.BraveFrontier a) {
        this.this$0 = a;
        super();
    }
    
    public void onConnectFailure() {
        sg.gumi.bravefrontier.BraveFrontier.fiverocksInitialized = false;
        android.util.Log.d("TAPJOY_DEBUG", "onConnectFailure");
    }
    
    public void onConnectSuccess() {
        android.util.Log.d("TAPJOY_DEBUG", "onConnectSuccess");
        sg.gumi.bravefrontier.BraveFrontier.fiverocksInitialized = true;
    }
}
