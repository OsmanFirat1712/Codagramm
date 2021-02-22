package com.tailoredapps.codagram.remote

import com.tailoredapps.codagram.models.*
import com.tailoredapps.codagram.remoteModels.GroupList
import retrofit2.http.*
import java.util.*

interface CodagramApi {
    @POST("user")
    suspend fun addUser(@Body user: SendUser): User

    @GET("user")
    suspend fun getUser(): User

    @GET("user/search")
    suspend fun getSearchedUser(@Query ("query") input:String?): SearchResult

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                   POST ///

    @POST("group")
    suspend fun createGroup(@Body group: GroupCreate):Group

    @GET("group")
    suspend fun getAllGroups(): GroupList

    @GET("group/invites")
    suspend fun sendGroupInvites(@Body groupInviteBody: GroupInviteBody):Group

    @GET("group/{id}")
    suspend fun getGroupbyId(@Path("id" )id:String):Group




}