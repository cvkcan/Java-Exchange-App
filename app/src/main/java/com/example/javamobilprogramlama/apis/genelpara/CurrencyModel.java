package com.example.javamobilprogramlama.apis.genelpara;

import com.google.gson.annotations.SerializedName;

public class CurrencyModel {
    @SerializedName("satis")
    private String forexBuying;
    @SerializedName("alis")
    private String forexSelling;
    @SerializedName("degisim")
    private String variation;
    @SerializedName("d_oran")
    private String exchangeRatio;
    public CurrencyModel(String forexBuying, String forexSelling, String variation, String exchangeRatio) {
        this.forexBuying = forexBuying;
        this.forexSelling = forexSelling;
        this.variation = variation;
        this.exchangeRatio = exchangeRatio;
    }
    public String getForexBuying() {
        return forexBuying;
    }
    public void setForexBuying(String forexBuying) {
        this.forexBuying = forexBuying;
    }
    public String getForexSelling() {
        return forexSelling;
    }
    public void setForexSelling(String forexSelling) {
        this.forexSelling = forexSelling;
    }
    public String getVariation() {
        return variation;
    }
    public void setVariation(String variation) {
        this.variation = variation;
    }
    public String getExchangeRatio() {
        return exchangeRatio;
    }
    public void setExchangeRatio(String exchangeRatio) {
        this.exchangeRatio = exchangeRatio;
    }
}
