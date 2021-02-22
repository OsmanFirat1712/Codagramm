package com.tailoredapps.codagram.interfaces

import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.GroupInvite
import com.tailoredapps.codagram.remoteModels.ReplyToInvite

interface ReplyInviteCallBack {
    fun replyInvite(accept: Group)


}