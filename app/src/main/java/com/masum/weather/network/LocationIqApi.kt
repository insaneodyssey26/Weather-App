package com.masum.weather.network

import com.masum.weather.network.model.LocationIqSuggestion
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationIqApi {
    @GET("v1/autocomplete.php")
    suspend fun autocomplete(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int = 5,
        @Query("dedupe") dedupe: Int = 1,
        @Query("format") format: String = "json"
    ): List<LocationIqSuggestion>
}
