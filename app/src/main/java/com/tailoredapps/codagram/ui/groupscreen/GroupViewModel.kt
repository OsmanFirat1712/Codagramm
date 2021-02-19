package com.tailoredapps.codagram.ui.groupscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.GroupCreate
import com.tailoredapps.codagram.models.SearchResult
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.remote.CodagramApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import okhttp3.internal.filterList
import timber.log.Timber


class GroupViewModel(private val context: Context, private val codagramApi: CodagramApi) : ViewModel() {
    @ExperimentalCoroutinesApi
    private val searchForUser = MutableLiveData<List<SelectedUser>>()

    @ExperimentalCoroutinesApi
    fun getSearchedUser(): LiveData<List<SelectedUser>> = searchForUser



    @ExperimentalCoroutinesApi
    fun searchUser(input: String){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = codagramApi.getSearchedUser(input)
                updateSearchList(response.users.map{(SelectedUser(it))})
            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }

    @ExperimentalCoroutinesApi
    fun updateSearchList(userSearch:List<SelectedUser>){
        viewModelScope.launch (Dispatchers.Main){
            searchForUser.value = userSearch
        }

    }


    @ExperimentalCoroutinesApi
    fun createGroup(group: String){
       try {
            viewModelScope.launch(Dispatchers.IO) {
                val selectedUsers = searchForUser.value.filter {it.selected}.map {it.user.id}


                codagramApi.createGroup(GroupCreate(group,selectedUsers))
            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }




}