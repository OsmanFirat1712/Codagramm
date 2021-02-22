package com.tailoredapps.codagram.ui.groupscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.GroupInvite
import com.tailoredapps.codagram.remote.CodagramApi
import com.tailoredapps.codagram.remoteModels.ReplyToInvite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
class MyGroupScreenViewMode(private val context: Context, private val codagramApi: CodagramApi) :
    ViewModel() {

    @ExperimentalCoroutinesApi
    private val searchForUser = MutableLiveData<ReplyToInvite>()


    @ExperimentalCoroutinesApi
    fun getSearchedUser(): LiveData<ReplyToInvite> = searchForUser

    @ExperimentalCoroutinesApi
    private val myInvites = MutableLiveData<List<GroupInvite>>()
    @ExperimentalCoroutinesApi
    fun getMyInvites(): LiveData<List<GroupInvite>> = myInvites

    @ExperimentalCoroutinesApi
    private val myGroups = MutableLiveData<List<Group>>()
    @ExperimentalCoroutinesApi
    fun getMyGroups(): LiveData<List<Group>> = myGroups


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = codagramApi.getAllGroups()
            updateUi(response.groups)

        }
    }

    @ExperimentalCoroutinesApi
    private fun updateUi(update: List<Group>) {
        viewModelScope.launch(Dispatchers.Main) {
            myGroups.value = update
        }
    }

    fun getInvites(accept:ReplyToInvite){
        try {
            viewModelScope.launch(Dispatchers.IO) {
               val response = codagramApi.getGroupInvitees()
                updateList(response.invites)
                val selectedUsers = myInvites.value?.filter { it.selected }?.map { it.selected }

                codagramApi.replyToanyInvite(response.invites[1].id,accept)
            }
        } catch (ie: Exception) {
            Timber.e(ie)

        }


    }

    @ExperimentalCoroutinesApi
    private fun updateList(update: List<GroupInvite>) {
        viewModelScope.launch(Dispatchers.Main) {
            myInvites.value = update
        }
    }




}