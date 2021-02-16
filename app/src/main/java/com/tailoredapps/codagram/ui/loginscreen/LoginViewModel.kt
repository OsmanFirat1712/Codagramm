package com.tailoredapps.codagram.ui.loginscreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.tailoredapps.codagram.remote.CodagramApi

class LoginViewModel(private val context: Context,private val codagramApi: CodagramApi) : ViewModel() {

     fun retrieveAndStoreToken(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    val token = task.result
                    val userId = FirebaseAuth.getInstance().currentUser!!.uid
                    FirebaseDatabase.getInstance()
                        .getReference("tokens")
                        .child(userId)
                        .setValue(token)
                }
            }
    }

    fun getToken(){
        val mUser = FirebaseAuth.getInstance().currentUser
        mUser!!.getIdToken(true)
            .addOnCompleteListener{task ->
                if (task.isSuccessful()) {
                    val idToken: String = task.result!!.token.toString()
                    Log.e("idtoken",idToken)
                    // Send token to your backend via HTTPS
                    // ...
                } else {
                    // Handle error -> task.getException();
                }
            }
    }

    fun saveUserInfo(firstName:String,lastName:String,nickName:String){
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef:DatabaseReference = FirebaseDatabase.getInstance().reference.child("users")
        val userMap = HashMap<String,Any>()
        userMap["uid"] = currentUserId
        userMap["lastName"] = currentUserId
        userMap["firstName"] = currentUserId
        userMap["nickName"] = currentUserId
        userMap["bio"] = "hey iam using sex"
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/codagram-2bc9b.appspot.com/o/images%2Fpokemon-7-282136.png?alt=media&token=e96a7882-485c-4cde-8f74-b00efc600860"

        usersRef.child(currentUserId).setValue(userMap)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Toast.makeText(context,"Account has been created !",Toast.LENGTH_LONG).show()
                }
            }

    }

}



