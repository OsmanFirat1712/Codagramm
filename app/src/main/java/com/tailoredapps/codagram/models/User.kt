package com.tailoredapps.codagram.models

data class User(
    val email: String,
    val firstname: String,
    val id: String,
    val image: Image,
    val lastname: String,
    val nickname: String
)

data class Image(
    val url: String
)