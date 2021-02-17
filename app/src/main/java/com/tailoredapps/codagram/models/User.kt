package com.tailoredapps.codagram.models

data class User(
    val nickname: String,
    val firstname: String,
    val lastname: String,
    val id: String?,
    val image: Image?,
    val email: String?,

)
{
    init {
        require(nickname.isNotEmpty()){throw IllegalArgumentException()}
        require(firstname.isNotEmpty()){throw IllegalArgumentException()}
        require(lastname.isNotEmpty()){throw IllegalArgumentException()}
        email?.let { require(it.isNotEmpty()){throw IllegalArgumentException()} }
    }
}


data class Image(
    val url: String
)


