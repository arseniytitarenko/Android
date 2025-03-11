package com.example.exam3;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.Call;

public interface WeatherService {

    @GET("weather")
    Call<WeatherResponse> getWeather(
            @Query("q") String city,
            @Query("units") String units,
            @Query("appid") String apiKey
    );
}
