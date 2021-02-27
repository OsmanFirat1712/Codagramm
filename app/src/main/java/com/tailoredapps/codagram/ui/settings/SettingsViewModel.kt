package com.tailoredapps.codagram.ui.settings

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.remote.CodagramApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
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


    fun addPhoto(uri: Uri){
        try {
            val file = File(uri.path!!)
            val requestBody: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val part: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, requestBody)
            viewModelScope.launch(Dispatchers.IO) {

                codagramApi.updateUserImage(part)

            }
        } catch (ie: Exception) {
            Timber.e(ie)
        }
    }

    fun deleteUserImage(){
        viewModelScope.launch(Dispatchers.IO) {
            codagramApi.deleteUserImage()

        }
    }

}