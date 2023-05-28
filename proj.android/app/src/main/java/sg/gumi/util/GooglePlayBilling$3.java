package sg.gumi.util;

class GooglePlayBilling$3 implements com.android.billingclient.api.SkuDetailsResponseListener {
    final sg.gumi.util.GooglePlayBilling this$0;
    
    GooglePlayBilling$3(sg.gumi.util.GooglePlayBilling a) {
        this.this$0 = a;
        super();
    }
    
    public void onSkuDetailsResponse(com.android.billingclient.api.BillingResult a, java.util.List a0) {
        if (a0 != null) {
            Object a1 = a0.iterator();
            while(((java.util.Iterator)a1).hasNext()) {
                com.android.billingclient.api.SkuDetails a2 = (com.android.billingclient.api.SkuDetails)((java.util.Iterator)a1).next();
                String s = a2.getSku();
                String s0 = a2.getPrice();
                Object a3 = com.soomla.store.data.StoreInfo.getCurrencyPacks().iterator();
                while(((java.util.Iterator)a3).hasNext()) {
                    com.soomla.store.domain.data.VirtualCurrencyPack a4 = (com.soomla.store.domain.data.VirtualCurrencyPack)((java.util.Iterator)a3).next();
                    if (a4.getProductId().equals((Object)s)) {
                        a4.setPriceString(s0);
                        a4.setSkuDetails(a2);
                        break;
                    }
                }
            }
        }
    }
}
