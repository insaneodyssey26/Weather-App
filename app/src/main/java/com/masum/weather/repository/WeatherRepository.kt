package com.masum.weather.repository

import com.masum.weather.network.RetrofitInstance
import com.masum.weather.network.model.CurrentWeatherResponse
import com.masum.weather.network.model.ForecastResponse

class WeatherRepository {
    suspend fun getCurrentWeather(city: String): CurrentWeatherResponse {
        return RetrofitInstance.api.getCurrentWeather(city)
    }

    suspend fun getForecast(city: String): ForecastResponse {
        return RetrofitInstance.api.getForecast(city)
    }
}
