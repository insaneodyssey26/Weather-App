package com.masum.weather.network.model

import com.squareup.moshi.Json

data class CurrentWeatherResponse(
    @Json(name = "name") val cityName: String?,
    @Json(name = "main") val main: Main?,
    @Json(name = "weather") val weather: List<Weather>?,
    @Json(name = "dt") val timestamp: Long?
)

data class Main(
    @Json(name = "temp") val temp: Double?,
    @Json(name = "feels_like") val feelsLike: Double?,
    @Json(name = "humidity") val humidity: Int?
)

data class Weather(
    @Json(name = "main") val main: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "icon") val icon: String?
)
