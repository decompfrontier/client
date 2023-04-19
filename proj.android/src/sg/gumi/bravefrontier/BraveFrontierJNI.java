package sg.gumi.bravefrontier;

public class BraveFrontierJNI {
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
    private static org.cocos2dx.lib.Cocos2dxActivity mActivity;
    private static java.util.regex.Pattern[] regexArray;
    static int s_UnzipStatus;
    
    static {
    }
    
    public BraveFrontierJNI() {
    }
    
    static org.cocos2dx.lib.Cocos2dxActivity access$000() {
        return mActivity;
    }
    
    static String access$100(String s, String s0, String s1) {
        return sg.gumi.bravefrontier.BraveFrontierJNI.replaceGooglePlayStoreName(s, s0, s1);
    }
    
    public static void appExit() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    
    public static void appsflyerStartTracking() {
        sg.gumi.bravefrontier.BraveFrontier.appsflyerStartTracking();
    }
    
    native public static void backButtonCallback();
    
    
    public static boolean canLaunchUrl(String s) {
        return sg.gumi.bravefrontier.webview.BFWebView.canLaunchUrl(s);
    }
    
    public static void cancelLocalNotifications() {
        android.content.Context a = ((android.app.Activity)mActivity).getApplicationContext();
        android.app.AlarmManager a0 = (android.app.AlarmManager)a.getSystemService("alarm");
        android.content.Intent a1 = new android.content.Intent(a, sg.gumi.bravefrontier.AlarmReceiver.class);
        a1.setAction("EnergyNotif");
        a0.cancel(android.app.PendingIntent.getBroadcast(a, 0, a1, 1073741824));
        a1.setAction("ArenaNotif");
        a0.cancel(android.app.PendingIntent.getBroadcast(a, 0, a1, 1073741824));
        a1.setAction("NoLoginNotif");
        a0.cancel(android.app.PendingIntent.getBroadcast(a, 0, a1, 1073741824));
    }
    
    public static void clearApplicationData() {
        sg.gumi.bravefrontier.BraveFrontierJNI.deleteDir(((android.app.Activity)mActivity).getBaseContext().getFilesDir());
    }
    
    public static void clearDeepLinkParam() {
        deepLinkParam = null;
    }
    
