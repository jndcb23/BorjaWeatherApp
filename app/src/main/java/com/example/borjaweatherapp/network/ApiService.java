package com.example.borjaweatherapp.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ApiService {

    String CURRENT = "/data/2.5/weather";

    @GET(CURRENT)
    Call<String> requestCurrentWeather(
            @Query("q") String cityName,
            @Query("appid") String appId
    );
}
