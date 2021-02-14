package com.example.navigationgraph.ui.loginscreen

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.BLUE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.navigationgraph.MainView
import com.example.navigationgraph.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class LoginActiviy : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var createButton: Button
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var auth: FirebaseAuth

    private lateinit var firstNameFire:String
    private lateinit var lastNameFire:String
    private lateinit var nickNameFire:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_activiy)

        auth = FirebaseAuth.getInstance()


        loginButton = findViewById(R.id.btnLogin)
        createButton = findViewById(R.id.btnNewAccount)

        userEmail = findViewById(R.id.etEmail)
        userPassword = findViewById(R.id.etPassword)




        createAlertDialog()
        
        loginButton.setOnClickListener {
            if (userEmail.text.trim().toString().isNotEmpty() || userPassword.text.trim().toString().isNotEmpty()){
                login(userEmail.text.trim().toString(),userPassword.text.trim().toString())
                val tokenId = auth.currentUser!!.getIdToken(true)
                Log.e("token",tokenId.toString())

            }
            else{
                Toast.makeText(this,"input required",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun createUser(email:String,password:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful){
                    Log.e("task message","Successfully")
                    saveUserInfo(firstNameFire,lastNameFire,nickNameFire)
                    retrieveAndStoreToken()
                    var intent = Intent(this,MainView::class.java)
                    startActivity(intent)
                }
                else{
                    Log.e("task message","Failed"+task.exception)
                }
            }
    }

    fun login(email: String,password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful){
                    Log.e("task message","Successfully")
                    retrieveAndStoreToken()
                    var intent = Intent(this,MainView::class.java)
                    startActivity(intent)
                }
                else{
                    Log.e("task message","Failed"+task.exception)
                }
            }
    }


    fun createAlertDialog(){
        createButton.setOnClickListener {
            val view:View = layoutInflater.inflate(R.layout.register_dialog,null)
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setView(view)
            alertDialog.create()
            val alert = alertDialog.show()

            val dialogCreateButton:Button = view.findViewById(R.id.btnDialogCreate)
            val dialogCancelButton:Button = view.findViewById(R.id.btnDialogCancel)

            val dialogEmail:EditText = view.findViewById(R.id.dialogEmail)
            val dialogPassword:EditText = view.findViewById(R.id.dialogPassword)
            val dialogFirstName:EditText = view.findViewById(R.id.dialogFirstName)
            val dialogLastName:EditText = view.findViewById(R.id.dialogLastName)
            val dialogNickName:EditText = view.findViewById(R.id.dialogNickName)


            firstNameFire = dialogFirstName.text.toString()
            lastNameFire = dialogLastName.text.toString()
            nickNameFire = dialogNickName.text.toString()

            dialogCreateButton.setOnClickListener{
                when{

                    dialogPassword.text.toString().isEmpty() -> Toast.makeText(this,"password can not be empty!",Toast.LENGTH_LONG).show()
                    dialogFirstName.text.toString().isEmpty() -> Toast.makeText(this,"first name can not be empty!",Toast.LENGTH_LONG).show()
                    dialogLastName.text.toString().isEmpty() -> Toast.makeText(this,"last name can not be empty!",Toast.LENGTH_LONG).show()
                    dialogNickName.text.toString().isEmpty() -> Toast.makeText(this,"nick name can not be empty!",Toast.LENGTH_LONG).show()

                    else -> createUser(dialogEmail.text.toString(),dialogPassword.text.toString())
                }

            }
            dialogCancelButton.setOnClickListener {
                Log.e("message","clicked")
                alert.dismiss()
            }
        }
    }

    private fun saveUserInfo(firstName:String,lastName:String,nickName:String){

    }

    private fun retrieveAndStoreToken(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    val token = task.result
                    val userId = FirebaseAuth.getInstance().currentUser!!.uid

                   // val msg = getString(R.string.msg_token_fmt, token)
                    //Log.d("tokentoken", msg)
                    //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()


                    FirebaseDatabase.getInstance()
                        .getReference("tokens")
                        .child(userId)
                        .setValue(token)
                }
            }
    }

/*
    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user != null){
            var intent = Intent(this,MainView::class.java)
            startActivity(intent)
        }

    }

 */


}