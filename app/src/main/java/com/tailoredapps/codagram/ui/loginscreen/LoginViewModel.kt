package com.tailoredapps.codagram.ui.loginscreen

import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.models.SendUser
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.remote.CodagramApi
import com.tailoredapps.codagram.remote.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class LoginViewModel(private val context: Context, private val codagramApi: CodagramApi) :
    ViewModel() {

    private lateinit var auth: FirebaseAuth
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

                } else {
                    // Handle error -> task.getException();
                }
            }
    }


    fun postUser(user: SendUser) {
        try {
            viewModelScope.launch(Dispatchers.IO) {

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



    fun infoMessage(nameStatusIcon: ImageView,lastNameStatusIcon: ImageView,nickNameStatusIcon: ImageView,eMailStatusIcon: ImageView,passwordStatusIcon:ImageView){
        nameStatusIcon.setOnClickListener {
            Toast.makeText(context, R.string.register_firstName_rules, Toast.LENGTH_LONG).show()
        }
        lastNameStatusIcon.setOnClickListener {
            Toast.makeText(context,R.string.register_lastName_rules, Toast.LENGTH_LONG).show()
        }
        nickNameStatusIcon.setOnClickListener {
            Toast.makeText(context,R.string.register_nickName_rules, Toast.LENGTH_LONG).show()
        }
        eMailStatusIcon.setOnClickListener {
            Toast.makeText(context,R.string.register_email_rules, Toast.LENGTH_LONG).show()
        }
        passwordStatusIcon.setOnClickListener {
            Toast.makeText(context,R.string.register_password_rules, Toast.LENGTH_LONG).show()
        }
    }



    //postUser(user = SendUser(nickName,firstName,lastName,null))

    fun  statusRulesFirstName(firstName: EditText,lastNameStatusIcon: ImageView):Boolean{

        var gol:String = firstName.text.toString()

        if(gol.isEmpty() && gol.length < 5){
            firstName.setError("Group name cannot be Empty!")
            lastNameStatusIcon.setImageResource(R.drawable.icons8_cancel_24px_2)
            return false
        }

        else{
            lastNameStatusIcon.setImageResource(R.drawable.icons8_ok_24px)
            return true
        }
    }

    fun statusRulesLastName(lastName: EditText,lastNameStatusIcon: ImageView):Boolean{

        var ln = lastName.text.toString()
         if(ln.isEmpty()){
            lastName.setError("last name can not be Empty!")
            lastNameStatusIcon.setImageResource(R.drawable.icons8_cancel_24px_2)
             return false
        }

        else if(ln.length < 5){
             lastName.setError("last name must be more than 6 character!")
             lastNameStatusIcon.setImageResource(R.drawable.icons8_cancel_24px_2)
             return false
         }

        else{
            lastNameStatusIcon.setImageResource(R.drawable.icons8_ok_24px)
             return true
        }

    }


    fun statusRulesNickName(nickName: EditText,nickNameStatusIcon: ImageView):Boolean{
        val nn = nickName.text.toString()
        if(nn.isEmpty()){
            nickName.setError("Nick name can not be Empty!")
            nickNameStatusIcon.setImageResource(R.drawable.icons8_cancel_24px_2)
            return false
        }

        else if (nn.length < 5){
            nickName.setError("nick name must be more than 5 character!")
            nickNameStatusIcon.setImageResource(R.drawable.icons8_cancel_24px_2)
            return false
        }

        else{
            nickNameStatusIcon.setImageResource(R.drawable.icons8_ok_24px)
            return true
        }
    }

    fun statusRulesEmail(email: EditText):Boolean{
        var emailPattern:String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        var em:String = email.text.toString()

        if(em.isEmpty()){

            email.setError("Email can not be Empty!")
            return false
        }
        else if(em.length < 5){
            email.setError("Email can not be less than 5 character!")
            return false
        }

        else{
            return true
        }
    }

    fun statusRulesPassword(password: EditText,passwordStatusIcon: ImageView):Boolean{
        val pw = password.text.toString()
        if(pw.isEmpty()){
            password.setError("can not be Empty!")
            passwordStatusIcon.setImageResource(R.drawable.icons8_cancel_24px_2)
            return false
        }

        else if (pw.length < 5){
            password.setError("have to be more than 5 character!!")
            passwordStatusIcon.setImageResource(R.drawable.icons8_cancel_24px_2)
            return false
        }

        else{
            passwordStatusIcon.setImageResource(R.drawable.icons8_ok_24px)
            return true
        }
    }


}



