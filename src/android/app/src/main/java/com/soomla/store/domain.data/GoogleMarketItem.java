package com.soomla.store.domain.data;

/* loaded from: classes.dex */
public class GoogleMarketItem {
    private static final String TAG = "SOOMLA GoogleMarketItem";
    private Managed mManaged;
    private double mPrice;
    private String mProductId;

    public enum Managed {
        MANAGED,
        UNMANAGED,
        SUBSCRIPTION
    }

    public GoogleMarketItem(String str, Managed managed, double d) {
        this.mProductId = str;
        this.mManaged = managed;
        this.mPrice = d;
    }

    public String getProductId() {
        return this.mProductId;
    }

    public Managed getManaged() {
        return this.mManaged;
    }

    public void setManaged(Managed managed) {
        this.mManaged = managed;
    }

    public double getPrice() {
        return this.mPrice;
    }
}