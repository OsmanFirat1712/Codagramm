package com.tailoredapps.codagram.ui.newStoryScreen

import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.remote.CodagramApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class NewStoryViewModel(private val codagramApi: CodagramApi):ViewModel() {

    @ExperimentalCoroutinesApi
    private val myGroups = MutableLiveData<List<Group>>()
    @ExperimentalCoroutinesApi
    fun getMyGroups(): LiveData<List<Group>> = myGroups

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = codagramApi.getAllGroups()
            updateUi(response.groups)
        }
    }

    @ExperimentalCoroutinesApi
     fun getGroups(){
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



}