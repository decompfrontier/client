package com.soomla.store.events;

import com.soomla.store.domain.data.GoogleMarketItem;

/* loaded from: classes.dex */
public class MarketRefundEvent {
    private GoogleMarketItem mGoogleMarketItem;

    public MarketRefundEvent(GoogleMarketItem googleMarketItem) {
        this.mGoogleMarketItem = googleMarketItem;
    }

    public GoogleMarketItem getGoogleMarketItem() {
        return this.mGoogleMarketItem;
    }
}