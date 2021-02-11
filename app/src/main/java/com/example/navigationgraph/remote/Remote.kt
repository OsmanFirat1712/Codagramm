package com.example.navigationgraph.remote

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object Remote {
    private const val BASE_URL = "https://restcountries.eu/rest/v2/"

    fun getApi(): CountriesApi = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(GsonConverterFactory.create(Gson()))
    }.build().create(CountriesApi::class.java)
}