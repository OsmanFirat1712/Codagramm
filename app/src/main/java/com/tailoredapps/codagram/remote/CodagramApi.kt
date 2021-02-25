package com.tailoredapps.codagram.remote

import com.tailoredapps.codagram.models.*
import com.tailoredapps.codagram.remoteModels.CommentList
import com.tailoredapps.codagram.remoteModels.GroupList
import com.tailoredapps.codagram.remoteModels.InvitesList
import com.tailoredapps.codagram.remoteModels.ReplyToInvite
import retrofit2.Response
import com.tailoredapps.codagram.remoteModels.PostList
import okhttp3.MultipartBody
import retrofit2.http.*

interface CodagramApi {
    @POST("user")
    suspend fun addUser(@Body user: SendUser): User

    @GET("user")
    suspend fun getUser(): User

    @GET("user/search")
    suspend fun getSearchedUser(@Query ("query") input:String?): SearchResult

    @PUT("user")
    suspend fun updateProfile(@Body user:SendUser):User

    @DELETE("user")
    suspend fun deleteUser():Response<Unit>
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
    suspend fun getStoryPost(@Query("gr")id:String?): PostList

    @GET("post/{id}")
    suspend fun getPostId(@Path("id")id:String):Post

    @POST("post/{id}/comment")
    suspend fun postComment(@Path("id")id:String,@Body commentBody: CommentBody): CommentList

    @GET("post/{id}/comment")
    suspend fun getComment(@Query("id")id:String?):CommentList

    @POST("post/{id}/image")
    suspend fun addPhoto(@Path("id")id: String,@Part file:MultipartBody.Part)


}