package com.tailoredapps.codagram.remote

import com.tailoredapps.codagram.models.*
import com.tailoredapps.codagram.remoteModels.GroupList
import com.tailoredapps.codagram.remoteModels.InvitesList
import com.tailoredapps.codagram.remoteModels.ReplyToInvite
import retrofit2.Response
import retrofit2.http.*

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

    @POST("group/invites")
    suspend fun sendGroupInvites(@Body groupInviteBody: GroupInviteBody?):Response<Unit>

    @GET("group/{id}")
    suspend fun getGroupbyId(@Path("id" )id:String):Group

    @GET("group/invites")
    suspend fun getGroupInvitees(): InvitesList

    @GET("group/invites/{id}")
    suspend fun replyToanyInvite(@Path("id" ) id:String, @Body accept: ReplyToInvite)





}