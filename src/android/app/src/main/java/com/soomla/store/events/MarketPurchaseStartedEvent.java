package com.soomla.store.events;

/* loaded from: classes.dex */
public class MarketPurchaseStartedEvent {
    private String mProductID;

    public MarketPurchaseStartedEvent(String str) {
        this.mProductID = str;
    }

    public String getProductId() {
        return this.mProductID;
    }
}