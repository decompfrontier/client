package sg.gumi.bravefrontier;

public class BraveFrontier extends sg.gumi.bravefrontier.BaseGameActivity {
    final public static String FIVEROCKS_APP_KEY = "UqfUMS53gACQAACCHQAABgEC4ueo1H0hmTfztQ3Byo7QZsTHevDXgt9Tjf0y";
    final public static String FLURRY_ID = "989N9DD6NYP8GZTJ53K2";
    private static sg.gumi.bravefrontier.BraveFrontier act;
    private static android.content.Context context;
    private static String deviceAdvertisingID = "";
    private static sg.gumi.bravefrontier.Facebook facebook;
    public static boolean fiverocksInitialized = false;
    public static boolean isInitialized = false;
    private static android.telephony.PhoneStateListener phoneStateListener;
    private float m_LastStoredEffectsVolume;
    private float m_LastStoredMusicVolume;
    private boolean m_isFromStateIdle;
    public android.os.Bundle savedInstanceState;
    
    static {
        try {
            com.tapjoy.Tapjoy.loadSharedLibrary();
        } catch(Throwable ignoredException) {
        }
        try {
            System.loadLibrary("game");
            org.cocos2dx.lib.Cocos2dxHelper.checkNativeSetApkPathMethod();
            org.cocos2dx.lib.Cocos2dxHelper.setNativeLibraryLoaded(true);
        } catch(Throwable ignoredException0) {
            org.cocos2dx.lib.Cocos2dxHelper.setNativeLibraryLoaded(false);
        }
    }
    
    public BraveFrontier() {
        this.savedInstanceState = null;
        this.m_isFromStateIdle = true;
        this.m_LastStoredMusicVolume = 0.0f;
        this.m_LastStoredEffectsVolume = 0.0f;
    }
    
