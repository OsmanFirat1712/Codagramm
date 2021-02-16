package com.tailoredapps.codagram.remote

import com.tailoredapps.codagram.models.User
import retrofit2.http.GET
import retrofit2.http.POST

interface CodagramApi {
    @POST("user")
    suspend fun addUser()
}