package sg.gumi.bravefrontier;

class BraveFrontier$5 implements Runnable {
    BraveFrontier$5() {
    }
    
    public void run() {
        try {
            com.soomla.store.StoreController.getInstance().initialize((com.soomla.store.IStoreAssets)(Object)new sg.gumi.bravefrontier.BraveFrontierStoreAssets(), (android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.access$400(), new android.os.Handler());
        } catch(Exception ignoredException) {
        }
    }
}
