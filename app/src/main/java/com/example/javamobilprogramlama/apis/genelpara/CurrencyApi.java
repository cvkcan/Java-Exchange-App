package com.example.javamobilprogramlama.apis.genelpara;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyApi {
    @GET("embed/altin.json")
    Call<ApiResponse> getData();
}
