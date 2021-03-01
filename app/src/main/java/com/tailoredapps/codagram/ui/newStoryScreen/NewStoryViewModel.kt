package com.tailoredapps.codagram.ui.newStoryScreen

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.PostBody
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.remote.CodaGramApi
import com.tailoredapps.codagram.ui.groupscreen.SelectedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File

class NewStoryViewModel(private val codaGramApi: CodaGramApi):ViewModel() {
    private lateinit var ids: String
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
            val response = codaGramApi.getAllGroups()
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
                val response = codaGramApi.getGroupbyId(input)
                ids = response.id.toString()

                updateSearchList(response.members.map { (SelectedUser(it)) })
            }
        } catch (ie: Exception) {
            Timber.e(ie)
        }
    }

    @ExperimentalCoroutinesApi
     fun post(description:String,groupId:String,uri: Uri){
        val file = File(uri.path!!)
        val requestBody:RequestBody= file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val part: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, requestBody)

        viewModelScope.launch (Dispatchers.IO){

            try {

                var response2 = codaGramApi.getAllGroups()
                //var groupId =codagramApi.getGroupbyId(response2.groups.first().id).toString()
                val selectedUsers = searchForUser.value?.filter { it.selected }?.map { it.user.id }

                var response =codaGramApi.newStoryPost(PostBody(description,groupId,selectedUsers as List<String>)).also {
                    codaGramApi.addPhoto(it.id.toString(),part)

            }



            }catch (ie: Exception){
                Timber.e(ie)
            }

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

                val response = codaGramApi.getGroupbyId(id)
                 ids = response.id.toString()
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

    fun addPhoto(id:String,uri: Uri){
        try {
            val file = File(uri.path!!)
            val requestBody:RequestBody= file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val part: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, requestBody)
            viewModelScope.launch(Dispatchers.IO) {

                  codaGramApi.addPhoto(id,part)

            }
        } catch (ie: Exception) {
            Timber.e(ie)
        }
    }
    }

