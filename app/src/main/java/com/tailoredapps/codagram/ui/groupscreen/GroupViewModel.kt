package com.tailoredapps.codagram.ui.groupscreen

import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.SearchResult
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.remote.CodagramApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import timber.log.Timber


class GroupViewModel(private val context: Context, private val codagramApi: CodagramApi) : ViewModel() {
    @ExperimentalCoroutinesApi
    private val searchForUser = MutableLiveData<List<SearchResult>>()

    @ExperimentalCoroutinesApi
    fun getSearchedUser(): LiveData<List<SearchResult>> = searchForUser



    @ExperimentalCoroutinesApi
    fun searchUser(input: String){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = codagramApi.getSearchedUser(input)
                updateSearchList(response)
            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }

    @ExperimentalCoroutinesApi
    fun updateSearchList(userSearch:SearchResult){
        viewModelScope.launch (Dispatchers.Main){
            searchForUser.value = listOf(userSearch)
        }

    }


    fun createGroup(group: Group){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                codagramApi.createGroup(group)
            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }






}