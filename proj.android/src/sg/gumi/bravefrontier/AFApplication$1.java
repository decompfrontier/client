package sg.gumi.bravefrontier;

class AFApplication$1 implements com.appsflyer.AppsFlyerConversionListener {
    final sg.gumi.bravefrontier.AFApplication this$0;
    
    AFApplication$1(sg.gumi.bravefrontier.AFApplication a) {
        this.this$0 = a;
        super();
    }
    
    public void onAppOpenAttribution(java.util.Map a) {
        java.util.Iterator a0 = a.keySet().iterator();
        Object a1 = a;
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
    
    public void onAttributionFailure(String s) {
        StringBuilder a = new StringBuilder();
        a.append("error onAttributionFailure : ");
        a.append(s);
        android.util.Log.d("LOG_TAG", a.toString());
    }
    
    public void onConversionDataFail(String s) {
        StringBuilder a = new StringBuilder();
        a.append("error getting conversion data: ");
        a.append(s);
        android.util.Log.d("LOG_TAG", a.toString());
    }
    
    public void onConversionDataSuccess(java.util.Map a) {
        java.util.Iterator a0 = a.keySet().iterator();
        Object a1 = a;
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
