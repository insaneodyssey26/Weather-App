package com.masum.weather.network

import com.masum.weather.BuildConfig
import com.masum.weather.network.model.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    // Example: api.openweathermap.org/data/2.5/weather?q=London&appid=API_KEY&units=metric
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = BuildConfig.OPENWEATHER_API_KEY,
        @Query("units") units: String = "metric"
    ): CurrentWeatherResponse
}
