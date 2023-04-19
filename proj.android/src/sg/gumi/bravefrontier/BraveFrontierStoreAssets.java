package sg.gumi.bravefrontier;

public class BraveFrontierStoreAssets implements com.soomla.store.IStoreAssets {
    static java.util.ArrayList currencyPacks;
    
    static {
        currencyPacks = new java.util.ArrayList();
    }
    
    public BraveFrontierStoreAssets() {
    }
    
    public static void AddCurrencyPack(String s) {
        ((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity()).runOnUiThread((Runnable)(Object)new sg.gumi.bravefrontier.BraveFrontierStoreAssets$1(s));
    }
    
    public com.soomla.store.domain.data.VirtualCurrencyPack[] getVirtualCurrencyPacks() {
        com.soomla.store.domain.data.VirtualCurrencyPack[] a = new com.soomla.store.domain.data.VirtualCurrencyPack[currencyPacks.size()];
        int i = 0;
        for(; i < currencyPacks.size(); i = i + 1) {
            a[i] = (com.soomla.store.domain.data.VirtualCurrencyPack)currencyPacks.get(i);
        }
        return a;
    }
}
