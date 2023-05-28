package sg.gumi.util;

public class BFInstallReferrer extends android.content.BroadcastReceiver {
    private static long elapsedTime = -9223372036854775808L;
    
    static {
    }
    
    public BFInstallReferrer() {
    }
    
    public void onReceive(android.content.Context a, android.content.Intent a0) {
        if (System.currentTimeMillis() > elapsedTime) {
            elapsedTime = System.currentTimeMillis() + 5000L;
            if (sg.gumi.util.BFConfig.PLATFORM == sg.gumi.util.BFConfig.PLATFORM_GOOGLE) {
                try {
                    new com.google.ads.conversiontracking.InstallReceiver().onReceive(a, a0);
                } catch(Throwable ignoredException) {
                }
            }
        }
    }
}
