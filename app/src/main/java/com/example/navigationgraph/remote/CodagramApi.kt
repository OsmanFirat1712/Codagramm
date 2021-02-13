package com.example.navigationgraph.remote

import com.example.navigationgraph.models.User
import retrofit2.http.GET

interface CodagramApi {
    @GET("all")
    suspend fun getAllCountries():List<User>
}