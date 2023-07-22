package sg.gumi.bravefrontier;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.*;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import org.cocos2dx.lib.Cocos2dxHelper;
import org.cocos2dx.lib.Cocos2dxActivity;
import org.json.JSONObject;
import sg.gumi.bravefrontier.video.BFVideoView;
import sg.gumi.bravefrontier.webview.BFWebView;
import sg.gumi.util.AppSession;
import sg.gumi.util.BFUncaughtExceptionHandler;

import java.util.*;

import static sg.gumi.util.BFConfig.*;
//import com.tapjoy.TJConnectListener;
//import com.tapjoy.Tapjoy;
//import com.appsflyer.AppsFlyerLib;
//import com.google.firebase.crashlytics.FirebaseCrashlytics;
//import com.soomla.store.StoreController;
//import com.google.ads.conversiontracking.AdWordsConversionReporter;

public class BraveFrontier extends BaseGameActivity {
    final public static String FIVEROCKS_APP_KEY = "UqfUMS53gACQAACCHQAABgEC4ueo1H0hmTfztQ3Byo7QZsTHevDXgt9Tjf0y";
    final public static String FLURRY_ID = "989N9DD6NYP8GZTJ53K2";
    private static BraveFrontier act;
    private static Context context;
    private static String deviceAdvertisingID = "";
    private static Facebook facebook;
    public static boolean fiverocksInitialized = false;
    public static boolean isInitialized = false;
    private static PhoneStateListener phoneStateListener;
    private float m_LastStoredEffectsVolume;
    private float m_LastStoredMusicVolume;
    private boolean m_isFromStateIdle;
    public Bundle savedInstanceState;


    static class BFPhoneStateListener extends PhoneStateListener {
        final BraveFrontier app;

        class ResultTask extends TimerTask {
            final BFPhoneStateListener task;

            ResultTask(BFPhoneStateListener task) {
                super();
                this.task = task;

            }

            public void run() {
                Cocos2dxHelper.setBackgroundMusicVolume(BraveFrontier.getLastStoredMusicVolume(task.app));
                Cocos2dxHelper.setEffectsVolume(BraveFrontier.getLastStoredEffectsVolume(task.app));
            }
        }


        BFPhoneStateListener(BraveFrontier app) {
            super();
            this.app = app;
        }

        public void onCallStateChanged(int state, String phoneNumber) {
            if (state != 0) {
                if (state != 1) {
                    BraveFrontier.setLastStoredMusicVolume(app, Cocos2dxHelper.getBackgroundMusicVolume());
                    BraveFrontier.setLastStoredEffectsVolume(app, Cocos2dxHelper.getEffectsVolume());
                    Cocos2dxHelper.setBackgroundMusicVolume(0.0f);
                    Cocos2dxHelper.setEffectsVolume(0.0f);
                    BraveFrontier.setFromStateIdle(app, false);
                } else {
                    BraveFrontier.setFromStateIdle(app, false);
                }
            } else {
                if (!BraveFrontier.isFromStateIdle(this.app)) {
                    new Timer().schedule(new ResultTask(this), 300L);
                }
                BraveFrontier.setFromStateIdle(this.app, true);
            }
            super.onCallStateChanged(state, phoneNumber);
        }
    }

    static class SignInTask implements Runnable {

        public void run() {
            BraveFrontier.instance().beginUserInitiatedSignIn();
        }
    }

    static class SetDeviceAdIdTask implements Runnable {
        final BraveFrontier app;

        SetDeviceAdIdTask(BraveFrontier app) {
            super();
            this.app = app;
        }

        public void run() {
            String androidId = Settings.Secure.getString(app.getContentResolver(), "android_id");
            try {
                BraveFrontier.setDeviceAdvertisementId(AdvertisingIdClient.getAdvertisingIdInfo((app).getApplicationContext()).getId());
            } catch(Exception ignoredException) {
                BraveFrontier.setDeviceAdvertisementId(androidId);
            }
        }
    }


    static class TapJoyConnectListener /*implements TJConnectListener*/ {
        final BraveFrontier app;

        TapJoyConnectListener(BraveFrontier app) {
            super();
            this.app = app;
        }

        public void onConnectFailure() {
            BraveFrontier.fiverocksInitialized = false;
            Log.d("TAPJOY_DEBUG", "onConnectFailure");
        }

        public void onConnectSuccess() {
            Log.d("TAPJOY_DEBUG", "onConnectSuccess");
            BraveFrontier.fiverocksInitialized = true;
        }
    }

    static class InitStoreAssets implements Runnable {

