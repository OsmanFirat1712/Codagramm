package com.tailoredapps.codagram.ui.groupscreen

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.remote.CodagramApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import timber.log.Timber
import java.lang.Exception

class GroupDetailsViewModel(private val context: Context,private val codagramApi: CodagramApi) : ViewModel() {



    @ExperimentalCoroutinesApi
    fun getGroupById(groupId:String){
        try {
            viewModelScope.launch(Dispatchers.IO){
                codagramApi.getGroupbyId(groupId)

            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }

    fun setTitle(title:TextView,groupName:String){
        title.setText(groupName)
    }


}