package com.tailoredapps.codagram.models


data class GroupInvite(
    val id: String,
    val inviter: User,
    val group: Group,
    val invitedAt: String

)

data class GroupInviteBody(
    val groupId: String,
    val invitees:List<String>
)
