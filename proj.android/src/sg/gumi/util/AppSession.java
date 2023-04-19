package sg.gumi.util;

final public class AppSession {
    final private static String APP_SESSION_PREF = "APP_SESSION";
    final private static String BF_SESSION_END_TIME_KEY = "bfendtime";
    final private static String BF_SESSION_START_TIME_KEY = "bfstarttime";
    final private static String FR_END_TIME = "END_SESSION";
    final private static String FR_SESSION_LENGTH = "SESSION_LENGTH";
    final private static long INVALID_TIME = -9223372036854775808L;
    final private static long SECONDS = 1000L;
    
    public AppSession() {
    }
    
    public static void onPause(sg.gumi.bravefrontier.BraveFrontier a) {
        android.content.SharedPreferences$Editor a0 = ((android.app.Activity)a).getSharedPreferences("APP_SESSION", 0).edit();
        a0.putLong("bfendtime", System.currentTimeMillis());
        a0.commit();
    }
    
    public static void onResume(sg.gumi.bravefrontier.BraveFrontier a, Object a0) {
        android.content.SharedPreferences a1 = ((android.app.Activity)a).getSharedPreferences("APP_SESSION", 0);
        long j = a1.getLong("bfstarttime", -9223372036854775808L);
        long j0 = a1.getLong("bfendtime", -9223372036854775808L);
        if (sg.gumi.bravefrontier.BraveFrontier.isFiverocksInitialized()) {
            if (j > -9223372036854775808L && j0 > -9223372036854775808L) {
                com.tapjoy.Tapjoy.trackEvent((String)null, "SESSION_LENGTH", (j0 - j) / 1000L);
            }
            if (j0 > -9223372036854775808L) {
                java.text.SimpleDateFormat a2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                a2.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
                com.tapjoy.Tapjoy.trackEvent((String)null, "END_SESSION", a2.format(new java.util.Date(j0)), (String)null, j0 / 1000L);
            }
        }
        android.content.SharedPreferences$Editor a3 = a1.edit();
        a3.putLong("bfstarttime", System.currentTimeMillis());
        a3.putLong("bfendtime", -9223372036854775808L);
        a3.commit();
    }
}
