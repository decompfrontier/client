package sg.gumi.bravefrontier;

class BraveFrontierStoreAssets$1 implements Runnable {
    final String val$productId;
    
    BraveFrontierStoreAssets$1(String s) {
        this.val$productId = s;
        super();
    }
    
    public void run() {
        try {
            sg.gumi.bravefrontier.BraveFrontierStoreAssets.currencyPacks.add((Object)new com.soomla.store.domain.data.VirtualCurrencyPack("", "", "", this.val$productId, 0.0));
        } catch(Exception a) {
            a.printStackTrace();
        }
    }
}
