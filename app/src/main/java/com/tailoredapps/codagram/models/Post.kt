package com.tailoredapps.codagram.models

import java.util.*

data class Post(
    val id:String,
    val user:User,
    val group: Group,
    val description:String,
    val image: Image,
    val likes:Int,
    val userLiked:Boolean,
    val comments:List<String>
)

data class PostBody(
    val description:String,
    val group:String,
    val tags:List<String>,

)