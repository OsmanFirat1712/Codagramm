package com.tailoredapps.codagram.remote

import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.SearchResult
import com.tailoredapps.codagram.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CodagramApi {
    @POST("user")
    suspend fun addUser(@Body user: User): User

    @GET("user")
    suspend fun getUser(): User

    @GET("user/search")
    suspend fun getSearchedUser(@Query ("query") input:String?): SearchResult

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                   POST ///

    @POST("group")
    suspend fun createGroup(@Body group: Group):Group

}