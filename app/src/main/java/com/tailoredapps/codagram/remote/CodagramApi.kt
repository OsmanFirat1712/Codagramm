package com.tailoredapps.codagram.remote

import com.tailoredapps.codagram.models.*
import com.tailoredapps.codagram.remoteModels.*
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
    //                   GROUP ///

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

    @PUT("group/invites/{id}")
    suspend fun replyToanyInvite(@Path("id" ) id:String, @Body accept: ReplyToInvite?):Response<Unit>

    @POST("post/{id}/like")
    suspend fun likeToComment(@Path("id")id:String,@Body like: CommentLike? ):Response<Unit>

    @DELETE("group/{id}")
    suspend fun deleteGroup(@Path("id") id: String):Response<Unit>

    @DELETE("group/{id}/remove/{uId}")
    suspend fun deleteMember(@Path("id") id: String, @Path("uId")uId:String):Response<Unit>

    @DELETE("group/{id}/exit")
    suspend fun exitGroup(@Path("id") id: String):Response<Unit>


    @PUT("group/{id}")
    suspend fun updateGroup(@Path("id") id:String, @Body updateGroup:UpdateGroup):Group

    @POST("post")
    suspend fun newStoryPost(@Body postBody: PostBody):PostList

    @GET("post")
    suspend fun getStoryPost(@Query("id")id:String?): PostList

    @GET("post/{id}")
    suspend fun getPostId(@Path("id")id:String):Post

    @POST("post/{id}/comment")
    suspend fun postComment(@Path("id")id:String,@Body commentBody: CommentBody): CommentList

    @GET("post/{id}/comment")
    suspend fun getComment(@Query("id")id:String?):CommentList




}