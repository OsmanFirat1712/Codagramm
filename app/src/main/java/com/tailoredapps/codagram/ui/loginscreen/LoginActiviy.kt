package com.tailoredapps.codagram.ui.loginscreen

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tailoredapps.codagram.MainView
import com.tailoredapps.codagram.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
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
                //val tokenId = auth.currentUser!!.getIdToken(true)
                Log.e("token",auth.currentUser!!.getIdToken(true).toString())

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
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener{task->
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
                }
             }

    fun login(email: String,password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful){
                    Log.e("task message","Successfully")
                    val user = auth.currentUser
                    updateUI(user,userEmail.text.trim().toString())
                    retrieveAndStoreToken()
                    getToken()
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

    private fun updateUI(currentUser:FirebaseUser?,email: String){
        if (currentUser != null){
            if (currentUser.isEmailVerified){
                var intent = Intent(this,MainView::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Email address is not verified.Please verify your email address!",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveUserInfo(firstName:String,lastName:String,nickName:String){
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
                    Toast.makeText(this,"Account has been created !",Toast.LENGTH_LONG).show()
                }
            }

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