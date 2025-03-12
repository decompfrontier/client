package com.soomla.store.events;

import com.soomla.store.domain.data.GoogleMarketItem;

/* loaded from: classes.dex */
public class MarketPurchaseEvent {
    private GoogleMarketItem mGoogleMarketItem;
    public String mSignature;
    public String mSignedData;

    public MarketPurchaseEvent(String str, String str2) {
        this.mGoogleMarketItem = null;
        this.mSignedData = str;
        this.mSignature = str2;
    }

    public MarketPurchaseEvent(GoogleMarketItem googleMarketItem) {
        this.mGoogleMarketItem = googleMarketItem;
    }

    public GoogleMarketItem getGoogleMarketItem() {
        return this.mGoogleMarketItem;
    }
}