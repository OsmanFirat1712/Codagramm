package com.tailoredapps.codagram.models

import com.google.gson.annotations.SerializedName


/*data class Group(
    val user: User,
)*/
data class Group(
    val creator: User?,
    val id: String,
    val image: Image?,
    val members: List<User>,
    val name: String
)




data class GroupCreate(
   val name: String,
   val members: List<String>,

)



