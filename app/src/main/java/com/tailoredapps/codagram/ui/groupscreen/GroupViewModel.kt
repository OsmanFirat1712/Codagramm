package com.tailoredapps.codagram.ui.groupscreen

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.models.GroupCreate
import com.tailoredapps.codagram.models.GroupInvite
import com.tailoredapps.codagram.models.GroupInviteBody
import com.tailoredapps.codagram.remote.CodagramApi
import com.tailoredapps.codagram.remoteModels.GroupList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File


class GroupViewModel(private val context: Context, private val codagramApi: CodagramApi) :
    ViewModel() {
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

    @ExperimentalCoroutinesApi
    fun createGroup(group: String,uri: Uri) {
        val file = File(uri.path!!)
        val requestBody: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val part: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, requestBody)
        try {

            viewModelScope.launch(Dispatchers.IO) {
                val selectedUsers = searchForUser.value?.filter { it.selected }?.map { it.user.id }

                val response = codagramApi.createGroup(GroupCreate(group, selectedUsers as List<String>)).also {
                    codagramApi.getGroupbyId(it.id)

                    codagramApi.addImageToGroup(it.id,part)
                }

                codagramApi.sendGroupInvites(GroupInviteBody(response.id, selectedUsers as List<String>,))


            }
        } catch (ie: Exception) {
            Timber.e(ie)
        }

    }


}