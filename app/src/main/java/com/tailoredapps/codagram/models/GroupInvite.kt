package com.tailoredapps.codagram.models

import java.util.*


data class GroupInvite(
    val id: String,
    val inviter: User,
    val group: Group,
    val invitedAt: String

)

data class GroupInviteBody(
    val groupId:UUID,
    val invitees:List<String>
)