    public static void GPGSSignIn() {
        android.util.Log.e("BraveFrontier", "Start Sign In");
        if (((sg.gumi.bravefrontier.BaseGameActivity)act).getGameService().getSignInCancellations() <= 0) {
            ((android.app.Activity)act).runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontier$6());
        }
    }
    
    public static void GPGSSignOut() {
        ((sg.gumi.bravefrontier.BaseGameActivity)act).signOut();
    }
    
    static String access$002(String s) {
        deviceAdvertisingID = s;
        return s;
    }
    
    static boolean access$100(sg.gumi.bravefrontier.BraveFrontier a) {
        return a.m_isFromStateIdle;
    }
    
    static boolean access$102(sg.gumi.bravefrontier.BraveFrontier a, boolean b) {
        a.m_isFromStateIdle = b;
        return b;
    }
    
    static float access$200(sg.gumi.bravefrontier.BraveFrontier a) {
        return a.m_LastStoredMusicVolume;
    }
    
    static float access$202(sg.gumi.bravefrontier.BraveFrontier a, float f) {
        a.m_LastStoredMusicVolume = f;
        return f;
    }
    
    static float access$300(sg.gumi.bravefrontier.BraveFrontier a) {
        return a.m_LastStoredEffectsVolume;
    }
    
    static float access$302(sg.gumi.bravefrontier.BraveFrontier a, float f) {
        a.m_LastStoredEffectsVolume = f;
        return f;
    }
    
    static sg.gumi.bravefrontier.BraveFrontier access$400() {
        return act;
    }
    
    public static void appsflyerStartTracking() {
        com.appsflyer.AppsFlyerLib.getInstance().start((android.content.Context)act);
    }
    
    private void checkLoadedLibraries() {
        if (!org.cocos2dx.lib.Cocos2dxHelper.isNativeLibraryLoaded()) {
            sg.gumi.bravefrontier.BraveFrontier$4 a = new sg.gumi.bravefrontier.BraveFrontier$4(this);
            new android.app.AlertDialog$Builder((android.content.Context)this).setTitle((CharSequence)(Object)"Install Error").setMessage((CharSequence)(Object)"Install failed because there isn't enough free space on device internal (non-SD Card) memory. Free up some space and reinstall again.").setPositiveButton((CharSequence)(Object)"Exit", (android.content.DialogInterface$OnClickListener)(Object)a).setNegativeButton((CharSequence)(Object)"Free Space", (android.content.DialogInterface$OnClickListener)(Object)a).create().show();
        }
    }
    
    public static void crashlyticsCustomLog(String s) {
        com.google.firebase.crashlytics.FirebaseCrashlytics.getInstance().log(s);
    }
    
    public static void crashlyticsForceCrash() {
        throw new RuntimeException("Testing crashlytics force crashing...");
    }
    
    public static void crashlyticsSetUserIdentifier(String s) {
        com.google.firebase.crashlytics.FirebaseCrashlytics.getInstance().setUserId(s);
    }
    
    public static void crashlyticsSetUserName(String s) {
        com.google.firebase.crashlytics.FirebaseCrashlytics.getInstance().setCustomKey("UserName", s);
    }
    
    public static sg.gumi.bravefrontier.BraveFrontier getActivity() {
        return act;
    }
    
    public static String getAmazonUserId() {
        return "";
    }
    
    public static String getAndroidId() {
        return android.provider.Settings$Secure.getString(((android.app.Activity)act).getContentResolver(), "android_id");
    }
    
    public static android.content.Context getAppContext() {
        return context;
    }
    
    public static String getAppName() {
        try {
            return ((android.app.Activity)act).getResources().getString(2131558429);
        } catch(android.content.res.Resources$NotFoundException a) {
            a.printStackTrace();
            return "Brave Frontier";
        }
    }
    
    public static String getBuildNo() {
        try {
            return ((android.app.Activity)act).getResources().getString(2131558431);
        } catch(android.content.res.Resources$NotFoundException a) {
            a.printStackTrace();
            return "";
        }
    }
    
    public static String getBundleName() {
        return ((android.app.Activity)act).getPackageName();
    }
    
    public static String getBundleVersion() {
        android.content.pm.PackageInfo a = null;
        try {
            a = ((android.app.Activity)act).getPackageManager().getPackageInfo(((android.app.Activity)act).getPackageName(), 0);
        } catch(android.content.pm.PackageManager$NameNotFoundException a0) {
            a0.printStackTrace();
            a = null;
        }
        return a.versionName;
    }
    
    public static String getDeviceAdvertisingID() {
        return deviceAdvertisingID;
    }
    
    public static String getDeviceArchitecture() {
        return System.getProperty("os.arch");
    }
    
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }
    
    public static String getDeviceUUID() {
        String s = null;
        android.telephony.TelephonyManager a = (android.telephony.TelephonyManager)((android.app.Activity)act).getBaseContext().getSystemService("phone");
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
        a3.append(a.getSimSerialNumber());
        String s1 = a3.toString();
        StringBuilder a4 = new StringBuilder();
        a4.append("");
        a4.append(android.provider.Settings$Secure.getString(((android.app.Activity)act).getContentResolver(), "android_id"));
        return new java.util.UUID((long)a4.toString().hashCode(), (long)s.hashCode() << 32 | (long)s1.hashCode()).toString();
    }
    
    public static long getExternalStorageSpace() {
        long j = 0L;
        long j0 = 0L;
        android.os.StatFs a = new android.os.StatFs(android.os.Environment.getExternalStorageDirectory().getPath());
        if (android.os.Build$VERSION.SDK_INT < 18) {
            j = (long)a.getAvailableBlocks();
            j0 = (long)a.getBlockSize();
        } else {
            j = a.getAvailableBlocksLong();
            j0 = a.getBlockSizeLong();
        }
        return j * j0;
    }
    
    public static String getLegacyDeviceUUID() {
        String s = null;
        try {
            android.content.SharedPreferences a = sg.gumi.bravefrontier.BraveFrontier.getAppContext().getSharedPreferences("DEVUUID", 0);
            String s0 = a.getString("DEVUUID", "");
            if (s0.equals((Object)"")) {
                s0 = java.util.UUID.randomUUID().toString();
                android.content.SharedPreferences$Editor a0 = a.edit();
                a0.putString("DEVUUID", s0);
                a0.commit();
            }
            s = s0.toString();
        } catch(Exception a1) {
            a1.printStackTrace();
            return " ";
        }
        return s;
    }
    
    public static String getOSVersion() {
        return android.os.Build$VERSION.RELEASE;
    }
    
    public static String[] getPermissions() {
        String[] a = new String[4];
        a[0] = "android.permission.READ_PHONE_STATE";
        a[1] = "android.permission.INTERNET";
        a[2] = "android.permission.ACCESS_WIFI_STATE";
        a[3] = "android.permission.ACCESS_NETWORK_STATE";
        return a;
    }
    
    public static void goToAppSettings() {
        android.content.Intent a = new android.content.Intent();
        a.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        a.setData(android.net.Uri.fromParts("package", ((android.app.Activity)act).getPackageName(), (String)null));
        ((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity()).startActivity(a);
    }
    
    public static void googleAnalyticsSendScreenView(String s) {
        ((sg.gumi.bravefrontier.BaseGameActivity)act).getGameService().googleAnalyticsSendScreenView(s);
    }
    
    public static void googleAnalyticsSetUserID(String s) {
        ((sg.gumi.bravefrontier.BaseGameActivity)act).getGameService().googleAnalyticsSetUserID(s);
    }
    
    public static void googleAnalyticsTrackEvent(String s, String s0, String s1, long j) {
        ((sg.gumi.bravefrontier.BaseGameActivity)act).getGameService().googleAnalyticsTrackEvent(s, s0, s1, j);
    }
    
    public static void googleAnalyticsTrackPurchase(String s, String s0, String s1, String s2, double d, long j, String s3) {
        ((sg.gumi.bravefrontier.BaseGameActivity)act).getGameService().googleAnalyticsTrackPurchase(s, s0, s1, s2, d, j, s3);
    }
    
    public static boolean hasPermissions() {
        String[] a = sg.gumi.bravefrontier.BraveFrontier.getPermissions();
        if (android.os.Build$VERSION.SDK_INT >= 23 && context != null && a != null) {
            int i = a.length;
            int i0 = 0;
            for(; i0 < i; i0 = i0 + 1) {
                String s = a[i0];
                if (androidx.core.content.ContextCompat.checkSelfPermission(org.cocos2dx.lib.Cocos2dxActivity.getContext(), s) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void initializeStore() {
        sg.gumi.util.BFConfig$Platform a = sg.gumi.util.BFConfig.PLATFORM;
        sg.gumi.util.BFConfig$Platform a0 = sg.gumi.util.BFConfig.PLATFORM_GOOGLE;
        label0: {
            label1: {
                if (a == a0) {
                    break label1;
                }
                if (sg.gumi.util.BFConfig.PLATFORM != sg.gumi.util.BFConfig.PLATFORM_AMAZON) {
                    break label0;
                }
            }
            ((android.app.Activity)act).runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontier$5());
        }
    }
    
    public static boolean isBundleExist() {
        boolean b = false;
        try {
            ((android.app.Activity)act).getPackageManager().getPackageInfo(((android.app.Activity)act).getPackageName(), 0);
            b = true;
        } catch(android.content.pm.PackageManager$NameNotFoundException ignoredException) {
            b = false;
        }
        return b;
    }
    
    public static boolean isFiverocksInitialized() {
        return fiverocksInitialized;
    }
    
    public static boolean isLoadingComplete() {
        if (!android.os.Debug.isDebuggerConnected()) {
            return true;
        }
        return false;
    }
    
    public static boolean isSignedInToGPGS() {
        return ((sg.gumi.bravefrontier.BaseGameActivity)act).isSignedIn();
    }
    
    native public static void onGPGSSignInFailed();
    
    
    native public static void onGPGSSignInSucceeded();
    
    
    native public static void onGPGSSignOutSucceeded();
    
    
    public static void openURL(String s) {
        android.content.Intent a = new android.content.Intent("android.intent.action.VIEW");
        a.setData(android.net.Uri.parse(s));
        ((android.app.Activity)act).startActivity(a);
    }
    
    public static void requestPermissions() {
        if (!sg.gumi.bravefrontier.BraveFrontier.hasPermissions()) {
            androidx.core.app.ActivityCompat.requestPermissions((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity(), sg.gumi.bravefrontier.BraveFrontier.getPermissions(), 1);
        }
    }
    
    public static void resetGPGSAchievements() {
    }
    
    public static void setAppsFlyeruserId(String s) {
        try {
            if (sg.gumi.bravefrontier.AFApplication.isAppsflyerSDKInitialised()) {
                com.appsflyer.AppsFlyerLib.getInstance().setCustomerUserId(s);
            }
        } catch(Throwable ignoredException) {
        }
    }
    
    public static void setRemoteNotificationsEnable(boolean b) {
        sg.gumi.bravefrontier.NotificationService.getInstance().setRemoteNotificationsEnable(act, b);
    }
    
    public static boolean shouldShowRequestPermissionRationale() {
        String[] a = sg.gumi.bravefrontier.BraveFrontier.getPermissions();
        int i = 0;
        while(true) {
            boolean b = false;
            if (i >= a.length) {
                b = false;
            } else {
                if (!androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity(), a[i])) {
                    i = i + 1;
                    continue;
                }
                b = true;
            }
            return b;
        }
    }
    
    public static void showAchievements() {
        sg.gumi.bravefrontier.GameService a = ((sg.gumi.bravefrontier.BaseGameActivity)act).getGameService();
        if (a != null) {
            if (a.isSignedIn()) {
                a.showAchievements();
            } else {
                a.beginUserInitiatedSignIn();
            }
        }
    }
    
    public static void trackEvent2(byte[] a, byte[] a0) {
        String s = (a == null) ? null : new String(a);
        if (a0 != null) {
            String s0 = new String(a0);
        }
        if (act != null) {
            try {
                if (!((android.app.Activity)act).isFinishing() && s != null && sg.gumi.bravefrontier.AFApplication.isAppsflyerSDKInitialised()) {
                    java.util.HashMap a1 = new java.util.HashMap();
                    a1.put((Object)s, (Object)"0");
                    com.appsflyer.AppsFlyerLib.getInstance().logEvent(((android.app.Activity)act).getApplicationContext(), s, (java.util.Map)(Object)a1);
                }
            } catch(Throwable ignoredException) {
            }
        }
    }
    
    public static void trackPurchase(float f, String s) {
        if (act != null) {
            try {
                if (!((android.app.Activity)act).isFinishing()) {
                    label0: {
                        label1: {
                            if (s == null) {
                                break label1;
                            }
                            if (s.isEmpty()) {
                                break label1;
                            }
                            if (!s.equalsIgnoreCase("USD")) {
                                break label0;
                            }
                        }
                        com.google.ads.conversiontracking.AdWordsConversionReporter.reportWithConversionId(((android.app.Activity)act).getApplicationContext(), "963467556", "NxvHCJXllFYQpLK1ywM", String.valueOf(f), true);
                    }
                    if (sg.gumi.bravefrontier.AFApplication.isAppsflyerSDKInitialised()) {
                        java.util.HashMap a = new java.util.HashMap();
                        a.put((Object)"af_revenue", (Object)Float.valueOf(f));
                        com.appsflyer.AppsFlyerLib.getInstance().logEvent(((android.app.Activity)act).getApplicationContext(), "af_purchase", (java.util.Map)(Object)a);
                    }
                }
            } catch(Throwable ignoredException) {
            }
        }
    }
    
    public static void unlockGPGSAchievement(String s) {
        ((sg.gumi.bravefrontier.BaseGameActivity)act).getGameService().unlockAchievement(s);
    }
    
    public static void updateGPGSLeaderboard(int i, String s) {
        ((sg.gumi.bravefrontier.BaseGameActivity)act).getGameService().updateLeaderboard(i, s);
    }
    
    public void onAccelerationChanged(float f, float f0, float f1) {
    }
    
    public void onActivityResult(int i, int i0, android.content.Intent a) {
        android.util.Log.i("onActivityResult", "Recieved activity result");
        label2: {
            String s = null;
            label3: {
                label4: {
                    if (i0 == 0) {
                        break label4;
                    }
                    if (a != null) {
                        break label3;
                    }
                }
                try {
                    ((sg.gumi.bravefrontier.BaseGameActivity)this).onActivityResult(i, i0, a);
                    sg.gumi.bravefrontier.Facebook.callbackmanagerOnActivityResult(i, i0, a);
                    break label2;
                } catch(Throwable ignoredException) {
                    break label2;
                }
            }
            label0: {
                label1: {
                    if (i != 1001) {
                        break label1;
                    }
                    if (sg.gumi.util.BFConfig.PLATFORM != sg.gumi.util.BFConfig.PLATFORM_AMAZON) {
                        break label0;
                    }
                }
                ((sg.gumi.bravefrontier.BaseGameActivity)this).onActivityResult(i, i0, a);
                sg.gumi.bravefrontier.Facebook.callbackmanagerOnActivityResult(i, i0, a);
                break label2;
            }
            if (i0 != -1) {
                if (sg.gumi.util.BFConfig.PLATFORM == sg.gumi.util.BFConfig.PLATFORM_GOOGLE) {
                    com.soomla.store.StoreController.getInstance().onPurchaseStateChange("", "", "");
                }
                int i1 = a.getIntExtra("RESPONSE_CODE", 0);
                StringBuilder a0 = new StringBuilder();
                a0.append("onActivityResult payment attempt fail! Response code: ");
                a0.append(i1);
                android.util.Log.e("onActivityResult", a0.toString());
                return;
            }
            int i2 = a.getIntExtra("RESPONSE_CODE", 0);
            if (sg.gumi.util.BFConfig.PLATFORM == sg.gumi.util.BFConfig.PLATFORM_GOOGLE && i2 > 0) {
                com.soomla.store.StoreController.getInstance().onPurchaseStateChange("", "", "");
                return;
            }
            String s0 = a.getStringExtra("INAPP_PURCHASE_DATA");
            String s1 = a.getStringExtra("INAPP_DATA_SIGNATURE");
            try {
                s = new org.json.JSONObject(s0).getString("productId");
            } catch(Throwable a1) {
                a1.printStackTrace();
                s = null;
            }
            if (sg.gumi.util.BFConfig.PLATFORM == sg.gumi.util.BFConfig.PLATFORM_GOOGLE) {
                com.soomla.store.StoreController.getInstance().onPurchaseStateChange(s0, s1, s);
            }
        }
    }
    
    public void onBackPressed() {
        if (org.cocos2dx.lib.Cocos2dxHelper.isNativeLibraryLoaded()) {
            sg.gumi.bravefrontier.BraveFrontierJNI.backButtonCallback();
        }
    }
    
    protected void onCreate(android.os.Bundle a) {
        ((sg.gumi.bravefrontier.BaseGameActivity)this).onCreate(a);
        com.google.firebase.crashlytics.FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        this.checkLoadedLibraries();
        context = ((android.app.Activity)this).getApplicationContext();
        act = this;
        this.savedInstanceState = a;
        if (sg.gumi.util.BFConfig.PLATFORM == sg.gumi.util.BFConfig.PLATFORM_GOOGLE) {
            try {
                com.google.ads.conversiontracking.AdWordsConversionReporter.reportWithConversionId(((android.app.Activity)this).getApplicationContext(), "963467556", "n0JXCNWzn1gQpLK1ywM", "0.00", true);
            } catch(Throwable ignoredException) {
            }
        }
        new Thread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontier$1(this)).start();
        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof sg.gumi.util.BFUncaughtExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler((Thread$UncaughtExceptionHandler)(Object)new sg.gumi.util.BFUncaughtExceptionHandler());
        }
        if (sg.gumi.bravefrontier.AFApplication.isAppsflyerSDKInitialised() && act != null) {
            StringBuilder a0 = new StringBuilder();
            a0.append("Custom_Event_Arch_");
            a0.append(sg.gumi.bravefrontier.BraveFrontier.getDeviceArchitecture());
            String s = new String(a0.toString());
            java.util.HashMap a1 = new java.util.HashMap();
            a1.put((Object)s, (Object)sg.gumi.bravefrontier.BraveFrontier.getDeviceArchitecture());
            com.appsflyer.AppsFlyerLib.getInstance().logEvent(((android.app.Activity)act).getApplicationContext(), s, (java.util.Map)(Object)a1);
        }
        boolean b = fiverocksInitialized;
        label1: {
            label0: if (b) {
                android.util.Log.d("TAPJOY_DEBUG", "tapjoy is already initliaized");
                break label1;
            } else {
                try {
                    java.util.Hashtable a2 = new java.util.Hashtable();
                    a2.put((Object)"TJC_OPTION_ENABLE_LOGGING", (Object)"false");
                    com.tapjoy.Tapjoy.connect((android.content.Context)this, "UqfUMS53gACQAACCHQAABgEC4ueo1H0hmTfztQ3Byo7QZsTHevDXgt9Tjf0y", a2, (com.tapjoy.TJConnectListener)(Object)new sg.gumi.bravefrontier.BraveFrontier$2(this));
                    com.tapjoy.Tapjoy.setAppDataVersion(sg.gumi.bravefrontier.BraveFrontier.getBundleVersion());
                    com.tapjoy.Tapjoy.setGcmSender("821991734423");
                    com.tapjoy.Tapjoy.setGLSurfaceView((android.opengl.GLSurfaceView)((org.cocos2dx.lib.Cocos2dxActivity)this).getGLView());
                } catch(Throwable ignoredException0) {
                    break label0;
                }
                break label1;
            }
            fiverocksInitialized = false;
        }
        if (sg.gumi.util.BFConfig.PLATFORM == sg.gumi.util.BFConfig.PLATFORM_GOOGLE) {
            try {
                ((sg.gumi.bravefrontier.BaseGameActivity)this).getGameService().initGoogleAnalytics((android.app.Activity)this);
            } catch(Throwable ignoredException1) {
            }
        }
        ((android.app.Activity)this).setVolumeControlStream(3);
        android.util.Log.e("Brave Frontier", "Creating app");
        if (isInitialized) {
            sg.gumi.bravefrontier.video.BFVideoView.clearInstance();
        }
        sg.gumi.bravefrontier.NotificationService.getInstance().onCreate(this);
        sg.gumi.bravefrontier.BraveFrontierJNI.initialize((org.cocos2dx.lib.Cocos2dxActivity)this);
        sg.gumi.bravefrontier.webview.BFWebView.initialize((org.cocos2dx.lib.Cocos2dxActivity)this);
        sg.gumi.bravefrontier.video.BFVideoView.getInstance((org.cocos2dx.lib.Cocos2dxActivity)this);
        if (sg.gumi.util.BFConfig.PLATFORM != sg.gumi.util.BFConfig.PLATFORM_AMAZON) {
            sg.gumi.util.BFConfig$Platform dummy = sg.gumi.util.BFConfig.PLATFORM;
            sg.gumi.util.BFConfig$Platform dummy0 = sg.gumi.util.BFConfig.PLATFORM_SAMSUNG;
        }
        ((android.app.Activity)this).getWindow().setSoftInputMode(3);
        facebook = new sg.gumi.bravefrontier.Facebook(act);
        isInitialized = true;
        phoneStateListener = new sg.gumi.bravefrontier.BraveFrontier$3(this);
        android.telephony.TelephonyManager a3 = (android.telephony.TelephonyManager)((android.app.Activity)act).getBaseContext().getSystemService("phone");
        if (a3 != null) {
            a3.listen(phoneStateListener, 32);
        }
        sg.gumi.bravefrontier.RVHandler.getInstance();
        sg.gumi.bravefrontier.RVHandler.initialiseHandler();
        ((sg.gumi.bravefrontier.BaseGameActivity)this).hideSystemUI();
    }
    
    protected void onDestroy() {
        ((android.app.Activity)this).onDestroy();
    }
    
    protected void onNewIntent(android.content.Intent a) {
        ((sg.gumi.bravefrontier.BaseGameActivity)this).onCreate(this.savedInstanceState);
    }
    
    public void onPause() {
        ((sg.gumi.bravefrontier.BaseGameActivity)this).onPause();
        if (sg.gumi.bravefrontier.video.BFVideoView.isInstance()) {
            sg.gumi.bravefrontier.video.BFVideoView.getInstance((org.cocos2dx.lib.Cocos2dxActivity)this).onPause();
        }
        sg.gumi.util.AppSession.onPause(this);
        sg.gumi.util.BFConfig$Platform dummy = sg.gumi.util.BFConfig.PLATFORM;
        sg.gumi.util.BFConfig$Platform dummy0 = sg.gumi.util.BFConfig.PLATFORM_AMAZON;
        sg.gumi.bravefrontier.RVHandler.getInstance();
        sg.gumi.bravefrontier.RVHandler.onPause();
    }
    
    public void onResume() {
        ((sg.gumi.bravefrontier.BaseGameActivity)this).onResume();
        ((sg.gumi.bravefrontier.BaseGameActivity)this).hideSystemUI();
        if (sg.gumi.bravefrontier.video.BFVideoView.isInstance()) {
            sg.gumi.bravefrontier.video.BFVideoView.getInstance((org.cocos2dx.lib.Cocos2dxActivity)this).onResume();
        }
        sg.gumi.util.AppSession.onResume(this, (Object)null);
        sg.gumi.util.BFConfig$Platform dummy = sg.gumi.util.BFConfig.PLATFORM;
        sg.gumi.util.BFConfig$Platform dummy0 = sg.gumi.util.BFConfig.PLATFORM_AMAZON;
        sg.gumi.bravefrontier.RVHandler.getInstance();
        sg.gumi.bravefrontier.RVHandler.onResume();
    }
    
    protected void onSaveInstanceState(android.os.Bundle a) {
    }
    
    public void onShake(float f) {
        sg.gumi.bravefrontier.BraveFrontierJNI.onDeviceShake();
        android.util.Log.v("BraveFrontier", "Shoke");
    }
    
    protected void onStart() {
        ((sg.gumi.bravefrontier.BaseGameActivity)this).onStart();
        if (!org.cocos2dx.lib.Cocos2dxHelper.isNativeLibraryLoaded()) {
            java.util.HashMap a = new java.util.HashMap(1);
            StringBuilder a0 = new StringBuilder();
            a0.append(android.os.Build.MODEL);
            a0.append("-");
            a0.append(android.os.Build$VERSION.RELEASE);
            a.put((Object)"DEVICE_FAIL", (Object)a0.toString());
        }
        if (fiverocksInitialized) {
            com.tapjoy.Tapjoy.startSession();
            com.tapjoy.Tapjoy.onActivityStart((android.app.Activity)this);
        }
    }
    
    protected void onStop() {
        ((sg.gumi.bravefrontier.BaseGameActivity)this).onStop();
        if (fiverocksInitialized) {
            com.tapjoy.Tapjoy.endSession();
            com.tapjoy.Tapjoy.onActivityStop((android.app.Activity)this);
        }
    }
}
