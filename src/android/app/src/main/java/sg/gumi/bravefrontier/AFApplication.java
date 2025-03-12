package sg.gumi.bravefrontier;

import android.util.Log;
import androidx.multidex.MultiDexApplication;

import java.util.HashMap;
import java.util.Map;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AppsFlyerConversionListener;

public class AFApplication extends MultiDexApplication {

    public static final String APPSFLYER_KEY = "WMa4kPf8ZdvNhcpvdpwAvE";

    class AFListener implements AppsFlyerConversionListener {
        final AFApplication app;

        AFListener(AFApplication app) {
            super();
            this.app = app;
        }

        @Override
        public void onAppOpenAttribution(Map<String, String> map) {
            for (String str : map.keySet()) {
                Log.d("LOG_TAG", "attribute: " + str + " = " + map.get(str));
            }
        }

        @Override
        public void onAttributionFailure(String error) {
            Log.d("LOG_TAG", "error onAttributionFailure : " +
                    error);
        }

        @Override
        public void onConversionDataFail(String error) {
            Log.d("LOG_TAG", "error getting conversion data: " +
                    error);
        }

        @Override
        public void onConversionDataSuccess(Map<String, Object> kv) {
            for (String str : kv.keySet()) {
                Log.d("LOG_TAG", "attribute: " + str + " = " + kv.get(str));
            }

        }
    }

    public static boolean appsflyerInitialized = false;

    public AFApplication() {
    }
    
    public static boolean isAppsflyerSDKInitialised() {
        return appsflyerInitialized;
    }
    
    public void onCreate() {
        super.onCreate();

        try {
            AFListener listener = new AFListener(this);
            AppsFlyerLib.getInstance().init(APPSFLYER_KEY, listener, this);
            AppsFlyerLib.getInstance().setCollectIMEI(false);
            AppsFlyerLib.getInstance().setCurrencyCode("USD");
            appsflyerInitialized = true;
        } catch(Throwable ignoredException) {
            appsflyerInitialized = false;
        }

        if (appsflyerInitialized && BraveFrontier.getActivity() != null) {
            String key = "Custom_Event_Arch_" +
                    BraveFrontier.getDeviceArchitecture();
            HashMap<String, Object> keys = new HashMap<>();
            keys.put(key, BraveFrontier.getDeviceArchitecture());
            AppsFlyerLib.getInstance().logEvent(BraveFrontier.getActivity().getApplicationContext(), key, keys);
        }
    }
}
