package com.tailoredapps.codagram.ui.groupscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.GroupInvite
import com.tailoredapps.codagram.remote.CodaGramApi
import com.tailoredapps.codagram.remoteModels.ReplyToInvite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
class MyGroupScreenViewMode(private val context: Context, private val codaGramApi: CodaGramApi) :
    ViewModel() {

    @ExperimentalCoroutinesApi
    private val searchForUser = MutableLiveData<GroupInvite>()


    @ExperimentalCoroutinesApi
    fun getSearchedUser(): LiveData<GroupInvite> = searchForUser

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
            val response = codaGramApi.getAllGroups()
            updateUi(response.groups)
            delay(1000)

        }
    }

    fun getAllGroups(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = codaGramApi.getAllGroups()
            updateUi(response.groups)
            delay(1000)

        }
    }

    @ExperimentalCoroutinesApi
    private fun updateUi(update: List<Group>) {
        viewModelScope.launch(Dispatchers.Main) {
            myGroups.value = update
        }
    }

    fun getInvites(){
        try {
            viewModelScope.launch(Dispatchers.IO) {
               val response = codaGramApi.getGroupInvitees()
                updateList(response.invites)


/*
                val selectedUsers = myInvites.value?.filter{}
*/
                val selectedUsers = searchForUser.value?.replyToInvite


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

    fun answerInvites(id: String, accept: Boolean){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = codaGramApi.replyToanyInvite(id,ReplyToInvite(accept))
                val selectedUsers = searchForUser.value?.replyToInvite

            }
        } catch (ie: Exception) {
            Timber.e(ie)

        }


    }




}