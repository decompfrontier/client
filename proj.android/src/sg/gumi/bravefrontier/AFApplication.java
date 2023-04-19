package sg.gumi.bravefrontier;

public class AFApplication extends androidx.multidex.MultiDexApplication {
    final public static String APPSFLYER_KEY = "WMa4kPf8ZdvNhcpvdpwAvE";
    public static boolean appsflyerInitialized = false;
    
    static {
    }
    
    public AFApplication() {
    }
    
    public static boolean isAppsflyerSDKInitialised() {
        return appsflyerInitialized;
    }
    
    public void onCreate() {
        ((android.app.Application)this).onCreate();
        try {
            sg.gumi.bravefrontier.AFApplication$1 a = new sg.gumi.bravefrontier.AFApplication$1(this);
            com.appsflyer.AppsFlyerLib.getInstance().init("WMa4kPf8ZdvNhcpvdpwAvE", (com.appsflyer.AppsFlyerConversionListener)(Object)a, (android.content.Context)this);
            com.appsflyer.AppsFlyerLib.getInstance().setCollectIMEI(false);
            com.appsflyer.AppsFlyerLib.getInstance().setCurrencyCode("USD");
            appsflyerInitialized = true;
        } catch(Throwable ignoredException) {
            appsflyerInitialized = false;
        }
        if (appsflyerInitialized && sg.gumi.bravefrontier.BraveFrontier.getActivity() != null) {
            StringBuilder a0 = new StringBuilder();
            a0.append("Custom_Event_Arch_");
            a0.append(sg.gumi.bravefrontier.BraveFrontier.getDeviceArchitecture());
            String s = new String(a0.toString());
            java.util.HashMap a1 = new java.util.HashMap();
            a1.put((Object)s, (Object)sg.gumi.bravefrontier.BraveFrontier.getDeviceArchitecture());
            com.appsflyer.AppsFlyerLib.getInstance().logEvent(((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity()).getApplicationContext(), s, (java.util.Map)(Object)a1);
        }
    }
}
