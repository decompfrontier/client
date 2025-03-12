package sg.gumi.bravefrontier;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.*;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import org.cocos2dx.lib.Cocos2dxActivity;

import ch.boye.httpclientandroidlib.protocol.HTTP;
import sg.gumi.bravefrontier.video.BFVideoView;
import sg.gumi.bravefrontier.webview.BFWebView;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static sg.gumi.util.BFConfig.*;
import static android.os.Build.*;

public class BraveFrontierJNI {
    static class disableDimTask implements Runnable {

        @SuppressLint("LongLogTag")
        public void run() {
            Log.d("NONSLEEP_ANDROID BraveFrontierJNI", "disableDim");
        }
    }

    static class enableDimTask implements Runnable {

        @SuppressLint("LongLogTag")
        public void run() {
            Log.d("NONSLEEP_ANDROID BraveFrontierJNI", "enableDim");
        }
    }

    static class PlayVideoWithOptionTask implements Runnable {
        final String fileName;
        final boolean closeWhenTouch;

        PlayVideoWithOptionTask(String fileName, boolean close) {
            super();
            this.fileName = fileName;
            this.closeWhenTouch = close;

        }

        public void run() {
            BFVideoView.getInstance(BraveFrontierJNI.getActivity()).playVideo(fileName, closeWhenTouch);
        }
    }

    static class ShowRatePopupTask implements Runnable {

        static class BtnClose implements DialogInterface.OnClickListener {
            final ShowRatePopupTask popup;

            BtnClose(ShowRatePopupTask popup) {
                super();
                this.popup = popup;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                sg.gumi.bravefrontier.BraveFrontierJNI.nativeRateThisAppPopupCallback(1);
            }
        }



        static class BtnOk implements DialogInterface.OnClickListener {
            final ShowRatePopupTask popup;
            final android.content.Context context;

            BtnOk(ShowRatePopupTask popupTask, Context context) {
                super();
                this.popup = popupTask;
                this.context = context;
            }

            public void onClick(android.content.DialogInterface dialogInterface, int i) {
                String reviewUrl = null;
                if (PLATFORM != PLATFORM_AMAZON) {
                    if (PLATFORM != PLATFORM_SAMSUNG) {
                        reviewUrl = "market://details?id=" +
                                context.getPackageName();
                    } else {
                        reviewUrl = "samsungapps://ProductDetail/" +
                                context.getPackageName();
                    }
                } else {
                    reviewUrl = "amzn://apps/android?p=" +
                            context.getPackageName();
                }
                Log.v("MARKET-URL", reviewUrl);
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(reviewUrl));
                BraveFrontierJNI.getActivity().startActivity(intent);
                BraveFrontierJNI.nativeRateThisAppPopupCallback(0);
            }
        }

        final String body;
        final String btn1Text;
        final String btn2Text;
        final String title;

