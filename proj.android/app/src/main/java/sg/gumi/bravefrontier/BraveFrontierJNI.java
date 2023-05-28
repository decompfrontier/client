package sg.gumi.bravefrontier;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.*;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import org.cocos2dx.lib.Cocos2dxActivity;
import sg.gumi.bravefrontier.webview.BFWebView;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;
import static sg.gumi.util.BFConfig.*;
import static android.os.Build.*;

public class BraveFrontierJNI {
    static class disableDimTask implements Runnable {

        public void run() {
            Log.d("NONSLEEP_ANDROID BraveFrontierJNI", "disableDim");
        }
    }

    static class enableDimTask implements Runnable {

        public void run() {
            Log.d("NONSLEEP_ANDROID BraveFrontierJNI", "enableDim");
        }
    }


    static class unpackNoDlcTask implements Runnable {

        public void run() {
            String s = sg.gumi.bravefrontier.BraveFrontierJNI.getWritablePath();
            label0: {
                java.io.InputStream a = null;
                label2: {
                    label1: {
                        try {
                            a = null;
                            a = ((android.app.Activity)sg.gumi.bravefrontier.BraveFrontierJNI.getActivity()).getResources().getAssets().open("NoDLCRes.zip");
                            java.util.zip.ZipInputStream a0 = new java.util.zip.ZipInputStream(a);
                            while(true) {
                                java.util.zip.ZipEntry a1 = a0.getNextEntry();
                                if (a1 == null) {
                                    break;
                                }
                                if (!a1.isDirectory()) {
                                    StringBuilder a2 = new StringBuilder();
                                    a2.append(s);
                                    a2.append("/");
                                    a2.append(a1.getName().replace((CharSequence)(Object)"NoDLCRes", (CharSequence)(Object)""));
                                    java.io.FileOutputStream a3 = new java.io.FileOutputStream(a2.toString());
                                    byte[] a4 = new byte[4096];
                                    while(true) {
                                        int i = a0.read(a4);
                                        if (i <= 0) {
                                            a0.closeEntry();
                                            a3.close();
                                            break;
                                        } else {
                                            a3.write(a4, 0, i);
                                        }
                                    }
                                }
                            }
                            a0.close();
                            sg.gumi.bravefrontier.BraveFrontierJNI.s_UnzipStatus = 0;
                        } catch(Throwable ignoredException) {
                            break label1;
                        }
                        if (a == null) {
                            break label0;
                        }
                        break label2;
                    }
                    if (sg.gumi.bravefrontier.BraveFrontierJNI.s_UnzipStatus != 0) {
                        sg.gumi.bravefrontier.BraveFrontierJNI.s_UnzipStatus = 2;
                    }
                    if (a == null) {
                        break label0;
                    }
                }
                try {
                    a.close();
                } catch(Throwable ignoredException0) {
                }
            }
        }
    }

    static class clipboardTask implements Runnable {
        final String str;

        clipboardTask(String stringToCopy) {
            super();
            str = stringToCopy;

        }