        public void run() {
            /*try {
                StoreController.getInstance().initialize(new BraveFrontierStoreAssets(), BraveFrontier.instance(), new Handler());
            } catch(Exception ignoredException) {
            }*/
        }
    }

    static class ClickOpenSettings implements DialogInterface.OnClickListener {
        final BraveFrontier app;

        ClickOpenSettings(BraveFrontier activity) {
            super();
            this.app = activity;
        }

        public void onClick(DialogInterface dialog, int result) {
            if (result == -2) {
                try {
                    this.app.startActivity(new Intent("android.settings.APPLICATION_SETTINGS"));
                } catch(Throwable ignoredException) {
                }
            }
            BraveFrontierJNI.appExit();
        }
    }

    static {
        /*try {
            Tapjoy.loadSharedLibrary();
        } catch(Throwable ignoredException) {
        }*/
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
        if (act.getGameService().getSignInCancellations() <= 0) {
            act.runOnUiThread(new SignInTask());
        }
    }
    
    public static void GPGSSignOut() {
        act.signOut();
    }
    
    static void setDeviceAdvertisementId(String deviceAdvertisingId) {
        deviceAdvertisingID = deviceAdvertisingId;
    }
    
    static boolean isFromStateIdle(BraveFrontier activity) {
        return activity.m_isFromStateIdle;
    }
    
    static void setFromStateIdle(BraveFrontier activity, boolean fromstateIdle) {
        activity.m_isFromStateIdle = fromstateIdle;
    }
    
    static float getLastStoredMusicVolume(BraveFrontier activity) {
        return activity.m_LastStoredMusicVolume;
    }
    
    static void setLastStoredMusicVolume(BraveFrontier activity, float lastStoredMusicVolume) {
        activity.m_LastStoredMusicVolume = lastStoredMusicVolume;
    }
    
    static float getLastStoredEffectsVolume(BraveFrontier activity) {
        return activity.m_LastStoredEffectsVolume;
    }
    
    static void setLastStoredEffectsVolume(BraveFrontier activity, float lastStoredEffectsVolume) {
        activity.m_LastStoredEffectsVolume = lastStoredEffectsVolume;
    }
    
    static BraveFrontier instance() {
        return act;
    }
    
    public static void appsflyerStartTracking() {
        //AppsFlyerLib.getInstance().start(act);
    }
    
    private void checkLoadedLibraries() {
        if (!Cocos2dxHelper.isNativeLibraryLoaded()) {
            ClickOpenSettings btn = new ClickOpenSettings(this);
            new AlertDialog.Builder(this).setTitle("Install Error").setMessage("Install failed because there isn't enough free space on device internal (non-SD Card) memory. Free up some space and reinstall again.").setPositiveButton("Exit", btn).setNegativeButton("Free Space", btn).create().show();
        }
    }
    
    public static void crashlyticsCustomLog(String msg) {
        //FirebaseCrashlytics.getInstance().log(msg);
    }
    
    public static void crashlyticsForceCrash() {
        throw new RuntimeException("Testing crashlytics force crashing...");
    }
    
    public static void crashlyticsSetUserIdentifier(String userId) {
        //FirebaseCrashlytics.getInstance().setUserId(userId);
    }
    
    public static void crashlyticsSetUserName(String userName) {
        //FirebaseCrashlytics.getInstance().setCustomKey("UserName", userName);
    }
    
    public static BraveFrontier getActivity() {
        return act;
    }
    
    public static String getAmazonUserId() {
        return "";
    }
    
    public static String getAndroidId() {
        return Settings.Secure.getString(act.getContentResolver(), "android_id");
    }
    
    public static android.content.Context getAppContext() {
        return context;
    }
    
    public static String getAppName() {
        try {
            return act.getResources().getString(R.string.app_name);
        } catch(Resources.NotFoundException ex) {
            ex.printStackTrace();
            return "Brave Frontier";
        }
    }
    
    public static String getBuildNo() {
        try {
            return act.getResources().getString(R.string.build);
        } catch(Resources.NotFoundException ex) {
            ex.printStackTrace();
            return "";
        }
    }
    
    public static String getBundleName() {
        return act.getPackageName();
    }
    
