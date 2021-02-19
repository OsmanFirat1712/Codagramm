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
                val selectedUsers = searchForUser.value?.filter {it.selected}?.map {it.user.id}


                codagramApi.createGroup(GroupCreate(group, selectedUsers as List<String>))
            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }

    fun infoMessage(statusIcon: ImageView){
        statusIcon.setOnClickListener {
            Toast.makeText(context,"Group name have to more than 6 character", Toast.LENGTH_LONG).show()
        }
    }

    fun statusRules(groupEditText: String,statusIcon: ImageView){
        when{
            groupEditText.isNotEmpty() -> {
                Toast.makeText(context,"Group name cannot be Empty!",Toast.LENGTH_LONG).show()
                
            }
            else ->{
                Toast.makeText(context,"Successfully!",Toast.LENGTH_LONG).show()

            }

        }
    }


}