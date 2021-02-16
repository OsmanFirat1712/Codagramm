package com.tailoredapps.codagram.ui.loginscreen

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tailoredapps.codagram.MainView
import com.tailoredapps.codagram.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.tailoredapps.codagram.databinding.ActivityLoginActiviyBinding
import com.tailoredapps.codagram.databinding.ActivityMainBinding
import com.tailoredapps.codagram.databinding.FragmentFirstBinding
import com.tailoredapps.codagram.databinding.RegisterDialogBinding
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent
import timber.log.Timber


class LoginActiviy : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firstNameFire: String
    private lateinit var lastNameFire: String
    private lateinit var nickNameFire: String
    private lateinit var binding: ActivityLoginActiviyBinding
    private lateinit var alertDialogBinding: RegisterDialogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_login_activiy)
        binding = ActivityLoginActiviyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        createAlertDialog()


        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.text.trim().toString()
                    .isNotEmpty() || binding.etPassword.text.trim().toString().isNotEmpty()
            ) {
                login(
                    binding.etEmail.text.trim().toString(),
                    binding.etPassword.text.trim().toString()
                )
                val tokenId = auth.currentUser!!.getIdToken(true)
                Log.e("token", auth.currentUser!!.getIdToken(true).toString())

            } else {
                Toast.makeText(this, "input required", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Timber.e( "Successfuqlly")
                                saveUserInfo(firstNameFire, lastNameFire, nickNameFire)
                                retrieveAndStoreToken()
                                var intent = Intent(this, MainView::class.java)
                                startActivity(intent)
                            } else {
                                Log.e("task message", "Failed" + task.exception)
                            }
                        }
                }
            }
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.e("task message", "Successfully")qqq
                    val user = auth.currentUser
                    updateUI(user, binding.etEmail.text.trim().toString())
                    retrieveAndStoreToken()
                    getToken()
                    var intent = Intent(this, MainView::class.java)
                    startActivity(intent)
                } else {
                    Log.e("task message", "Failed" + task.exception)
                }
            }
    }


    fun createAlertDialog() {
        binding.btnNewAccount.setOnClickListener {
/*
            val view: View = layoutInflater.inflate(R.layout.register_dialog, alertDialogBinding.root)
*/
            alertDialogBinding = RegisterDialogBinding.inflate(layoutInflater)
            val alertDialog = MaterialAlertDialogBuilder(this)
            alertDialog.setView(alertDialogBinding.root)
            alertDialog.create()
            val alert = alertDialog.show()

            firstNameFire = alertDialogBinding.dialogFirstName.text.toString()
            lastNameFire = alertDialogBinding.dialogLastName.text.toString()
            nickNameFire = alertDialogBinding.dialogNickName.text.toString()

            alertDialogBinding.btnDialogCreate.setOnClickListener {
                when {

                    alertDialogBinding.dialogPassword.text.toString().isEmpty() -> Toast.makeText(
                        this,
                        "password can not be empty!",
                        Toast.LENGTH_LONG
                    ).show()
                    alertDialogBinding.dialogFirstName.text.toString().isEmpty() -> Toast.makeText(
                        this,
                        "first name can not be empty!",
                        Toast.LENGTH_LONG
                    ).show()
                    alertDialogBinding.dialogLastName.text.toString().isEmpty() -> Toast.makeText(
                        this,
                        "last name can not be empty!",
                        Toast.LENGTH_LONG
                    ).show()
                    alertDialogBinding.dialogNickName.text.toString().isEmpty() -> Toast.makeText(
                        this,
                        "nick name can not be empty!",
                        Toast.LENGTH_LONG
                    ).show()

                    else -> createUser(
                        alertDialogBinding.dialogEmail.text.toString(),
                        alertDialogBinding.dialogPassword.text.toString()
                    )
                }

            }
            alertDialogBinding.btnDialogCancel.setOnClickListener {
                Log.e("message", "clicked")
                alert.dismiss()
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?, email: String) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                var intent = Intent(this, MainView::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Email address is not verified.Please verify your email address!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun saveUserInfo(firstName: String, lastName: String, nickName: String) {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("users")
        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserId
        userMap["lastName"] = currentUserId
        userMap["firstName"] = currentUserId
        userMap["nickName"] = currentUserId
        userMap["bio"] = "hey iam using sex"
        userMap["image"] =
            "https://firebasestorage.googleapis.com/v0/b/codagram-2bc9b.appspot.com/o/images%2Fpokemon-7-282136.png?alt=media&token=e96a7882-485c-4cde-8f74-b00efc600860"

        usersRef.child(currentUserId).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Account has been created !", Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun retrieveAndStoreToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
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

    fun getToken() {
        val mUser = FirebaseAuth.getInstance().currentUser
        mUser!!.getIdToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idToken: String = task.result!!.token.toString()
                    Log.e("idtoken", idToken)
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