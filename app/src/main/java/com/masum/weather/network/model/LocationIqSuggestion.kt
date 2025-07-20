package com.masum.weather.network.model

import com.squareup.moshi.Json

data class LocationIqSuggestion(
    @Json(name = "display_place") val displayPlace: String?,
    @Json(name = "display_address") val displayAddress: String?,
    @Json(name = "lat") val lat: String?,
    @Json(name = "lon") val lon: String?,
    @Json(name = "type") val type: String?,
    @Json(name = "address") val address: LocationIqAddress?
)

data class LocationIqAddress(
    @Json(name = "city") val city: String?,
    @Json(name = "state") val state: String?,
    @Json(name = "country") val country: String?
)
