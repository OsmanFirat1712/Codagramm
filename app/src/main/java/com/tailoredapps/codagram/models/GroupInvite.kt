package com.tailoredapps.codagram.models


data class GroupInvite(
    val id: String,
    val inviter: User,
    val group: Group,
    val invitedAt: String

)