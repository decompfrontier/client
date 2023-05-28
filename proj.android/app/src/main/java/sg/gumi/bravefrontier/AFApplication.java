package sg.gumi.bravefrontier;

import androidx.multidex.MultiDexApplication;

import java.util.Map;

/*import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AppsFlyerConversionListener;*/

public class AFApplication extends MultiDexApplication {

    class AFListener /*implements com.appsflyer.AppsFlyerConversionListener*/ {
        final AFApplication app;

        AFListener(AFApplication app) {
            super();
            this.app = app;
        }

        public void onAppOpenAttribution(Map<String, String> map) {
            java.util.Iterator a0 = map.keySet().iterator();
            Object a1 = map;
            Object a2 = a0;

            while(((java.util.Iterator)a2).hasNext()) {
                String s = (String)((java.util.Iterator)a2).next();
                StringBuilder a3 = new StringBuilder();
                a3.append("attribute: ");
                a3.append(s);
                a3.append(" = ");
                a3.append((String)((java.util.Map)a1).get((Object)s));
                android.util.Log.d("LOG_TAG", a3.toString());
            }
        }

        public void onAttributionFailure(String error) {
            android.util.Log.d("LOG_TAG", "error onAttributionFailure : " +
                    error);
        }

        public void onConversionDataFail(String error) {
            android.util.Log.d("LOG_TAG", "error getting conversion data: " +
                    error);
        }

        public void onConversionDataSuccess(Map<String, String> map) {
            java.util.Iterator a0 = map.keySet().iterator();
            Object a1 = map;
            Object a2 = a0;
            while(((java.util.Iterator)a2).hasNext()) {
                String s = (String)((java.util.Iterator)a2).next();
                StringBuilder a3 = new StringBuilder();
                a3.append("attribute: ");
                a3.append(s);
                a3.append(" = ");
                a3.append(((java.util.Map)a1).get((Object)s));
                android.util.Log.d("LOG_TAG", a3.toString());
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

        /*try {
            AFListener listener = new AFListener(this);
            AppsFlyerLib.getInstance().init("WMa4kPf8ZdvNhcpvdpwAvE", listener, this);
            AppsFlyerLib.getInstance().setCollectIMEI(false);
            AppsFlyerLib.getInstance().setCurrencyCode("USD");
            appsflyerInitialized = true;
        } catch(Throwable ignoredException) {
            appsflyerInitialized = false;
        }*/

        if (appsflyerInitialized && sg.gumi.bravefrontier.BraveFrontier.getActivity() != null) {
            String a0 = "Custom_Event_Arch_" +
                    BraveFrontier.getDeviceArchitecture();
            String s = new String(a0);
            java.util.HashMap a1 = new java.util.HashMap();
            a1.put((Object)s, (Object)sg.gumi.bravefrontier.BraveFrontier.getDeviceArchitecture());
            AppsFlyerLib.getInstance().logEvent(((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity()).getApplicationContext(), s, (java.util.Map)(Object)a1);
        }
    }
}
