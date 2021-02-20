package com.tailoredapps.codagram.ui.groupscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.remote.CodagramApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MyGroupScreenViewMode(private val context: Context, private val codagramApi: CodagramApi) : ViewModel() {

    @ExperimentalCoroutinesApi
    private val myGroups = MutableLiveData<List<Group>>()

    @ExperimentalCoroutinesApi
    fun getMyGroups(): LiveData<List<Group>> = myGroups

    init {
        viewModelScope.launch(Dispatchers.IO){
            val response = codagramApi.getAllGroups()
            updateUi(response.groupList)
        }
    }
    @ExperimentalCoroutinesApi
    private fun updateUi(update:List<Group>){
        viewModelScope.launch(Dispatchers.Main){
            myGroups.value = update
        }
    }


}