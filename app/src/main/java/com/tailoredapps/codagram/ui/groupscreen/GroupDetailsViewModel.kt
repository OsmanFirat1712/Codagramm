package com.tailoredapps.codagram.ui.groupscreen

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.models.*
import com.tailoredapps.codagram.remote.CodaGramApi
import com.tailoredapps.codagram.remoteModels.SelectedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import java.lang.Exception
import java.util.*

@ExperimentalCoroutinesApi
class GroupDetailsViewModel(private val context: Context, private val codaGramApi: CodaGramApi) :
    ViewModel() {

    /*    val coroutineExceptionHandler = CoroutineExceptionHandler{_, t ->
            run {
                t.printStackTrace()
               // showErrorOrSomething()
            }
        }*/
    @ExperimentalCoroutinesApi
    private val myImage = MutableLiveData<Group>()

    @ExperimentalCoroutinesApi
    fun getMyImage(): LiveData<Group> = myImage


    @ExperimentalCoroutinesApi
    private val myGroupMembers = MutableLiveData<List<User>>()

    @ExperimentalCoroutinesApi
    fun getMyGroupMembers(): LiveData<List<User>> = myGroupMembers

    @ExperimentalCoroutinesApi
    private val myGroups = MutableLiveData<List<Group>>()

    @ExperimentalCoroutinesApi
    fun getMyGroups(): LiveData<List<Group>> = myGroups

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = codaGramApi.getAllGroups()
            updateUi(response.groups)
        }
    }

    @ExperimentalCoroutinesApi
    private fun updateImage(group: Group) {
        viewModelScope.launch(Dispatchers.Main) {
            myImage.value = group
        }
    }

    fun getAllGroupsonlyObject(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = codaGramApi.getGroupbyId(id)
            updateImage(response)
        }
    }

    fun getAllGroups() {
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
                val response = codaGramApi.getGroupbyId(id)
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
                val response = codaGramApi.getSearchedUser(input)
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
            codaGramApi.deleteGroup(id)

        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun deleteMember(id: String, uId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = codaGramApi.deleteMember(id, uId)
            if (response.isSuccessful)
                getGroupById(id)

        }


    }

    fun updateGroup(id: String, name: UpdateGroup) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = codaGramApi.updateGroup(id, name)
            if (response.isSuccessful) {
                val update = codaGramApi.getGroupbyId(id)
                updateImage(update)

            }
        }
    }

    fun exitGroup(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = codaGramApi.exitGroup(id)

        }
    }

    fun sendGroupInvites(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedUsers = searchForUser.value?.filter { it.selected }?.map { it.user.id }
            codaGramApi.sendGroupInvites(GroupInviteBody(id, selectedUsers as List<String>))
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun deleteGroupImage(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = codaGramApi.deleteGroupImage(id)
            if (response.isSuccessful) {
                getAllGroupsonlyObject(id)
                /*   val com = codaGramApi.getGroupbyId(id)
                   updateImage(com)*/
            }


        }
    }

    fun updateGroupImage(id: String, uri: Uri) {
        val file = File(uri.path!!)
        val requestBody: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val part: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", file.name, requestBody)
        viewModelScope.launch(Dispatchers.IO) {
            val response = codaGramApi.addImageToGroup(id, part)
            if (response.isSuccessful) {
                getAllGroupsonlyObject(id)
            }

        }
    }
}