    public static String getBundleVersion() {
        try {
            return act.getPackageManager().getPackageInfo(act.getPackageName(), 0).versionName;
        } catch(Resources.NotFoundException | PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
            return "";
        }
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
        String devUuid = null;
        TelephonyManager telephonyManager = (TelephonyManager)(act.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE));
        if (Build.VERSION.SDK_INT < 26) {
            devUuid = telephonyManager.getDeviceId();
        } else {
            String imei = telephonyManager.getImei();
            devUuid = telephonyManager.getMeid();
            if (!imei.isEmpty()) {
                devUuid = imei;
            }
        }
        String simSerialNumber = telephonyManager.getSimSerialNumber();
        StringBuilder uuid = new StringBuilder();
        uuid.append("");
        uuid.append(Settings.Secure.getString(act.getContentResolver(), "android_id"));
        return new UUID(uuid.toString().hashCode(), (long)devUuid.hashCode() << 32 | (long)simSerialNumber.hashCode()).toString();
    }
    
    public static long getExternalStorageSpace() {
        long availableBlocks;
        long blockSize;
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        if (Build.VERSION.SDK_INT < 18) {
            availableBlocks = statFs.getAvailableBlocks();
            blockSize = statFs.getBlockSize();
        } else {
            availableBlocks = statFs.getAvailableBlocksLong();
            blockSize = statFs.getBlockSizeLong();
        }
        return availableBlocks * blockSize;
    }
    
    public static String getLegacyDeviceUUID() {
        try {
            SharedPreferences preferences = BraveFrontier.getAppContext().getSharedPreferences("DEVUUID", 0);
            String devuuid = preferences.getString("DEVUUID", "");
            if (devuuid.equals("")) {
                devuuid = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("DEVUUID", devuuid);
                editor.commit();
            }
            return devuuid.toString();
        } catch(Exception ex) {
            ex.printStackTrace();

        }
        return " ";
    }
    
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }
    
    public static String[] getPermissions() {
        String[] permissions = new String[4];
        permissions[0] = "android.permission.READ_PHONE_STATE";
        permissions[1] = "android.permission.INTERNET";
        permissions[2] = "android.permission.ACCESS_WIFI_STATE";
        permissions[3] = "android.permission.ACCESS_NETWORK_STATE";
        return permissions;
    }
    
    public static void goToAppSettings() {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", act.getPackageName(), null));
        BraveFrontier.getActivity().startActivity(intent);
    }
    
    public static void googleAnalyticsSendScreenView(String screenName) {
        act.getGameService().googleAnalyticsSendScreenView(screenName);
    }
    
    public static void googleAnalyticsSetUserID(String userId) {
        act.getGameService().googleAnalyticsSetUserID(userId);
    }
    
    public static void googleAnalyticsTrackEvent(String category, String action, String label, long value) {
        act.getGameService().googleAnalyticsTrackEvent(category, action, label, value);
    }
    
    public static void googleAnalyticsTrackPurchase(String transactionId, String name, String id, String category, double price, long quantity, String cu) {
        act.getGameService().googleAnalyticsTrackPurchase(transactionId, name, id, category, price, quantity, cu);
    }
    
    public static boolean hasPermissions() {
        String[] permissions = BraveFrontier.getPermissions();
        if (Build.VERSION.SDK_INT >= 23 && context != null && permissions != null) {
            for(int i = 0; i < permissions.length; i = i + 1) {
                String s = permissions[i];
                if (ContextCompat.checkSelfPermission(Cocos2dxActivity.getContext(), s) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void initializeStore() {
        if (PLATFORM == PLATFORM_AMAZON || PLATFORM == PLATFORM_GOOGLE) {
            act.runOnUiThread(new InitStoreAssets());
        }
    }

    
    public static boolean isBundleExist() {
        try {
            act.getPackageManager().getPackageInfo(act.getPackageName(), 0);
            return true;
        } catch(PackageManager.NameNotFoundException ignoredException) {
        }
        return false;
    }
    
    public static boolean isFiverocksInitialized() {
        return fiverocksInitialized;
    }
    
    public static boolean isLoadingComplete() {
        if (!Debug.isDebuggerConnected()) {
            return true;
        }
        return false;
    }
    
    public static boolean isSignedInToGPGS() {
        return act.isSignedIn();
    }
    
    native public static void onGPGSSignInFailed();
    
    
    native public static void onGPGSSignInSucceeded();
    
    
    native public static void onGPGSSignOutSucceeded();
    
    
    public static void openURL(String url) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(url));
        act.startActivity(intent);
    }
    
    public static void requestPermissions() {
        if (!BraveFrontier.hasPermissions()) {
            ActivityCompat.requestPermissions(BraveFrontier.getActivity(), BraveFrontier.getPermissions(), 1);
        }
    }
    
    public static void resetGPGSAchievements() {
    }
    
    public static void setAppsFlyeruserId(String userId) {
        try {
            if (AFApplication.isAppsflyerSDKInitialised()) {
                //AppsFlyerLib.getInstance().setCustomerUserId(userId);
            }
        } catch(Throwable ignoredException) {
        }
    }
    
    public static void setRemoteNotificationsEnable(boolean enable) {
        NotificationService.getInstance().setRemoteNotificationsEnable(act, enable);
    }
    
    public static boolean shouldShowRequestPermissionRationale() {
        String[] permissions = BraveFrontier.getPermissions();

        for (int i = 0; i < permissions.length; i++) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(BraveFrontier.getActivity(), permissions[i])) {
                continue;
            }

            return true;
        }

        return false;
    }
    
    public static void showAchievements() {
        GameService gs = act.getGameService();
        if (gs != null) {
            if (gs.isSignedIn()) {
                gs.showAchievements();
            } else {
                gs.beginUserInitiatedSignIn();
            }
        }
    }
    
    public static void trackEvent2(byte[] keyBytes, byte[] unk) {
        String key = (keyBytes == null) ? null : new String(keyBytes);

        if (act != null) {
            try {
                if (!(act.isFinishing() && key != null && AFApplication.isAppsflyerSDKInitialised())) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put(key, "0");
                    //AppsFlyerLib.getInstance().logEvent(act.getApplicationContext(), key, map);
                }
            } catch(Throwable ignoredException) {
            }
        }
    }
    
    public static void trackPurchase(float price, String countryValueName) {
        if (act != null) {
            try {
                if (!act.isFinishing()) {

                    /*if (countryValueName == null) {
                        AdWordsConversionReporter.reportWithConversionId(act.getApplicationContext(), "963467556", "NxvHCJXllFYQpLK1ywM", String.valueOf(price), true);
                    }
                    if (countryValueName.isEmpty()) {
                        AdWordsConversionReporter.reportWithConversionId(act.getApplicationContext(), "963467556", "NxvHCJXllFYQpLK1ywM", String.valueOf(price), true);
                    }
                    if (countryValueName.equalsIgnoreCase("USD")) {
                        AdWordsConversionReporter.reportWithConversionId(act.getApplicationContext(), "963467556", "NxvHCJXllFYQpLK1ywM", String.valueOf(price), true);
                    }*/

                    if (AFApplication.isAppsflyerSDKInitialised()) {
                        HashMap<String, Float> map = new HashMap<>();
                        map.put("af_revenue", price);
                        //AppsFlyerLib.getInstance().logEvent(act.getApplicationContext(), "af_purchase", map);
                    }
                }
            } catch(Throwable ignoredException) {
            }
        }
    }
    
    public static void unlockGPGSAchievement(String id) {
        act.getGameService().unlockAchievement(id);
    }
    
    public static void updateGPGSLeaderboard(int value, String name) {
        act.getGameService().updateLeaderboard(value, name);
    }
    
    public void onAccelerationChanged(float f, float f0, float f1) {
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.i("onActivityResult", "Recieved activity result");


        if (resultCode == 0) {
            try {
                onActivityResult(requestCode, resultCode, intent);
                Facebook.callbackmanagerOnActivityResult(requestCode, resultCode, intent);
            } catch(Throwable ignoredException) {
            }
            return;
        }

        if (intent != null) {
            if (requestCode != 1001) {
                onActivityResult(requestCode, resultCode, intent);
                Facebook.callbackmanagerOnActivityResult(requestCode, resultCode, intent);
                return;
            }

            if (PLATFORM != PLATFORM_AMAZON) {
                if (resultCode != -1) {
                    if (PLATFORM == PLATFORM_GOOGLE) {
                        //StoreController.getInstance().onPurchaseStateChange("", "", "");
                    }
                    int responseCode = intent.getIntExtra("RESPONSE_CODE", 0);
                    Log.e("onActivityResult", "onActivityResult payment attempt fail! Response code: " +
                            responseCode);
                    return;
                }
                int responseCode = intent.getIntExtra("RESPONSE_CODE", 0);
                if (PLATFORM == PLATFORM_GOOGLE && responseCode > 0) {
                    //StoreController.getInstance().onPurchaseStateChange("", "", "");
                    return;
                }
                String iapData = intent.getStringExtra("INAPP_PURCHASE_DATA");
                String iapSig = intent.getStringExtra("INAPP_DATA_SIGNATURE");
                String purchase = null;
                try {
                    purchase = new JSONObject(iapData).getString("productId");
                } catch(Throwable ex) {
                    ex.printStackTrace();
                }
                if (PLATFORM == PLATFORM_GOOGLE) {
                    //StoreController.getInstance().onPurchaseStateChange(iapData, iapSig, purchase);
                }
            }

            onActivityResult(requestCode, resultCode, intent);
            Facebook.callbackmanagerOnActivityResult(requestCode, resultCode, intent);
        }
    }
    
    public void onBackPressed() {
        if (Cocos2dxHelper.isNativeLibraryLoaded()) {
            BraveFrontierJNI.backButtonCallback();
        }
    }
    
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        checkLoadedLibraries();
        context = getApplicationContext();
        act = this;
        savedInstanceState = bundle;

        if (OFFLINE_MODE) { // __DECOMP__
            it.arves100.gimuserver.OfflineMod.startOfflineServer();
        }

        if (PLATFORM == PLATFORM_GOOGLE) {
            try {
                //AdWordsConversionReporter.reportWithConversionId(getApplicationContext(), "963467556", "n0JXCNWzn1gQpLK1ywM", "0.00", true);
            } catch(Throwable ignoredException) {
            }
        }
        new Thread(new SetDeviceAdIdTask(this)).start();
        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof BFUncaughtExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new BFUncaughtExceptionHandler());
        }
        if (AFApplication.isAppsflyerSDKInitialised() && act != null) {
            String key = "Custom_Event_Arch_" +
                    BraveFrontier.getDeviceArchitecture();

            HashMap<String, String> map = new HashMap<>();
            map.put(key, BraveFrontier.getDeviceArchitecture());
            //AppsFlyerLib.getInstance().logEvent(act.getApplicationContext(), key, map);
        }

        if (fiverocksInitialized) {
            android.util.Log.d("TAPJOY_DEBUG", "tapjoy is already initliaized");
        } else {
            try {
                Hashtable<String, String> tbl = new Hashtable<>();
                tbl.put("TJC_OPTION_ENABLE_LOGGING", "false");
                /*Tapjoy.connect(this, FIVEROCKS_APP_KEY, tbl, new TapJoyConnectListener(this));
                Tapjoy.setAppDataVersion(BraveFrontier.getBundleVersion());
                Tapjoy.setGcmSender(NotificationService.GCM_SENDER_ID);
                Tapjoy.setGLSurfaceView(this.getGLView());*/
            } catch(Throwable ignoredException0) {
                fiverocksInitialized = false;
            }
        }

        if (PLATFORM == PLATFORM_GOOGLE) {
            try {
                getGameService().initGoogleAnalytics(this);
            } catch(Throwable ignoredException1) {
            }
        }
        setVolumeControlStream(3);
        Log.e("Brave Frontier", "Creating app");
        if (isInitialized) {
            BFVideoView.clearInstance();
        }
        NotificationService.getInstance().onCreate(this);
        BraveFrontierJNI.initialize(this);
        BFWebView.initialize(this);
        BFVideoView.getInstance(this);

        getWindow().setSoftInputMode(3);
        facebook = new Facebook(act);
        isInitialized = true;
        phoneStateListener = new BFPhoneStateListener(this);
        TelephonyManager telephonyManager = (TelephonyManager)act.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            telephonyManager.listen(phoneStateListener, 32);
        }
        RVHandler.getInstance();
        RVHandler.initialiseHandler();
        hideSystemUI();
    }
    
    protected void onDestroy() {
        super.onDestroy();
    }
    
    protected void onNewIntent(Intent intent) {
        onCreate(savedInstanceState);
    }
    
    public void onPause() {
        super.onPause();
        if (BFVideoView.isInstance()) {
            BFVideoView.getInstance(this).onPause();
        }
        AppSession.onPause(this);
        RVHandler.getInstance();
        RVHandler.onPause();
    }
    
    public void onResume() {
        super.onResume();
        hideSystemUI();
        if (BFVideoView.isInstance()) {
            BFVideoView.getInstance(this).onResume();
        }
        AppSession.onResume(this, null);
        RVHandler.getInstance();
        RVHandler.onResume();
    }
    
    protected void onSaveInstanceState(Bundle bundle) {
    }
    
    public void onShake(float value) {
        BraveFrontierJNI.onDeviceShake();
        Log.v("BraveFrontier", "Shoke");
    }
    
    protected void onStart() {
        super.onStart();
        /*if (!org.cocos2dx.lib.Cocos2dxHelper.isNativeLibraryLoaded()) {
            HashMap<String, String> a = new HashMap<>(1);
            a.put("DEVICE_FAIL", Build.MODEL +
                    "-" +
                    Build.VERSION.RELEASE);
        }
        if (fiverocksInitialized) {
            Tapjoy.startSession();
            Tapjoy.onActivityStart(this);
        }*/
    }
    
    protected void onStop() {
        super.onStop();
        /*if (fiverocksInitialized) {
            Tapjoy.endSession();
            Tapjoy.onActivityStop(this);
        }*/
    }
}
