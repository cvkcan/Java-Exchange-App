package com.example.javamobilprogramlama.chart;


import android.content.res.Configuration;
import android.graphics.Color;

import com.example.javamobilprogramlama.R;
import com.example.javamobilprogramlama.apis.historical.HistoricalApi;
import com.example.javamobilprogramlama.apis.historical.HistoricalModel;
import com.example.javamobilprogramlama.apis.historical.HistoricalResponse;
import com.example.javamobilprogramlama.databinding.RecyclerGetcurrencyBinding;
import com.example.javamobilprogramlama.models.Statics;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateChart implements HistoricalDataCallBack{
    private HistoricalResponse historicalModel;
    private RecyclerGetcurrencyBinding binding;
    private LineChart lineChart;


    public CreateChart(RecyclerGetcurrencyBinding binding, LineChart lineChart){
        this.binding = binding;
        this.lineChart = lineChart;
        lineChart.setNoDataText(binding.getRoot().getResources().getString(R.string.chart_NoData));
    }
    private void createLineChart(HistoricalModel[] historicalModels) {
        List<Entry> interbankRate = new ArrayList<>();
        for (int i = 0; i < historicalModels.length; i++) {
            HistoricalModel historicalModel = historicalModels[i];
            System.out.println(ConvertDateTime(Long.parseLong(historicalModel.getPointInTime())));
            interbankRate.add(new Entry(i, Float.parseFloat(historicalModel.getInterbankRate())));
        }

        int interbankColor;
        int textColor;
        Description description = new Description();

        if (isDarkThemeEnabled()) {
            interbankColor = Color.BLUE;
            textColor = Color.WHITE;
            lineChart.getAxisLeft().setTextColor(Color.WHITE);
            lineChart.getAxisRight().setTextColor(Color.WHITE);
            lineChart.getXAxis().setTextColor(Color.WHITE);
            lineChart.getLegend().setTextColor(Color.WHITE);
            lineChart.getDescription().setTextColor(Color.WHITE);
            description.setTextColor(Color.WHITE);
        } else {
            interbankColor = Color.rgb(255, 165, 0);
            textColor = Color.BLACK;
        }

        LineDataSet lineDataSet = new LineDataSet(interbankRate, binding.getRoot().getResources().getString(R.string.chart_Buying));
        lineDataSet.setColor(interbankColor);
        lineDataSet.setValueTextColor(textColor);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);

        description.setText(binding.getRoot().getResources().getString(R.string.chart_Description));
        lineChart.setDescription(description);

        lineChart.invalidate();
        binding.circularProgressIndicator.setIndeterminate(false);
    }
    private boolean isDarkThemeEnabled() {
        int darkModeFlag = binding.getRoot().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return darkModeFlag == Configuration.UI_MODE_NIGHT_YES;
    }
    private String ConvertDateTime(Long timeSpan){
        Date onFetchedTimeSpan = new Date(timeSpan);
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String date = dateFormat.format(onFetchedTimeSpan);
        return date;
    }

    private void testJson(HistoricalDataCallBack callBack, String timeSpan, String type){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Statics.HistoricalURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HistoricalApi service = retrofit.create(HistoricalApi.class);
        Call<HistoricalResponse> callData = service.getData(timeSpan, type);

        callData.enqueue(new Callback<HistoricalResponse>() {
            @Override
            public void onResponse(Call<HistoricalResponse> call, Response<HistoricalResponse> response) {
                if (response.isSuccessful()) {
                    HistoricalResponse historicalResponse = response.body();
                    if (historicalResponse != null) {
                        historicalModel = historicalResponse;
                        HistoricalModel[] historicalPoints = historicalResponse.getHistoricalPoints();
                        callBack.onHistoricalDataFetched(historicalPoints);
                        System.out.println("Success");
                    }
                } else {
                    System.out.println("Request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<HistoricalResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onHistoricalDataFetched(HistoricalModel[] historicalModels) {
        createLineChart(historicalModels);
    }

    public void fetchData(String timeSpan, String type){
        lineChart.clear();
        binding.circularProgressIndicator.setIndeterminate(true);
        testJson(this, timeSpan,type);
    }
}
