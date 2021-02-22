package com.tailoredapps.codagram.models

import java.util.*


data class GroupInvite(
    val id: String,
    val inviter: User,
    val name: String,
    val group: Group,
    val invitedAt: String,
    val members:List<User>,
    var selected: Boolean = false

)

data class GroupInviteBody(
    val groupId:String?,
    val invitees:List<String>?
)
