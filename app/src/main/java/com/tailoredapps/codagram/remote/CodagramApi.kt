package com.tailoredapps.codagram.remote

import com.tailoredapps.codagram.models.User
import retrofit2.http.GET

interface CodagramApi {
    @GET("all")
    suspend fun getAllCountries():List<User>
}