        public void run() {
            try {
                ((ClipboardManager) (BraveFrontierJNI.getActivity()).getBaseContext().getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(str, str));
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }

    final public static String PREFS_NAME_AUTH = "auth";
    final public static String PREFS_NAME_GAMEDATA = "gamedata";
    final private static String THAI_COMMON_WORDS = "(\u0e40\u0e1b\u0e47\u0e19|\u0e2d\u0e22\u0e39\u0e48|\u0e08\u0e30|\u0e43\u0e0a\u0e49|\u0e44\u0e14\u0e49|\u0e43\u0e2b\u0e49|\u0e43\u0e19|\u0e08\u0e36\u0e07|\u0e2b\u0e23\u0e37\u0e2d|\u0e41\u0e25\u0e30|\u0e01\u0e31\u0e1a|\u0e40\u0e19\u0e37\u0e48\u0e2d\u0e07|\u0e14\u0e49\u0e27\u0e22|\u0e16\u0e49\u0e32|\u0e41\u0e25\u0e49\u0e27|\u0e17\u0e31\u0e49\u0e07|\u0e40\u0e1e\u0e23\u0e32\u0e30|\u0e0b\u0e36\u0e48\u0e07|\u0e0b\u0e49\u0e33|\u0e44\u0e21\u0e48|\u0e43\u0e0a\u0e48|\u0e15\u0e49\u0e2d\u0e07|\u0e01\u0e31\u0e19|\u0e08\u0e32\u0e01|\u0e16\u0e36\u0e07|\u0e19\u0e31\u0e49\u0e19|\u0e1c\u0e39\u0e49|\u0e04\u0e27\u0e32\u0e21|\u0e2a\u0e48\u0e27\u0e19|\u0e22\u0e31\u0e07|\u0e17\u0e31\u0e48\u0e27|\u0e2d\u0e37\u0e48\u0e19|\u0e42\u0e14\u0e22|\u0e2a\u0e32\u0e21\u0e32\u0e23\u0e16|\u0e40\u0e17\u0e48\u0e32|\u0e43\u0e15\u0e49|\u0e43\u0e2a\u0e48|\u0e43\u0e14|\u0e44\u0e27\u0e49|\u0e43\u0e2b\u0e21\u0e48|\u0e43\u0e2b\u0e0d\u0e48|\u0e40\u0e25\u0e47\u0e01|\u0e43\u0e01\u0e25\u0e49|\u0e44\u0e01\u0e25|\u0e40\u0e02\u0e32|\u0e0a\u0e48\u0e27\u0e22|\u0e09\u0e1a\u0e31\u0e1a|\u0e04\u0e49\u0e19|\u0e40\u0e23\u0e47\u0e27|\u0e40\u0e02\u0e49\u0e32|\u0e40\u0e0a\u0e49\u0e32)";
    final private static String THAI_FOLLOWING_CHARS = "\u0e2f|[\u0e30-\u0e3a]|[\u0e45-\u0e4e]|\\)|\\]|\\}|\"";
    final private static String THAI_LEADING_CHARS = "[\u0e40-\u0e44]|\\(|\\[|\\{|\"";
    final private static String THAI_REGEX_0 = "(\u0e2f|[\u0e30-\u0e3a]|[\u0e45-\u0e4e]|\\)|\\]|\\}|\")(?=([\u0e40-\u0e44]|\\(|\\[|\\{|\"))";
    final private static String THAI_REGEX_1 = "([\u0e01-\u0e3a\u0e40-\u0e4f\u0e5a\u0e5b])(?![\\)\\]\\}\"]|[\u0e01-\u0e3a\u0e40-\u0e4f\u0e5a\u0e5b])";
    final private static String THAI_REGEX_2 = "([^\u0e01-\u0e3a\u0e40-\u0e4f\u0e5a\u0e5b\\(\\(\\[\\{\"])(?=[\u0e01-\u0e3a\u0e40-\u0e4f\u0e5a\u0e5b])";
    final private static String THAI_REGEX_3 = "([^[\u0e40-\u0e44]|\\(|\\[|\\{|\"])(?=([\u0e40-\u0e44]|\\(|\\[|\\{|\")*(\u0e40\u0e1b\u0e47\u0e19|\u0e2d\u0e22\u0e39\u0e48|\u0e08\u0e30|\u0e43\u0e0a\u0e49|\u0e44\u0e14\u0e49|\u0e43\u0e2b\u0e49|\u0e43\u0e19|\u0e08\u0e36\u0e07|\u0e2b\u0e23\u0e37\u0e2d|\u0e41\u0e25\u0e30|\u0e01\u0e31\u0e1a|\u0e40\u0e19\u0e37\u0e48\u0e2d\u0e07|\u0e14\u0e49\u0e27\u0e22|\u0e16\u0e49\u0e32|\u0e41\u0e25\u0e49\u0e27|\u0e17\u0e31\u0e49\u0e07|\u0e40\u0e1e\u0e23\u0e32\u0e30|\u0e0b\u0e36\u0e48\u0e07|\u0e0b\u0e49\u0e33|\u0e44\u0e21\u0e48|\u0e43\u0e0a\u0e48|\u0e15\u0e49\u0e2d\u0e07|\u0e01\u0e31\u0e19|\u0e08\u0e32\u0e01|\u0e16\u0e36\u0e07|\u0e19\u0e31\u0e49\u0e19|\u0e1c\u0e39\u0e49|\u0e04\u0e27\u0e32\u0e21|\u0e2a\u0e48\u0e27\u0e19|\u0e22\u0e31\u0e07|\u0e17\u0e31\u0e48\u0e27|\u0e2d\u0e37\u0e48\u0e19|\u0e42\u0e14\u0e22|\u0e2a\u0e32\u0e21\u0e32\u0e23\u0e16|\u0e40\u0e17\u0e48\u0e32|\u0e43\u0e15\u0e49|\u0e43\u0e2a\u0e48|\u0e43\u0e14|\u0e44\u0e27\u0e49|\u0e43\u0e2b\u0e21\u0e48|\u0e43\u0e2b\u0e0d\u0e48|\u0e40\u0e25\u0e47\u0e01|\u0e43\u0e01\u0e25\u0e49|\u0e44\u0e01\u0e25|\u0e40\u0e02\u0e32|\u0e0a\u0e48\u0e27\u0e22|\u0e09\u0e1a\u0e31\u0e1a|\u0e04\u0e49\u0e19|\u0e40\u0e23\u0e47\u0e27|\u0e40\u0e02\u0e49\u0e32|\u0e40\u0e0a\u0e49\u0e32)(\u0e2f|[\u0e30-\u0e3a]|[\u0e45-\u0e4e]|\\)|\\]|\\}|\")?)";
    final private static String THAI_REGEX_4 = "([\u0e45\u0e46\u0e33])";
    final private static String THAI_SINGLE_CHARS = "\u0e01-\u0e3a\u0e40-\u0e4f\u0e5a\u0e5b";
    private static String deepLinkParam;
    private static Cocos2dxActivity mActivity;
    private static Pattern[] regexArray;
    static int s_UnzipStatus;

    static Cocos2dxActivity getActivity() {
        return mActivity;
    }

    public static void appExit() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    
    public static void appsflyerStartTracking() {
        BraveFrontier.appsflyerStartTracking();
    }
    
    native public static void backButtonCallback();
    
    
    public static boolean canLaunchUrl(String url) {
        return BFWebView.canLaunchUrl(url);
    }
    
    public static void cancelLocalNotifications() {
        Context context = mActivity.getApplicationContext();
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("EnergyNotif");
        alarm.cancel(PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT));
        intent.setAction("ArenaNotif");
        alarm.cancel(PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT));
        intent.setAction("NoLoginNotif");
        alarm.cancel(PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT));
    }
    
    public static void clearApplicationData() {
        BraveFrontierJNI.deleteDir(mActivity.getBaseContext().getFilesDir());
    }
    
    public static void clearDeepLinkParam() {
        deepLinkParam = null;
    }
    
    public static void copyToClipboard(String stringToCopy) {
       mActivity.runOnUiThread(new clipboardTask(stringToCopy));
    }
    
    public static void createLocalNotification(int seconds, String text, String action) {
        Context context = ((android.opengl.GLSurfaceView)mActivity.getGLView()).getContext();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, seconds);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("notification", text);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        ((android.app.AlarmManager)context.getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    public static String decodeCStringForBase64(String data, String key) {
        try {
            byte[] aesKey = new byte[16];
            System.arraycopy(key.getBytes(StandardCharsets.UTF_8), 0, aesKey, 0, Math.min(16, key.getBytes(StandardCharsets.UTF_8).length));
            SecretKeySpec secKey = new SecretKeySpec(aesKey, "AES");
            Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
            aes.init(2, secKey);
            return new String(aes.doFinal(sg.gumi.bravefrontier.Base64.decode(data)));
        } catch(Throwable ignoredException) {
            return "";
        }

    }
    
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            for (String dd: dir.list()) {
                if (!BraveFrontierJNI.deleteDir(new File(dir, dd))) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    
    public static boolean deleteLocalFiles() {
        for (String file: mActivity.fileList()) {
            mActivity.deleteFile(file);
        }
        return true;
    }
    
    public static String dictionaryWordBreak(String wordDict) {
        label0: {
            if (wordDict != null && wordDict.length() != 0) {
                if (regexArray == null) {
                    try {
                        java.util.regex.Pattern[] a = new java.util.regex.Pattern[5];
                        a[0] = java.util.regex.Pattern.compile(THAI_REGEX_0);
                        a[1] = java.util.regex.Pattern.compile(THAI_REGEX_1);
                        a[2] = java.util.regex.Pattern.compile(THAI_REGEX_2);
                        a[3] = java.util.regex.Pattern.compile(THAI_REGEX_3);
                        a[4] = java.util.regex.Pattern.compile(THAI_REGEX_4);
                        regexArray = a;
                    } catch(Throwable ignoredException) {
                        break label0;
                    }
                }
                int i = regexArray.length;
                java.util.regex.Matcher[] a0 = new java.util.regex.Matcher[i];
                int i0 = 0;
                for(; i0 < i; i0 = i0 + 1) {
                    a0[i0] = regexArray[i0].matcher((CharSequence)(Object)wordDict);
                }
                java.util.ArrayList a1 = new java.util.ArrayList();
                int i1 = wordDict.length();
                int i2 = 0;
                int i3 = 0;
                while(i2 < i1) {
                    i2 = i1;
                    int i4 = 0;
                    for(; i4 < i; i4 = i4 + 1) {
                        if (a0[i4].reset().find(i3)) {
                            i2 = Math.min(i2, a0[i4].start() + 1);
                        }
                    }
                    {
                        if (i2 >= i1) {
                            continue;
                        }
                        a1.add((Object)Integer.valueOf(i2));
                        i3 = i2;
                    }
                }
                if (a1.size() > 0) {
                    StringBuilder a2 = new StringBuilder(wordDict);
                    int i5 = a1.size() - 1;
                    for(; i5 >= 0; i5 = i5 + -1) {
                        a2.insert(((Integer)a1.get(i5)).intValue(), "<wb>");
                    }
                    wordDict = a2.toString();
                }
            }
            return wordDict;
        }
        regexArray = null;
        return wordDict;
    }
    
    public static void disableDim() {
        mActivity.runOnUiThread(new disableDimTask());
    }
    
    public static void enableDim() {
        mActivity.runOnUiThread(new enableDimTask());
    }
    
    public static String encodeCStringForBase64(String data, String key) {
        try {
            byte[] aesKey = new byte[16];
            System.arraycopy(key.getBytes(StandardCharsets.UTF_8), 0, aesKey, 0, Math.min(16, key.getBytes(StandardCharsets.UTF_8).length));
            SecretKeySpec secKey = new SecretKeySpec(aesKey, "AES");
            Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
            aes.init(1, secKey);
            return sg.gumi.bravefrontier.Base64.encodeToString(aes.doFinal(data.getBytes()), false);
        }
        catch (Exception ex)
        {
            return "";
        }
    }
    
    public static boolean existsBundleFile(String fileName) {
        InputStream stream;

        try {
            stream = mActivity.getResources().getAssets().open(fileName);

        } catch(Throwable ignoredException) {
            return false;
        }

        if (stream != null) {
            try {
                stream.close();
            } catch(Throwable ignoredException) {}
        }

        return true;
    }
    
    public static String getAppVersion() {
        try {
           return mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0).versionName;
        } catch(Throwable ex) {
            ex.printStackTrace();
            return "";
        }
    }
    
    public static int getAvailableMemory() {
        ActivityManager service = (ActivityManager)mActivity.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        service.getMemoryInfo(info);
        return (int)info.availMem;
    }
    
    public static String getDeepLinkParam() {
        return deepLinkParam;
    }
    
    public static String getDeviceAdvertisingID() {
        return BraveFrontier.getDeviceAdvertisingID();
    }
    
    public static String getDeviceID() {
        String devId = null;
        TelephonyManager manager = (TelephonyManager)mActivity.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (VERSION.SDK_INT < 26) {
            devId = manager.getDeviceId();
        } else {
            devId = manager.getMeid();
            if (!devId.isEmpty()) {
                devId = manager.getImei();
            }
        }
        String androidId = Settings.Secure.getString(mActivity.getContentResolver(), "android_id");
        return new UUID(androidId.hashCode(), devId.hashCode()).toString();
    }
    
    public static String getDeviceLanguage() {
        return Locale.getDefault().getLanguage();
    }
    
    public static String getDeviceLanguageLocale() {
        String language;
        String country;
        android.content.res.Configuration a = mActivity.getResources().getConfiguration();
        if (VERSION.SDK_INT < 24) {
            language = a.locale.getLanguage();
            country = a.locale.getCountry();
        } else {
            language = a.getLocales().get(0).getLanguage();
            country = a.getLocales().get(0).getCountry();
        }
        return language +
                "." +
                language +
                "_" +
                country;
    }
    
    public static String getDeviceManufacturer() {
        return (PLATFORM != PLATFORM_AMAZON) ? android.os.Build.MANUFACTURER : "Amazon";
    }
    
    public static String getDeviceModel() {
        return android.os.Build.MODEL +
                "_android" +
                VERSION.RELEASE;
    }
    
    public static String getDeviceSDKVersion() {
        return String.valueOf(VERSION.SDK_INT);
    }
    
    public static String getDeviceVersion() {
        return VERSION.RELEASE;
    }
    
    public static String getISOCountryCode() {
        TelephonyManager manager = (TelephonyManager)mActivity.getSystemService(Context.TELEPHONY_SERVICE);
        String isoCountry = (manager == null) ? "" : manager.getSimCountryIso();
        if (!isoCountry.isEmpty()) {
            isoCountry = isoCountry.toUpperCase();
        }
        return isoCountry;
    }
    
    public static void getLeaderBoardScore(String leaderboard) {
        if (mActivity instanceof sg.gumi.bravefrontier.BraveFrontier) {
            ((sg.gumi.bravefrontier.BaseGameActivity)mActivity).getGameService().getLeaderBoardScore(leaderboard);
        }
    }
    
    public static String getNameFromAccount() {
        return mActivity.getSharedPreferences(PREFS_NAME_AUTH, 0).getString("name", "");
    }
    
    public static int getNetworkState() {
        if (!((ConnectivityManager)mActivity.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(1).isConnected()) {
            return 0;
        }
        return 1;
    }
    
    public static long getNowUnixTimeForMulti() {
        return System.currentTimeMillis();
    }
    
    public static String getPasswordFromAccount() {
        return mActivity.getSharedPreferences(PREFS_NAME_AUTH, 0).getString("password", "");
    }
    
    public static int getSecondsFromDate(String s) {
        java.util.Date a = null;
        String s0 = Calendar.getInstance().get(1) +
                " " +
                s;
        java.util.Date a1 = java.util.Calendar.getInstance().getTime();
        java.text.SimpleDateFormat a2 = new java.text.SimpleDateFormat("yyyy MMM dd, HH:mm  z", Locale.getDefault());
        try {
            a = a2.parse(s0);
        } catch(Throwable a3) {
            a3.printStackTrace();
            a = null;
        }
        return (int)(a.getTime() - a1.getTime()) / 1000;
    }
    
    public static String getSharedPrefString(String key) {
        return mActivity.getSharedPreferences(PREFS_NAME_GAMEDATA, 0).getString(key, "");
    }
    
    public static int getUnzipStatus() {
        return s_UnzipStatus;
    }
    
    public static String getWritablePath() {
        String s = null;
        label1: {
            label0: try {
                s = ((android.app.Activity)mActivity).getBaseContext().getFilesDir().getAbsolutePath();
                break label1;
            } catch(Throwable ignoredException) {
                String s0 = null;
                try {
                    s0 = sg.gumi.bravefrontier.BraveFrontier.getAppContext().getFilesDir().getAbsolutePath();
                } catch(Throwable ignoredException0) {
                    break label0;
                }
                return s0;
            }
            String s1 = null;
            return s1;
        }
        return s;
    }
    
    public static boolean hasDeepLinkParam() {
        return deepLinkParam != null;
    }
    
    public static void initialize(Cocos2dxActivity activity) {
        try {
            mActivity = activity;
            Uri dataUri = activity.getIntent().getData();
            if (dataUri.getScheme().equals("gumiasia") && dataUri.getHost().equals("bravefrontier")) {
                deepLinkParam = dataUri.getPath();
            }
        } catch(Throwable ignoredException) {
        }
    }
    
    public static boolean isCanSimultaneousDownload() {
        if (VERSION.SDK_INT < 11) {
            return false;
        }
        label0: {
            label5: {
                label1: {
                    int i = 0;
                    label9: {
                        boolean b = false;
                        label3: {
                            label4: try {
                                android.net.ConnectivityManager a = (android.net.ConnectivityManager)((android.app.Activity)mActivity).getBaseContext().getSystemService("connectivity");
                                android.telephony.TelephonyManager a0 = (android.telephony.TelephonyManager)((android.app.Activity)mActivity).getBaseContext().getSystemService("phone");
                                android.net.NetworkInfo a1 = a.getActiveNetworkInfo();
                                int i0 = android.os.Build$VERSION.SDK_INT;
                                label8: {
                                    if (i0 >= 23) {
                                        break label8;
                                    }
                                    int i1 = a1.getType();
                                    label7: {
                                        if (i1 == 0) {
                                            break label7;
                                        }
                                        label6: {
                                            if (i1 == 1) {
                                                break label6;
                                            }
                                            break label1;
                                        }
                                        if (a.getNetworkInfo(1).isConnected()) {
                                            break label5;
                                        }
                                    }
                                    int i2 = a0.getNetworkType();
                                    if (i2 == 1) {
                                        break label4;
                                    }
                                    if (i2 == 2) {
                                        break label4;
                                    }
                                    break label1;
                                }
                                if (a == null) {
                                    break label1;
                                }
                                android.net.NetworkCapabilities a2 = a.getNetworkCapabilities(a.getActiveNetwork());
                                if (a2 == null) {
                                    break label1;
                                }
                                boolean b0 = a2.hasTransport(1);
                                label2: {
                                    if (!b0) {
                                        break label2;
                                    }
                                    b = a1.isConnected();
                                    break label3;
                                }
                                if (!a2.hasTransport(0)) {
                                    break label1;
                                }
                                i = a1.getSubtype();
                                break label9;
                            } catch(Throwable ignoredException) {
                            }
                            return false;
                        }
                        if (b) {
                            return true;
                        }
                        break label1;
                    }
                    if (i == 1) {
                        break label0;
                    }
                    if (i == 2) {
                        break label0;
                    }
                }
                return true;
            }
            return true;
        }
        return false;
    }
    
    public static boolean isWebViewAvailable() {
        return BFWebView.isWebViewAvailable();
    }
    
    public static boolean isWebViewVisible() {
        return BFWebView.isWebViewVisible();
    }
    
    public static boolean launchNewApplication(String url) {
        return BFWebView.launchNewApplication(url);
    }
    
    public static void launchNewBrowser(String url) {
        sg.gumi.bravefrontier.webview.BFWebView.launchNewBrowser(url);
    }
    
    public static void leaveBreadcrumb(String unk) {
    }
    
    public static void leaveHandledException(String exception) {
    }
    
    native public static void nativeDownloadCallback(long arg, byte[] arg0, String arg1);
    
    
    native public static void nativeRateThisAppPopupCallback(int arg);
    
    
    native public static void nativeSetDeviceRegistrationId(String arg);
    
    
    native public static void onDeviceShake();
    
    
    public static void outputLogcat(String log) {
        Log.d("barave", log);
    }
    
    native public static void playPhonePurchaseFailCallBack();
    
    
    native public static void playPhonePurchaseSuccessCallBack(String arg, String arg0, String arg1);
    
    
    public static void playVideo(String s) {
        ((android.app.Activity)mActivity).runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$5(s));
    }
    
    public static void playVideoWithOption(String s, boolean b) {
        ((android.app.Activity)mActivity).runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$6(s, b));
    }
    
    public static void playYoutubeVideo(String url) {
        if (sg.gumi.util.BFConfig.PLATFORM != sg.gumi.util.BFConfig.PLATFORM_AMAZON) {
            android.content.Context a = ((android.app.Activity)mActivity).getApplicationContext();
            ((android.app.Activity)mActivity).runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$8(url, a));
        } else {
            BFWebView.playYoutubeVideo(url);
        }
    }
    
    native public static void purchaseStateChangedAdmobCallback(String arg, String arg0, String arg1, int arg2);
    
    
    native public static void purchaseStateChangedCYPayCallback(String arg, String arg0, String arg1, String arg2, int arg3);
    
    
    native public static void purchaseStateChangedCallback(String arg, String arg0, String arg1);
    
    
    private static String replaceGooglePlayStoreName(String s, String s0, String s1) {
        return s.replace((CharSequence)(Object)"Google Play Store", (CharSequence)(Object)s0).replace((CharSequence)(Object)"Google Play", (CharSequence)(Object)s1).replace((CharSequence)(Object)"Google", (CharSequence)(Object)s1);
    }
    
    public static void saveToAccount(String name, String pass) {
        SharedPreferences.Editor a = mActivity.getSharedPreferences(PREFS_NAME_AUTH, 0).edit();
        a.putString("name", name);
        a.putString("password", pass);
        a.commit();
    }
    
    public static void sendAdSdkActionResult(String result) {
    }
    
    native public static void setMultiInvateSchemeData(String arg, String arg0);
    
    
    public static void setSharedPrefString(String key, String value) {
        SharedPreferences.Editor editor = mActivity.getSharedPreferences(PREFS_NAME_GAMEDATA, 0).edit();
        editor.putString(key, value);
        editor.commit();
    }
    
    public static void setWebViewVisible(boolean visible) {
        sg.gumi.bravefrontier.webview.BFWebView.setWebViewVisible(visible);
    }
    
    public static void showPlayPhoneButton(boolean visible) {
        if (mActivity instanceof sg.gumi.bravefrontier.BraveFrontier) {
            ((sg.gumi.bravefrontier.BaseGameActivity)mActivity).getGameService().showPlayPhoneButton(visible);
        }
    }
    
    private static void showRateThisAppPopup(String s, String s0, String s1, String s2) {
        mActivity.runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$7(s0, s, s1, s2));
    }
    
    public static void showWebView(String s, float f, float f0, float f1, float f2) {
        sg.gumi.bravefrontier.webview.BFWebView.showWebView(s, f, f0, f1, f2);
    }
    
    public static void submitLeaderBoardScore(String leaderboard, int score) {
        if (mActivity instanceof sg.gumi.bravefrontier.BraveFrontier) {
            ((BaseGameActivity)mActivity).getGameService().submitLeaderBoardScore(leaderboard, score);
        }
    }
    
    public static void unpackNoDLCRes() {
        s_UnzipStatus = 1;
        new Thread(new unpackNoDlcTask()).start();
    }
    
    native public static void videoFinishedCallback();
    
    
    native public static void videoPreparedCallback();
    
    
    native public static void videoSkippedCallback();
}
