package com.masum.weather.network.model

import com.squareup.moshi.Json

data class ForecastResponse(
    @Json(name = "list") val list: List<ForecastItem>?
)

data class ForecastItem(
    @Json(name = "dt") val dt: Long?,
    @Json(name = "main") val main: ForecastMain?,
    @Json(name = "weather") val weather: List<ForecastWeather>?,
    @Json(name = "dt_txt") val dtTxt: String?
)

data class ForecastMain(
    @Json(name = "temp") val temp: Double?
)

data class ForecastWeather(
    @Json(name = "main") val main: String?,
    @Json(name = "description") val description: String?
)
