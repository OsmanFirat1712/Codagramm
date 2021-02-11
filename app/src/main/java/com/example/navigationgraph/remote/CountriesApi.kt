package com.example.navigationgraph.remote

import com.example.navigationgraph.models.Country
import retrofit2.http.GET

interface CountriesApi {
    @GET("all")
    suspend fun getAllCountries():List<Country>
}