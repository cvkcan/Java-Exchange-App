package com.example.javamobilprogramlama.apis.historical;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HistoricalApi {
    @GET("{timeSpan}/{type}/TRY?DecimalPlaces=6&ReportingInterval=daily&format=json")
    Call<HistoricalResponse> getData(@Path("timeSpan") String timeSpan,
                                     @Path("type") String type);
}
