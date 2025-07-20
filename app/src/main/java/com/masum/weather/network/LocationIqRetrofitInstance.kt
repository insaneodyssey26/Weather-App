package com.masum.weather.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object LocationIqRetrofitInstance {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val api: LocationIqApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.locationiq.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(LocationIqApi::class.java)
    }
}
