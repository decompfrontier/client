package sg.gumi.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import sg.gumi.bravefrontier.BraveFrontier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.tapjoy.Tapjoy;

final public class AppSession {
    final private static String APP_SESSION_PREF = "APP_SESSION";
    final private static String BF_SESSION_END_TIME_KEY = "bfendtime";
    final private static String BF_SESSION_START_TIME_KEY = "bfstarttime";
    final private static String FR_END_TIME = "END_SESSION";
    final private static String FR_SESSION_LENGTH = "SESSION_LENGTH";
    final private static long INVALID_TIME = Long.MIN_VALUE;
    final private static long SECONDS = 1000;
    
    public AppSession() {
    }
    
    public static void onPause(BraveFrontier app) {
        Editor edit = app.getSharedPreferences(APP_SESSION_PREF, 0).edit();
        edit.putLong(BF_SESSION_END_TIME_KEY, System.currentTimeMillis());
        edit.commit();
    }
    
    public static void onResume(BraveFrontier app, Object a0) {
        android.content.SharedPreferences appSession = app.getSharedPreferences(APP_SESSION_PREF, 0);
        long startTime = appSession.getLong(BF_SESSION_START_TIME_KEY, INVALID_TIME);
        long endTime = appSession.getLong(BF_SESSION_END_TIME_KEY, INVALID_TIME);
        if (sg.gumi.bravefrontier.BraveFrontier.isFiverocksInitialized()) {
            if (startTime > INVALID_TIME && endTime > INVALID_TIME) {
               Tapjoy.trackEvent(null, FR_SESSION_LENGTH, (endTime - startTime) / SECONDS);
            }
            if (endTime > INVALID_TIME) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                Tapjoy.trackEvent(null, FR_END_TIME, format.format(new Date(endTime)), null, endTime / SECONDS);
            }
        }
        SharedPreferences.Editor editor = appSession.edit();
        editor.putLong(BF_SESSION_START_TIME_KEY, System.currentTimeMillis());
        editor.putLong(BF_SESSION_END_TIME_KEY, INVALID_TIME);
        editor.commit();
    }
}
