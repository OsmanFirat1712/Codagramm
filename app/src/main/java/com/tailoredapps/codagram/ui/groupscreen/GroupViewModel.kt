package com.tailoredapps.codagram.ui.groupscreen

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.remote.CodagramApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Query
import timber.log.Timber


class GroupViewModel(private val context: Context, private val codagramApi: CodagramApi) : ViewModel() {


    fun createGroup(group: Group){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                codagramApi.createGroup(group)
            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }


    fun searchUser(query:String){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                codagramApi.getSearchedUser(query)
            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }



}