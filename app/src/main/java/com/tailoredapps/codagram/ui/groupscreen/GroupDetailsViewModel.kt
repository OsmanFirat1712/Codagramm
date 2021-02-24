package com.tailoredapps.codagram.ui.groupscreen

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.UpdateGroup
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.remote.CodagramApi
import com.tailoredapps.codagram.remoteModels.GroupList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
import timber.log.Timber
import java.lang.Exception
import java.util.*
import java.util.Collections.emptyList

@ExperimentalCoroutinesApi
class GroupDetailsViewModel(private val context: Context, private val codagramApi: CodagramApi) :
    ViewModel() {

    @ExperimentalCoroutinesApi
    private val myGroupMembers = MutableLiveData<List<User>>()

    @ExperimentalCoroutinesApi
    fun getMyGroupMembers(): LiveData<List<User>> = myGroupMembers

    @ExperimentalCoroutinesApi
    private val myGroups = MutableLiveData<List<Group>>()

    @ExperimentalCoroutinesApi
    fun getMyGroups(): LiveData<List<Group>> = myGroups
    private val response: GroupList = GroupList(emptyList())


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

    @ExperimentalCoroutinesApi
    private fun updateMembersList(update: List<User>) {
        viewModelScope.launch(Dispatchers.Main) {
            myGroupMembers.value = update
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalCoroutinesApi
    fun getGroupById(id: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = codagramApi.getGroupbyId(id)
                updateMembersList(response.members)
                /*   response.members.forEach {
                       val users = it.id
                   }
                   codagramApi.deleteMember(id)*/
            }
        } catch (ie: Exception) {
            Timber.e(ie)
        }
    }

    @ExperimentalCoroutinesApi
    private val searchForUser = MutableLiveData<List<SelectedUser>>()

    @ExperimentalCoroutinesApi
    fun getSearchedUser(): LiveData<List<SelectedUser>> = searchForUser


    @ExperimentalCoroutinesApi
    fun searchUser(input: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = codagramApi.getSearchedUser(input)
                updateSearchList(response.users.map { (SelectedUser(it)) })
            }
        } catch (ie: Exception) {
            Timber.e(ie)
        }
    }

    @ExperimentalCoroutinesApi
    fun updateSearchList(userSearch: List<SelectedUser>) {
        viewModelScope.launch(Dispatchers.Main) {
            searchForUser.value = userSearch
        }

    }

    fun deleteGroup(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            codagramApi.deleteGroup(id)

        }

    }

    fun deleteMember(id: String, uId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            codagramApi.deleteMember(id, uId)

        }


    }

    fun updateGroup(id: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            codagramApi.updateGroup(id, UpdateGroup(name))

        }
    }
}
