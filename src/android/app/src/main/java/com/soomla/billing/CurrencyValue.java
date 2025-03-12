package com.soomla.billing;

/* loaded from: classes.dex */
public class CurrencyValue {
    String currency;
    String decimalPart;
    String integerPart;

    public String toString() {
        return "CurrencyValue [integerPart=" + this.integerPart + ", decimalPart=" + this.decimalPart + ", currency=" + this.currency + "]";
    }

    public String getIntegerPart() {
        return this.integerPart;
    }

    public void setIntegerPart(String str) {
        this.integerPart = str;
    }

    public String getDecimalPart() {
        return this.decimalPart;
    }

    public void setDecimalPart(String str) {
        this.decimalPart = str;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String str) {
        this.currency = str;
    }
}