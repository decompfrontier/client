package com.soomla.store.events;

/* loaded from: classes.dex */
public class MarketEvent {
    public String mPackageName;
    public String mSignature;
    public String mSignedData;

    public MarketEvent(String str, String str2, String str3) {
        this.mSignedData = str;
        this.mSignature = str2;
        this.mPackageName = str3;
    }
}