        ShowRatePopupTask(String body, String title, String btn1Text, String btn2Text) {
            super();
            this.body = body;
            this.title = title;
            this.btn1Text = btn1Text;
            this.btn2Text = btn2Text;
        }

        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(BraveFrontierJNI.getActivity());
            TextView textView = new TextView(BraveFrontierJNI.getActivity().getApplicationContext());
            textView.setPadding(10, 10, 10, 10);
            textView.setGravity(17);
            textView.setTextColor(-1);
            textView.setTextSize(20f);
            builder.setCustomTitle(textView);
            if (PLATFORM != PLATFORM_AMAZON) {
                builder.setMessage(body);
            } else {
                builder.setMessage(BraveFrontierJNI.replaceGooglePlayStoreName(body, "Amazon Appstore", "Amazon"));
            }
            textView.setText(title);
            builder.setPositiveButton(btn1Text, new BtnOk(this, BraveFrontierJNI.getActivity().getApplicationContext()));
            builder.setNegativeButton(btn2Text, new BtnClose(this));
            AlertDialog dialog = builder.create();
            dialog.show();
            try {
                ((TextView)dialog.findViewById(R.id.message)).setGravity(17);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    static class PlayVideoTask implements Runnable {
        final String fileName;

        PlayVideoTask(String fileName) {
            super();
            this.fileName = fileName;

        }

        public void run() {
            BFVideoView.getInstance(BraveFrontierJNI.getActivity()).playVideo(fileName, false);
        }
    }


    static class unpackNoDlcTask implements Runnable {

        public void run() {
            String basePath = BraveFrontierJNI.getWritablePath();
            InputStream dlcResZip = null;

            try {
                dlcResZip = BraveFrontierJNI.getActivity().getResources().getAssets().open("NoDLCRes.zip");
                ZipInputStream zipfile = new ZipInputStream(dlcResZip);
                while (true) { // unpack all the zip file
                    ZipEntry entry = zipfile.getNextEntry();
                    if (entry == null) {
                        break;
                    }
                    if (!entry.isDirectory()) {
                        String writePath = basePath +
                                "/" +
                                entry.getName().replace("NoDLCRes", "");
                        FileOutputStream writeFile = new FileOutputStream(writePath);
                        byte[] dataBuffer = new byte[4096];
                        while (true) {
                            int i = zipfile.read(dataBuffer);
                            if (i <= 0) {
                                zipfile.closeEntry();
                                writeFile.close();
                                break;
                            } else {
                                writeFile.write(dataBuffer, 0, i);
                            }
                        }
                    }
                }
                zipfile.close();
                BraveFrontierJNI.s_UnzipStatus = 0;
            } catch (Throwable ignoredException) {
                if (BraveFrontierJNI.s_UnzipStatus != 0) {
                    BraveFrontierJNI.s_UnzipStatus = 2;
                }
                if (dlcResZip == null) {
                    return;
                }
                try {
                    dlcResZip.close();
                } catch (Throwable ignoredException0) {
                }
                return;
            }
            if (dlcResZip == null) {
                return;
            }
            try {
                dlcResZip.close();
            } catch (Throwable ignoredException0) {
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

    static class PlayYoutubeVideoTask implements Runnable {
        final String videoUrl;
        final android.content.Context context;

        PlayYoutubeVideoTask(String id, Context context) {
            super();
            this.videoUrl = id;
            this.context = context;
        }

        public void run() {
            YoutubeActivity.VIDEO_ID = this.videoUrl;
            Intent intent = new Intent(context, YoutubeActivity.class);
            BraveFrontierJNI.getActivity().startActivity(intent);
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

    /* 0 = unzipped, 1 = zipped, 2 = error */
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
        alarm.cancel(PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_ONE_SHOT));
        intent.setAction("ArenaNotif");
        alarm.cancel(PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_ONE_SHOT));
        intent.setAction("NoLoginNotif");
        alarm.cancel(PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_ONE_SHOT));
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
        Context context = ((android.opengl.GLSurfaceView) mActivity.getGLView()).getContext();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, seconds);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("notification", text);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        ((android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    public static String decodeCStringForBase64(String data, String key) {
        try {
            byte[] aesKey = new byte[16];
            System.arraycopy(key.getBytes(HTTP.UTF_8), 0, aesKey, 0, Math.min(16, key.getBytes(HTTP.UTF_8).length));
            SecretKeySpec secKey = new SecretKeySpec(aesKey, "AES");
            Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
            aes.init(2, secKey);
            return new String(aes.doFinal(sg.gumi.bravefrontier.Base64.decode(data)));
        } catch (Throwable ignoredException) {
            return "";
        }

    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            for (String dd : dir.list()) {
                if (!BraveFrontierJNI.deleteDir(new File(dir, dd))) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static boolean deleteLocalFiles() {
        for (String file : mActivity.fileList()) {
            mActivity.deleteFile(file);
        }
        return true;
    }

    public static String dictionaryWordBreak(String wordDict) {
        if (wordDict != null && wordDict.length() != 0) {
            if (regexArray == null) {
                try {
                    Pattern[] patterns = new Pattern[5];
                    patterns[0] = Pattern.compile(THAI_REGEX_0);
                    patterns[1] = Pattern.compile(THAI_REGEX_1);
                    patterns[2] = Pattern.compile(THAI_REGEX_2);
                    patterns[3] = Pattern.compile(THAI_REGEX_3);
                    patterns[4] = Pattern.compile(THAI_REGEX_4);
                    regexArray = patterns;
                } catch (Throwable ignoredException) {
                    regexArray = null;
                    return wordDict;
                }
            }

            Matcher[] matcher = new Matcher[regexArray.length];
            for (int i = 0; i < regexArray.length; i++) {
                matcher[i] = regexArray[i].matcher(wordDict);
            }

            ArrayList<Integer> matches = new ArrayList<>();
            int end = wordDict.length();
            int start = 0;
            int regexStart = 0;
            while (start < end) {
                start = end;
                for (int k = 0; k < regexArray.length; k++) {
                    if (matcher[k].reset().find(regexStart)) {
                        start = Math.min(start, matcher[k].start() + 1);
                    }
                }

                if (start >= end) {
                    continue;
                }
                matches.add(start);
                regexStart = start;
            }

            if (matches.size() > 0) {
                StringBuilder newStr = new StringBuilder(wordDict);
                for (int i = matches.size() - 1; i >= 0; i--) {
                    newStr.insert(matches.get(i), "<wb>");
                }
                wordDict = newStr.toString();
            }
        }

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
            System.arraycopy(key.getBytes(HTTP.UTF_8), 0, aesKey, 0, Math.min(16, key.getBytes(HTTP.UTF_8).length));
            SecretKeySpec secKey = new SecretKeySpec(aesKey, "AES");
            Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
            aes.init(1, secKey);
            return sg.gumi.bravefrontier.Base64.encodeToString(aes.doFinal(data.getBytes()), false);
        } catch (Exception ex) {
            return "";
        }
    }

    public static boolean existsBundleFile(String fileName) {
        InputStream stream;

        try {
            stream = mActivity.getResources().getAssets().open(fileName);

        } catch (Throwable ignoredException) {
            return false;
        }

        if (stream != null) {
            try {
                stream.close();
            } catch (Throwable ignoredException) {
            }
        }

        return true;
    }

    public static String getAppVersion() {
        try {
            return mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0).versionName;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static int getAvailableMemory() {
        ActivityManager service = (ActivityManager) mActivity.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        service.getMemoryInfo(info);
        return (int) info.availMem;
    }

    public static String getDeepLinkParam() {
        return deepLinkParam;
    }

    public static String getDeviceAdvertisingID() {
        return BraveFrontier.getDeviceAdvertisingID();
    }

    public static String getDeviceID() {
        String devId = null;
        TelephonyManager manager = (TelephonyManager) mActivity.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
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
        TelephonyManager manager = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
        String isoCountry = (manager == null) ? "" : manager.getSimCountryIso();
        if (!isoCountry.isEmpty()) {
            isoCountry = isoCountry.toUpperCase();
        }
        return isoCountry;
    }

    public static void getLeaderBoardScore(String leaderboard) {
        if (mActivity instanceof sg.gumi.bravefrontier.BraveFrontier) {
            ((sg.gumi.bravefrontier.BaseGameActivity) mActivity).getGameService().getLeaderBoardScore(leaderboard);
        }
    }

    public static String getNameFromAccount() {
        return mActivity.getSharedPreferences(PREFS_NAME_AUTH, 0).getString("name", "");
    }

    public static int getNetworkState() {
        if (!((ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(1).isConnected()) {
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

    public static int getSecondsFromDate(String dateStr) {
        Date selectedDate;
        String dateFormatted = Calendar.getInstance().get(1) +
                " " +
                dateStr;
        Date currentDate = java.util.Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMM dd, HH:mm  z", Locale.getDefault());
        try {
            selectedDate = dateFormat.parse(dateFormatted);
        } catch (Throwable ex) {
            ex.printStackTrace();
            selectedDate = null;
        }
        return (int) (selectedDate.getTime() - currentDate.getTime()) / 1000;
    }

    public static String getSharedPrefString(String key) {
        return mActivity.getSharedPreferences(PREFS_NAME_GAMEDATA, 0).getString(key, "");
    }

    public static int getUnzipStatus() {
        return s_UnzipStatus;
    }

    public static String getWritablePath() {
        String s = null;

        try {
            return mActivity.getBaseContext().getFilesDir().getAbsolutePath();
        } catch (Throwable ignoredException) {
            try {
                return BraveFrontier.getAppContext().getFilesDir().getAbsolutePath();
            } catch (Throwable ignoredException0) {
            }
        }
        return null;
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
        } catch (Throwable ignoredException) {
        }
    }

    public static boolean isCanSimultaneousDownload() {
        if (VERSION.SDK_INT < 11) {
            return false;
        }

        int networkSubType = 0;

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) mActivity.getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            TelephonyManager manager = (TelephonyManager) mActivity.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (VERSION.SDK_INT >= 23) {
                if (connectivityManager == null) {
                    return true;
                }
                NetworkCapabilities netCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (netCapabilities == null) {
                    return true;
                }

                if (!netCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    if (!netCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    }
                    networkSubType = networkInfo.getSubtype();
                    if (networkSubType == 1) {
                        return false;
                    }
                    if (networkSubType == 2) {
                        return false;
                    }
                    return true;
                }

                if (networkInfo.isConnected()) {
                    return true;
                }
                return true;
            }
            int infoType = networkInfo.getType();
            if (infoType == 0) {
                if (ActivityCompat.checkSelfPermission(BraveFrontierJNI.getActivity(), android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }

                int networkType = manager.getNetworkType();
                if (networkType == TelephonyManager.NETWORK_TYPE_GPRS) {
                    return false;
                } else if (networkType == TelephonyManager.NETWORK_TYPE_EDGE) {
                    return false;
                }
                return true;
            }
            if (infoType == 1) {
                if (connectivityManager.getNetworkInfo(1).isConnected()) {
                    return true;
                }
                if (ActivityCompat.checkSelfPermission(BraveFrontierJNI.getActivity(), android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }

                int networkType = manager.getNetworkType();
                if (networkType == TelephonyManager.NETWORK_TYPE_GPRS) {
                    return false;
                }
                else if (networkType == TelephonyManager.NETWORK_TYPE_EDGE) {
                    return false;
                }

                return true;
            }
            return true;
        } catch(Throwable ignoredException) {
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
        BFWebView.launchNewBrowser(url);
    }
    
    public static void leaveBreadcrumb(String unk) {
    }
    
    public static void leaveHandledException(String exception) {
    }
    
    native public static void nativeDownloadCallback(long obj, byte[] data, String error);
    

    // status 0: rated, status 1: not rated
    native public static void nativeRateThisAppPopupCallback(int status);
    
    
    native public static void nativeSetDeviceRegistrationId(String deviceId);
    
    
    native public static void onDeviceShake();
    
    
    public static void outputLogcat(String log) {
        Log.d("barave", log);
    }
    
    native public static void playPhonePurchaseFailCallBack();
    
    
    native public static void playPhonePurchaseSuccessCallBack(String arg, String arg0, String arg1);
    
    
    public static void playVideo(String fileName) {
        mActivity.runOnUiThread(new PlayVideoTask(fileName));
    }
    
    public static void playVideoWithOption(String fileName, boolean closeWhenTouched) {
        mActivity.runOnUiThread(new PlayVideoWithOptionTask(fileName, closeWhenTouched));
    }
    
    public static void playYoutubeVideo(String url) {
        if (PLATFORM != PLATFORM_AMAZON) {
            mActivity.runOnUiThread(new PlayYoutubeVideoTask(url, mActivity.getApplicationContext()));
        } else {
            BFWebView.playYoutubeVideo(url);
        }
    }
    
    native public static void purchaseStateChangedAdmobCallback(String arg, String arg0, String arg1, int arg2);
    
    
    native public static void purchaseStateChangedCYPayCallback(String arg, String arg0, String arg1, String arg2, int arg3);
    
    
    native public static void purchaseStateChangedCallback(String iapData, String iapSignature, String purchase);
    
    
    private static String replaceGooglePlayStoreName(String input, String playStoreName, String companyName) {
        return input.replace("Google Play Store", playStoreName).replace("Google Play", companyName).replace("Google", companyName);
    }
    
    public static void saveToAccount(String name, String pass) {
        SharedPreferences.Editor editor = mActivity.getSharedPreferences(PREFS_NAME_AUTH, 0).edit();
        editor.putString("name", name);
        editor.putString("password", pass);
        editor.commit();
    }
    
    public static void sendAdSdkActionResult(String result) {
    }

    /* does not happen in decompilation? */
    native public static void setMultiInvateSchemeData(String arg, String arg0);
    
    
    public static void setSharedPrefString(String key, String value) {
        SharedPreferences.Editor editor = mActivity.getSharedPreferences(PREFS_NAME_GAMEDATA, 0).edit();
        editor.putString(key, value);
        editor.commit();
    }
    
    public static void setWebViewVisible(boolean visible) {
        BFWebView.setWebViewVisible(visible);
    }
    
    public static void showPlayPhoneButton(boolean visible) {
        if (mActivity instanceof BraveFrontier) {
            ((BaseGameActivity)mActivity).getGameService().showPlayPhoneButton(visible);
        }
    }
    
    private static void showRateThisAppPopup(String title, String body, String btn1Text, String btn2Text) {
        mActivity.runOnUiThread(new ShowRatePopupTask(body, title, btn1Text, btn2Text));
    }
    
    public static void showWebView(String s, float f, float f0, float f1, float f2) {
        BFWebView.showWebView(s, f, f0, f1, f2);
    }
    
    public static void submitLeaderBoardScore(String leaderboard, int score) {
        if (mActivity instanceof BraveFrontier) {
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
