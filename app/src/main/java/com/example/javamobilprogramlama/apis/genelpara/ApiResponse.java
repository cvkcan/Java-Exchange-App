package com.example.javamobilprogramlama.apis.genelpara;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("USD")
    private CurrencyModel usd;
    @SerializedName("EUR")
    private CurrencyModel euro;
    @SerializedName("GBP")
    private CurrencyModel sterlin;

    public ApiResponse(CurrencyModel usd, CurrencyModel euro, CurrencyModel sterlin) {
        this.usd = usd;
        this.euro = euro;
        this.sterlin = sterlin;
    }

    public CurrencyModel getUsd() {
        return usd;
    }

    public void setUsd(CurrencyModel usd) {
        this.usd = usd;
    }

    public CurrencyModel getEuro() {
        return euro;
    }

    public void setEuro(CurrencyModel euro) {
        this.euro = euro;
    }

    public CurrencyModel getSterlin() {
        return sterlin;
    }

    public void setSterlin(CurrencyModel sterlin) {
        this.sterlin = sterlin;
    }
}