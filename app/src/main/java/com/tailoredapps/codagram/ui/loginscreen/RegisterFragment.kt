package com.tailoredapps.codagram.ui.loginscreen

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.MainView
import com.tailoredapps.codagram.R

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var firstNameFire:String
    private lateinit var lastNameFire:String
    private lateinit var nickNameFire:String
    private lateinit var createButton: Button

    private lateinit var etEmail:EditText
    private lateinit var etPassword:EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createButton = view.findViewById(R.id.btnDialogCreate)
        etEmail = view.findViewById(R.id.dialogEmail)
        etPassword = view.findViewById(R.id.dialogPassword)

        createButton.setOnClickListener {
            createUser(etEmail.text.toString(),etPassword.text.toString())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun createUser(email:String,password:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(requireActivity()){task ->
                if (task.isSuccessful){
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener{task->
                            if (task.isSuccessful){
                                Log.e("task message","Successfully")
                                viewModel.saveUserInfo(firstNameFire,lastNameFire,nickNameFire)
                                viewModel.retrieveAndStoreToken()
                                //var intent = Intent(this, MainView::class.java)
                                //startActivity(intent)
                            }

                            else{
                                Log.e("task message","Failed"+task.exception)
                            }
                        }
                }
            }
    }

}