    public static void copyToClipboard(String s) {
        ((android.app.Activity)mActivity).runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$3(s));
    }
    
    public static void createLocalNotification(int i, String s, String s0) {
        android.content.Context a = ((android.opengl.GLSurfaceView)mActivity.getGLView()).getContext();
        java.util.Calendar a0 = java.util.Calendar.getInstance();
        a0.add(13, i);
        android.content.Intent a1 = new android.content.Intent(a, sg.gumi.bravefrontier.AlarmReceiver.class);
        a1.putExtra("notification", s);
        a1.setAction(s0);
        android.app.PendingIntent a2 = android.app.PendingIntent.getBroadcast(a, 0, a1, 1073741824);
        ((android.app.AlarmManager)a.getSystemService("alarm")).set(0, a0.getTimeInMillis(), a2);
    }
    
    public static String decodeCStringForBase64(String s, String s0) {
        String s1 = null;
        try {
            byte[] a = new byte[16];
            System.arraycopy((Object)s0.getBytes("UTF-8"), 0, (Object)a, 0, Math.min(16, s0.getBytes("UTF-8").length));
            javax.crypto.spec.SecretKeySpec a0 = new javax.crypto.spec.SecretKeySpec(a, "AES");
            javax.crypto.Cipher a1 = javax.crypto.Cipher.getInstance("AES");
            a1.init(2, (java.security.Key)(Object)a0);
            s1 = new String(a1.doFinal(sg.gumi.bravefrontier.Base64.decode(s)));
        } catch(Throwable ignoredException) {
            return "";
        }
        return s1;
    }
    
    public static boolean deleteDir(java.io.File a) {
        if (a != null && a.isDirectory()) {
            String[] a0 = a.list();
            int i = 0;
            for(; i < a0.length; i = i + 1) {
                if (!sg.gumi.bravefrontier.BraveFrontierJNI.deleteDir(new java.io.File(a, a0[i]))) {
                    return false;
                }
            }
        }
        return a.delete();
    }
    
    public static boolean deleteLocalFiles() {
        String[] a = ((android.app.Activity)mActivity).fileList();
        int i = a.length;
        int i0 = 0;
        for(; i0 < i; i0 = i0 + 1) {
            String s = a[i0];
            ((android.app.Activity)mActivity).deleteFile(s);
        }
        return true;
    }
    
    public static String dictionaryWordBreak(String s) {
        label0: {
            if (s != null && s.length() != 0) {
                if (regexArray == null) {
                    try {
                        java.util.regex.Pattern[] a = new java.util.regex.Pattern[5];
                        a[0] = java.util.regex.Pattern.compile("(\u0e2f|[\u0e30-\u0e3a]|[\u0e45-\u0e4e]|\\)|\\]|\\}|\")(?=([\u0e40-\u0e44]|\\(|\\[|\\{|\"))");
                        a[1] = java.util.regex.Pattern.compile("([\u0e01-\u0e3a\u0e40-\u0e4f\u0e5a\u0e5b])(?![\\)\\]\\}\"]|[\u0e01-\u0e3a\u0e40-\u0e4f\u0e5a\u0e5b])");
                        a[2] = java.util.regex.Pattern.compile("([^\u0e01-\u0e3a\u0e40-\u0e4f\u0e5a\u0e5b\\(\\(\\[\\{\"])(?=[\u0e01-\u0e3a\u0e40-\u0e4f\u0e5a\u0e5b])");
                        a[3] = java.util.regex.Pattern.compile("([^[\u0e40-\u0e44]|\\(|\\[|\\{|\"])(?=([\u0e40-\u0e44]|\\(|\\[|\\{|\")*(\u0e40\u0e1b\u0e47\u0e19|\u0e2d\u0e22\u0e39\u0e48|\u0e08\u0e30|\u0e43\u0e0a\u0e49|\u0e44\u0e14\u0e49|\u0e43\u0e2b\u0e49|\u0e43\u0e19|\u0e08\u0e36\u0e07|\u0e2b\u0e23\u0e37\u0e2d|\u0e41\u0e25\u0e30|\u0e01\u0e31\u0e1a|\u0e40\u0e19\u0e37\u0e48\u0e2d\u0e07|\u0e14\u0e49\u0e27\u0e22|\u0e16\u0e49\u0e32|\u0e41\u0e25\u0e49\u0e27|\u0e17\u0e31\u0e49\u0e07|\u0e40\u0e1e\u0e23\u0e32\u0e30|\u0e0b\u0e36\u0e48\u0e07|\u0e0b\u0e49\u0e33|\u0e44\u0e21\u0e48|\u0e43\u0e0a\u0e48|\u0e15\u0e49\u0e2d\u0e07|\u0e01\u0e31\u0e19|\u0e08\u0e32\u0e01|\u0e16\u0e36\u0e07|\u0e19\u0e31\u0e49\u0e19|\u0e1c\u0e39\u0e49|\u0e04\u0e27\u0e32\u0e21|\u0e2a\u0e48\u0e27\u0e19|\u0e22\u0e31\u0e07|\u0e17\u0e31\u0e48\u0e27|\u0e2d\u0e37\u0e48\u0e19|\u0e42\u0e14\u0e22|\u0e2a\u0e32\u0e21\u0e32\u0e23\u0e16|\u0e40\u0e17\u0e48\u0e32|\u0e43\u0e15\u0e49|\u0e43\u0e2a\u0e48|\u0e43\u0e14|\u0e44\u0e27\u0e49|\u0e43\u0e2b\u0e21\u0e48|\u0e43\u0e2b\u0e0d\u0e48|\u0e40\u0e25\u0e47\u0e01|\u0e43\u0e01\u0e25\u0e49|\u0e44\u0e01\u0e25|\u0e40\u0e02\u0e32|\u0e0a\u0e48\u0e27\u0e22|\u0e09\u0e1a\u0e31\u0e1a|\u0e04\u0e49\u0e19|\u0e40\u0e23\u0e47\u0e27|\u0e40\u0e02\u0e49\u0e32|\u0e40\u0e0a\u0e49\u0e32)(\u0e2f|[\u0e30-\u0e3a]|[\u0e45-\u0e4e]|\\)|\\]|\\}|\")?)");
                        a[4] = java.util.regex.Pattern.compile("([\u0e45\u0e46\u0e33])");
                        regexArray = a;
                    } catch(Throwable ignoredException) {
                        break label0;
                    }
                }
                int i = regexArray.length;
                java.util.regex.Matcher[] a0 = new java.util.regex.Matcher[i];
                int i0 = 0;
                for(; i0 < i; i0 = i0 + 1) {
                    a0[i0] = regexArray[i0].matcher((CharSequence)(Object)s);
                }
                java.util.ArrayList a1 = new java.util.ArrayList();
                int i1 = s.length();
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
                    StringBuilder a2 = new StringBuilder(s);
                    int i5 = a1.size() - 1;
                    for(; i5 >= 0; i5 = i5 + -1) {
                        a2.insert(((Integer)a1.get(i5)).intValue(), "<wb>");
                    }
                    s = a2.toString();
                }
            }
            return s;
        }
        regexArray = null;
        return s;
    }
    
    public static void disableDim() {
        ((android.app.Activity)mActivity).runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$2());
    }
    
    public static void enableDim() {
        ((android.app.Activity)mActivity).runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$1());
    }
    
    public static String encodeCStringForBase64(String s, String s0) {
        byte[] a = new byte[16];
        System.arraycopy((Object)s0.getBytes("UTF-8"), 0, (Object)a, 0, Math.min(16, s0.getBytes("UTF-8").length));
        javax.crypto.spec.SecretKeySpec a0 = new javax.crypto.spec.SecretKeySpec(a, "AES");
        javax.crypto.Cipher a1 = javax.crypto.Cipher.getInstance("AES");
        a1.init(1, (java.security.Key)(Object)a0);
        return sg.gumi.bravefrontier.Base64.encodeToString(a1.doFinal(s.getBytes()), false);
    }
    
    public static boolean existsBundleFile(String s) {
        java.io.InputStream a = null;
        try {
            a = ((android.app.Activity)mActivity).getResources().getAssets().open(s);
        } catch(Throwable ignoredException) {
            return false;
        }
        if (a != null) {
            try {
                a.close();
            } catch(Throwable ignoredException0) {
            }
        }
        return true;
    }
    
    public static String getAppVersion() {
        String s = null;
        try {
            s = ((android.app.Activity)mActivity).getPackageManager().getPackageInfo(((android.app.Activity)mActivity).getPackageName(), 0).versionName;
        } catch(Throwable a) {
            a.printStackTrace();
            return "";
        }
        return s;
    }
    
    public static int getAvailableMemory() {
        android.app.ActivityManager a = (android.app.ActivityManager)((android.app.Activity)mActivity).getSystemService("activity");
        android.app.ActivityManager$MemoryInfo a0 = new android.app.ActivityManager$MemoryInfo();
        a.getMemoryInfo(a0);
        return (int)a0.availMem;
    }
    
    public static String getDeepLinkParam() {
        return deepLinkParam;
    }
    
    public static String getDeviceAdvertisingID() {
        return sg.gumi.bravefrontier.BraveFrontier.getDeviceAdvertisingID();
    }
    
    public static String getDeviceID() {
        String s = null;
        android.telephony.TelephonyManager a = (android.telephony.TelephonyManager)((android.app.Activity)mActivity).getBaseContext().getSystemService("phone");
        if (android.os.Build$VERSION.SDK_INT < 26) {
            StringBuilder a0 = new StringBuilder();
            a0.append("");
            a0.append(a.getDeviceId());
            s = a0.toString();
        } else {
            StringBuilder a1 = new StringBuilder();
            a1.append("");
            a1.append(a.getImei());
            String s0 = a1.toString();
            StringBuilder a2 = new StringBuilder();
            a2.append("");
            a2.append(a.getMeid());
            s = a2.toString();
            if (!s0.isEmpty()) {
                s = s0;
            }
        }
        StringBuilder a3 = new StringBuilder();
        a3.append("");
        a3.append(android.provider.Settings$Secure.getString(((android.app.Activity)mActivity).getContentResolver(), "android_id"));
        return new java.util.UUID((long)a3.toString().hashCode(), (long)s.hashCode()).toString();
    }
    
    public static String getDeviceLanguage() {
        return java.util.Locale.getDefault().getLanguage();
    }
    
    public static String getDeviceLanguageLocale() {
        String s = null;
        String s0 = null;
        android.content.res.Configuration a = ((android.app.Activity)mActivity).getResources().getConfiguration();
        if (android.os.Build$VERSION.SDK_INT < 24) {
            s = a.locale.getLanguage();
            s0 = a.locale.getCountry();
        } else {
            s = a.getLocales().get(0).getLanguage();
            s0 = a.getLocales().get(0).getCountry();
        }
        StringBuilder a0 = new StringBuilder();
        a0.append(s);
        a0.append(".");
        a0.append(s);
        a0.append("_");
        a0.append(s0);
        return a0.toString();
    }
    
    public static String getDeviceManufacturer() {
        return (sg.gumi.util.BFConfig.PLATFORM != sg.gumi.util.BFConfig.PLATFORM_AMAZON) ? android.os.Build.MANUFACTURER : "Amazon";
    }
    
    public static String getDeviceModel() {
        StringBuilder a = new StringBuilder();
        a.append(android.os.Build.MODEL);
        a.append("_android");
        a.append(android.os.Build$VERSION.RELEASE);
        return a.toString();
    }
    
    public static String getDeviceSDKVersion() {
        StringBuilder a = new StringBuilder();
        a.append(android.os.Build$VERSION.SDK_INT);
        a.append("");
        return a.toString();
    }
    
    public static String getDeviceVersion() {
        return android.os.Build$VERSION.RELEASE;
    }
    
    public static String getISOCountryCode() {
        android.telephony.TelephonyManager a = (android.telephony.TelephonyManager)((android.app.Activity)mActivity).getSystemService("phone");
        String s = (a == null) ? "" : a.getSimCountryIso();
        if (!s.equals((Object)"")) {
            s = s.toUpperCase();
        }
        return s;
    }
    
    public static void getLeaderBoardScore(String s) {
        org.cocos2dx.lib.Cocos2dxActivity a = mActivity;
        if (a instanceof sg.gumi.bravefrontier.BraveFrontier) {
            ((sg.gumi.bravefrontier.BaseGameActivity)(sg.gumi.bravefrontier.BraveFrontier)a).getGameService().getLeaderBoardScore(s);
        }
    }
    
    public static String getNameFromAccount() {
        return ((android.app.Activity)mActivity).getSharedPreferences("auth", 0).getString("name", "");
    }
    
    public static int getNetworkState() {
        if (!((android.net.ConnectivityManager)((android.app.Activity)mActivity).getSystemService("connectivity")).getNetworkInfo(1).isConnected()) {
            return 0;
        }
        return 1;
    }
    
    public static long getNowUnixTimeForMulti() {
        return System.currentTimeMillis();
    }
    
    public static String getPasswordFromAccount() {
        return ((android.app.Activity)mActivity).getSharedPreferences("auth", 0).getString("password", "");
    }
    
    public static int getSecondsFromDate(String s) {
        java.util.Date a = null;
        StringBuilder a0 = new StringBuilder();
        a0.append(java.util.Calendar.getInstance().get(1));
        a0.append(" ");
        a0.append(s);
        String s0 = a0.toString();
        java.util.Date a1 = java.util.Calendar.getInstance().getTime();
        java.text.SimpleDateFormat a2 = new java.text.SimpleDateFormat("yyyy MMM dd, HH:mm  z");
        try {
            a = a2.parse(s0);
        } catch(Throwable a3) {
            a3.printStackTrace();
            a = null;
        }
        return (int)(a.getTime() - a1.getTime()) / 1000;
    }
    
    public static String getSharedPrefString(String s) {
        return ((android.app.Activity)mActivity).getSharedPreferences("gamedata", 0).getString(s, "");
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
    
    public static void initialize(org.cocos2dx.lib.Cocos2dxActivity a) {
        try {
            mActivity = a;
            android.net.Uri a0 = ((android.app.Activity)a).getIntent().getData();
            if (a0.getScheme().equals((Object)"gumiasia") && a0.getHost().equals((Object)"bravefrontier")) {
                deepLinkParam = a0.getPath();
            }
        } catch(Throwable ignoredException) {
        }
    }
    
    public static boolean isCanSimultaneousDownload() {
        if (android.os.Build$VERSION.SDK_INT < 11) {
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
        return sg.gumi.bravefrontier.webview.BFWebView.isWebViewAvailable();
    }
    
    public static boolean isWebViewVisible() {
        return sg.gumi.bravefrontier.webview.BFWebView.isWebViewVisible();
    }
    
    public static boolean launchNewApplication(String s) {
        return sg.gumi.bravefrontier.webview.BFWebView.launchNewApplication(s);
    }
    
    public static void launchNewBrowser(String s) {
        sg.gumi.bravefrontier.webview.BFWebView.launchNewBrowser(s);
    }
    
    public static void leaveBreadcrumb(String s) {
    }
    
    public static void leaveHandledException(String s) {
    }
    
    native public static void nativeDownloadCallback(long arg, byte[] arg0, String arg1);
    
    
    native public static void nativeRateThisAppPopupCallback(int arg);
    
    
    native public static void nativeSetDeviceRegistrationId(String arg);
    
    
    native public static void onDeviceShake();
    
    
    public static void outputLogcat(String s) {
        android.util.Log.d("barave", s);
    }
    
    native public static void playPhonePurchaseFailCallBack();
    
    
    native public static void playPhonePurchaseSuccessCallBack(String arg, String arg0, String arg1);
    
    
    public static void playVideo(String s) {
        ((android.app.Activity)mActivity).runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$5(s));
    }
    
    public static void playVideoWithOption(String s, boolean b) {
        ((android.app.Activity)mActivity).runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$6(s, b));
    }
    
    public static void playYoutubeVideo(String s) {
        if (sg.gumi.util.BFConfig.PLATFORM != sg.gumi.util.BFConfig.PLATFORM_AMAZON) {
            android.content.Context a = ((android.app.Activity)mActivity).getApplicationContext();
            ((android.app.Activity)mActivity).runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$8(s, a));
        } else {
            sg.gumi.bravefrontier.webview.BFWebView.playYoutubeVideo(s);
        }
    }
    
    native public static void purchaseStateChangedAdmobCallback(String arg, String arg0, String arg1, int arg2);
    
    
    native public static void purchaseStateChangedCYPayCallback(String arg, String arg0, String arg1, String arg2, int arg3);
    
    
    native public static void purchaseStateChangedCallback(String arg, String arg0, String arg1);
    
    
    private static String replaceGooglePlayStoreName(String s, String s0, String s1) {
        return s.replace((CharSequence)(Object)"Google Play Store", (CharSequence)(Object)s0).replace((CharSequence)(Object)"Google Play", (CharSequence)(Object)s1).replace((CharSequence)(Object)"Google", (CharSequence)(Object)s1);
    }
    
    public static void saveToAccount(String s, String s0) {
        android.content.SharedPreferences$Editor a = ((android.app.Activity)mActivity).getSharedPreferences("auth", 0).edit();
        a.putString("name", s);
        a.putString("password", s0);
        a.commit();
    }
    
    public static void sendAdSdkActionResult(String s) {
    }
    
    native public static void setMultiInvateSchemeData(String arg, String arg0);
    
    
    public static void setSharedPrefString(String s, String s0) {
        android.content.SharedPreferences$Editor a = ((android.app.Activity)mActivity).getSharedPreferences("gamedata", 0).edit();
        a.putString(s, s0);
        a.commit();
    }
    
    public static void setWebViewVisible(boolean b) {
        sg.gumi.bravefrontier.webview.BFWebView.setWebViewVisible(b);
    }
    
    public static void showPlayPhoneButton(boolean b) {
        org.cocos2dx.lib.Cocos2dxActivity a = mActivity;
        if (a instanceof sg.gumi.bravefrontier.BraveFrontier) {
            ((sg.gumi.bravefrontier.BaseGameActivity)(sg.gumi.bravefrontier.BraveFrontier)a).getGameService().showPlayPhoneButton(b);
        }
    }
    
    private static void showRateThisAppPopup(String s, String s0, String s1, String s2) {
        ((android.app.Activity)mActivity).runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$7(s0, s, s1, s2));
    }
    
    public static void showWebView(String s, float f, float f0, float f1, float f2) {
        sg.gumi.bravefrontier.webview.BFWebView.showWebView(s, f, f0, f1, f2);
    }
    
    public static void submitLeaderBoardScore(String s, int i) {
        org.cocos2dx.lib.Cocos2dxActivity a = mActivity;
        if (a instanceof sg.gumi.bravefrontier.BraveFrontier) {
            ((sg.gumi.bravefrontier.BaseGameActivity)(sg.gumi.bravefrontier.BraveFrontier)a).getGameService().submitLeaderBoardScore(s, i);
        }
    }
    
    public static void unpackNoDLCRes() {
        s_UnzipStatus = 1;
        new Thread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$4()).start();
    }
    
    native public static void videoFinishedCallback();
    
    
    native public static void videoPreparedCallback();
    
    
    native public static void videoSkippedCallback();
}
