package sg.gumi.util;

import android.content.Context;
import android.content.Intent;

public class BFInstallReferrer extends android.content.BroadcastReceiver {
    private static long elapsedTime = -9223372036854775808L;
    
    static {
    }
    
    public BFInstallReferrer() {
    }
    
    public void onReceive(Context context, Intent intent) {
        if (System.currentTimeMillis() > elapsedTime) {
            elapsedTime = System.currentTimeMillis() + 5000L;
            if (BFConfig.PLATFORM == BFConfig.PLATFORM_GOOGLE) {
                try {
                    //new com.google.ads.conversiontracking.InstallReceiver().onReceive(context, intent);
                } catch(Throwable ignoredException) {
                }
            }
        }
    }
}
