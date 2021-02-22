package com.tailoredapps.codagram.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.remote.CodagramApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class SettingsViewModel(private val context: Context, private val codagramApi: CodagramApi):ViewModel() {


    fun getFireBaseInfo(newPassword:String){
        val user = FirebaseAuth.getInstance().currentUser
        val getEmail = user?.email.toString()
        val updateEmail = user?.updatePassword(newPassword)
    }

    fun getUser(){
        try {
            viewModelScope.launch(Dispatchers.IO){
                val getName = codagramApi.getUser().firstname
                val getLastName = codagramApi.getUser().lastname
                val getNickName = codagramApi.getUser().nickname
                val getEmail = codagramApi.getUser().email

            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }


}