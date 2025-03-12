package com.soomla.store.domain.data;

import com.android.billingclient.api.SkuDetails;
import com.soomla.store.domain.data.GoogleMarketItem;

/* loaded from: classes.dex */
public class VirtualCurrencyPack {
    private static final String TAG = "SOOMLA VirtualCurrencyPack";
    private String mDescription;
    private GoogleMarketItem mGoogleItem;
    private String mItemId;
    private String mName;
    private String mPriceString = "";
    private SkuDetails mSkuDetails = null;

    public VirtualCurrencyPack(String str, String str2, String str3, String str4, double d) {
        this.mName = str;
        this.mDescription = str2;
        this.mItemId = str3;
        this.mGoogleItem = new GoogleMarketItem(str4, GoogleMarketItem.Managed.UNMANAGED, d);
    }

    public String getName() {
        return this.mName;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getItemId() {
        return this.mItemId;
    }

    public GoogleMarketItem getGoogleItem() {
        return this.mGoogleItem;
    }

    public String getProductId() {
        return this.mGoogleItem.getProductId();
    }

    public double getPrice() {
        return this.mGoogleItem.getPrice();
    }

    public String getPriceString() {
        return this.mPriceString;
    }

    public void setPriceString(String str) {
        this.mPriceString = str;
    }

    public SkuDetails getSkuDetails() {
        return this.mSkuDetails;
    }

    public void setSkuDetails(SkuDetails skuDetails) {
        this.mSkuDetails = skuDetails;
    }
}