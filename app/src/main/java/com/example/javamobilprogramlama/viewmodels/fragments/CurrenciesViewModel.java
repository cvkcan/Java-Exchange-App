package com.example.javamobilprogramlama.viewmodels.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.javamobilprogramlama.apis.genelpara.ApiResponse;
import com.example.javamobilprogramlama.apis.genelpara.CurrencyApi;
import com.example.javamobilprogramlama.models.Statics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrenciesViewModel extends AndroidViewModel {

    private final MutableLiveData<ApiResponse> currencyModel;
    private final MutableLiveData<Boolean> isLoading;
    private final CurrencyApi service;

    public CurrenciesViewModel(@NonNull Application application) {
        super(application);
        currencyModel = new MutableLiveData<>();
        isLoading = new MutableLiveData<>(false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Statics.GenelparaURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(CurrencyApi.class);
    }

    public LiveData<ApiResponse> getCurrencyModel() {
        return currencyModel;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadCurrencyData(String currencyType) {
        isLoading.setValue(true);
        Call<ApiResponse> callData = service.getData();
        callData.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currencyModel.setValue(response.body());
                }
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                isLoading.setValue(false);
            }
        });
    }
}