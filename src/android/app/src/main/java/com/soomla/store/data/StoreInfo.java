/*
 * Copyright (C) 2012 Soomla Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soomla.store.data;

import android.util.Log;
import com.soomla.store.IStoreAssets;
import com.soomla.store.domain.data.VirtualCurrencyPack;
import java.util.Arrays;
import java.util.List;

/**
 * This class holds the store's meta data including:
 * - Virtual Currencies
 * - Virtual Currency Packs
 * - All kinds of Virtual goods
 * - Virtual categories
 * - NonConsumables
 */
public class StoreInfo {
    private static final String TAG = "SOOMLA StoreInfo";
    private static List<VirtualCurrencyPack> mVirtualCurrencyPacks;

    /**
     * This function initializes StoreInfo. On first initialization, when the
     * database doesn't have any previous version of the store metadata, StoreInfo
     * is being loaded from the given {@link IStoreAssets}. After the first initialization,
     * StoreInfo will be initialized from the database.
     *
     * IMPORTANT: If you want to override the current StoreInfo, you'll have to bump the version of your
     * implementation of IStoreAssets in order to remove the metadata when the application loads.
     */
    public static void setStoreAssets(IStoreAssets assets) {
        if (assets == null) {
            Log.e(TAG, "The given store assets can't be null !");
        } else {
            mVirtualCurrencyPacks = Arrays.asList(assets.getVirtualCurrencyPacks());
        }
    }

    public static VirtualCurrencyPack getPackByGoogleProductId(String productId) throws Exception {
        for (VirtualCurrencyPack virtualCurrencyPack : mVirtualCurrencyPacks) {
            if (virtualCurrencyPack.getProductId().equals(productId)) {
                return virtualCurrencyPack;
            }
        }
        throw new Exception();
    }

    public static VirtualCurrencyPack getPackByItemId(String itemId) throws Exception {
        for (VirtualCurrencyPack virtualCurrencyPack : mVirtualCurrencyPacks) {
            try {
                if (virtualCurrencyPack.getItemId().equals(itemId)) {
                    return virtualCurrencyPack;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        throw new Exception();
    }

    public static List<VirtualCurrencyPack> getCurrencyPacks() {
        return mVirtualCurrencyPacks;
    }
}