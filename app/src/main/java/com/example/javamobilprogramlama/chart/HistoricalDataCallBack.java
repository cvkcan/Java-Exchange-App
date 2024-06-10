package com.example.javamobilprogramlama.chart;

import com.example.javamobilprogramlama.apis.historical.HistoricalModel;

public interface HistoricalDataCallBack {
    void onHistoricalDataFetched(HistoricalModel[] historicalModels);
}
