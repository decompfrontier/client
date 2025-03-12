package sg.gumi.bravefrontier;

import com.soomla.store.IStoreAssets;
import com.soomla.store.domain.data.VirtualCurrencyPack;
import java.util.ArrayList;

public class BraveFrontierStoreAssets implements IStoreAssets {

    static class AddStoreAssetTask implements Runnable {
        final String productId;

        AddStoreAssetTask(String productId) {
            super();
            this.productId = productId;
        }

        public void run() {
            try {
                BraveFrontierStoreAssets.currencyPacks.add(new VirtualCurrencyPack("", "", "", productId, 0.0));
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    static ArrayList<VirtualCurrencyPack> currencyPacks = new ArrayList<VirtualCurrencyPack>();

    public BraveFrontierStoreAssets() {
    }
    
    public static void AddCurrencyPack(String productId) {
        BraveFrontier.getActivity().runOnUiThread(new AddStoreAssetTask(productId));
    }
    
    public VirtualCurrencyPack[] getVirtualCurrencyPacks() {
        VirtualCurrencyPack[] packs = new VirtualCurrencyPack[currencyPacks.size()];

        for(int i = 0; i < currencyPacks.size(); i++) {
            packs[i] = currencyPacks.get(i);
        }
        return packs;
    }
}
