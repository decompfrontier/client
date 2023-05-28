package sg.gumi.bravefrontier;

class GCMIntentService$1 implements Runnable {
    final sg.gumi.bravefrontier.GCMIntentService this$0;
    final String val$deviceId;
    
    GCMIntentService$1(sg.gumi.bravefrontier.GCMIntentService a, String s) {
        this.this$0 = a;
        this.val$deviceId = s;
        super();
    }
    
    public void run() {
        try {
            sg.gumi.bravefrontier.BraveFrontierJNI.nativeSetDeviceRegistrationId(this.val$deviceId);
        } catch(Throwable ignoredException) {
        }
    }
}
