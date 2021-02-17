package com.tailoredapps.codagram.models
import com.google.gson.annotations.SerializedName


/*data class Group(
    val user: User,
)*/
data class Group(
    val creator: Creator?,
    val id: String?,
    val image: Image?,
    val members: List<Member>,
    val name: String
)

data class Creator(
    val user:User

)

data class Member(
 val user:User
)

