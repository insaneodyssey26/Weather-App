package com.masum.weather.repository

import com.masum.weather.network.RetrofitInstance
import com.masum.weather.network.model.CurrentWeatherResponse

class WeatherRepository {
    suspend fun getCurrentWeather(city: String): CurrentWeatherResponse {
        return RetrofitInstance.api.getCurrentWeather(city)
    }
}
