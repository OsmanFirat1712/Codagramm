package com.tailoredapps.codagram.ui.newStoryScreen

import android.os.Build
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.PostBody
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.remote.CodagramApi
import com.tailoredapps.codagram.remoteModels.GroupList
import com.tailoredapps.codagram.ui.groupscreen.SelectedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.URI

class NewStoryViewModel(private val codagramApi: CodagramApi):ViewModel() {
    private lateinit var id: String
    @ExperimentalCoroutinesApi
    private val myGroupMembers = MutableLiveData<List<User>>()

    @ExperimentalCoroutinesApi
    fun getMyGroupMembers(): LiveData<List<User>> = myGroupMembers

    @ExperimentalCoroutinesApi
    private val myGroups = MutableLiveData<List<Group>>()
    @ExperimentalCoroutinesApi
    fun getMyGroups(): LiveData<List<Group>> = myGroups

    @ExperimentalCoroutinesApi
    private val searchForUser = MutableLiveData<List<SelectedUser>>()
    @ExperimentalCoroutinesApi
    fun getSearchedUser(): LiveData<List<SelectedUser>> = searchForUser


    @ExperimentalCoroutinesApi
     fun getGroups(){
        viewModelScope.launch(Dispatchers.IO) {
            var response = codagramApi.getAllGroups()
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
    fun searchUser(input: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = codagramApi.getGroupbyId(input)
                updateSearchList(response.members.map { (SelectedUser(it)) })
            }
        } catch (ie: Exception) {
            Timber.e(ie)
        }
    }

    @ExperimentalCoroutinesApi
     fun post(description:String,groupId:String){
        viewModelScope.launch (Dispatchers.IO){
            var response2 = codagramApi.getAllGroups()
            //var groupId =codagramApi.getGroupbyId(response2.groups.first().id).toString()
            val selectedUsers = searchForUser.value?.filter { it.selected }?.map { it.user.id }
            var response =codagramApi.newStoryPost(PostBody(description,groupId,selectedUsers as List<String>))
        }
    }


    @ExperimentalCoroutinesApi
    fun updateSearchList(userSearch: List<SelectedUser>) {
        viewModelScope.launch(Dispatchers.Main) {
            searchForUser.value = userSearch
        }

    }
/*    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalCoroutinesApi
    fun getGroupById(id: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
               *//* val responses = codagramApi.getAllGroups()
                responses.groups.forEach { it->
                     id  = it.id
                }*//*

                val response = codagramApi.getGroupbyId(id)

                updateMembersList(response.members)
                *//*   response.members.forEach {
                       val users = it.id
                   }
                   codagramApi.deleteMember(id)*//*
            }
        } catch (ie: java.lang.Exception) {
            Timber.e(ie)
        }
    }

    @ExperimentalCoroutinesApi
    private fun updateMembersList(update: List<User>) {
        viewModelScope.launch(Dispatchers.Main) {
            myGroupMembers.value = update
        }
    }*/


    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalCoroutinesApi
    fun getGroupById(id: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                /* val responses = codagramApi.getAllGroups()
                 responses.groups.forEach { it->
                      id  = it.id
                 }*/

                val response = codagramApi.getGroupbyId(id)

                updateMembersList(response.members)
                /*   response.members.forEach {
                       val users = it.id
                   }
                   codagramApi.deleteMember(id)*/
            }
        } catch (ie: java.lang.Exception) {
            Timber.e(ie)
        }
    }

    @ExperimentalCoroutinesApi
    private fun updateMembersList(update: List<User>) {
        viewModelScope.launch(Dispatchers.Main) {
            myGroupMembers.value = update
        }
    }

/*    private fun addPhoto(id: String,uri:Uri){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = codagramApi.addPhoto(id,)
            }
        } catch (ie: Exception) {
            Timber.e(ie)
        }
    }*/
    }

