package com.tailoredapps.codagram.ui.loginscreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.models.SendUser
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.remote.CodagramApi
import com.tailoredapps.codagram.remote.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class LoginViewModel(private val context: Context, private val codagramApi: CodagramApi) :
    ViewModel() {


    private val sessionManager = SessionManager(context)

    fun getToken() {
        val mUser = FirebaseAuth.getInstance().currentUser
        mUser!!.getIdToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful()) {
                    val idToken: String = task.result!!.token.toString()
                    Log.e("idtoken", idToken)
                    sessionManager.saveAuthToken(idToken)
                    Log.e("savetoken", "dssd")

                    // Send token to your backend via HTTPS
                    // ...
                } else {
                    // Handle error -> task.getException();
                }
            }
    }


    fun postUser(user: SendUser) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
/*
                FirebaseAuth.getInstance().createUserWithEmailAndPassword("","").auth
*/
                codagramApi.addUser(user)
            }
        } catch (ie: Exception) {
            Timber.e(ie)
        }

    }

    fun getUser(){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                codagramApi.getUser()
            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }
}



