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
import com.tailoredapps.codagram.databinding.ActivityLoginActiviyBinding
import com.tailoredapps.codagram.databinding.LoginFragmentBinding
import com.tailoredapps.codagram.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: RegisterFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = RegisterFragmentBinding.inflate(layoutInflater)

        binding.btnDialogCreate.setOnClickListener {
            createUser(binding.dialogEmail.text.toString(),binding.dialogPassword.text.toString())
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
                                viewModel.saveUserInfo(binding.dialogFirstName.text.toString(),binding.dialogLastName.text.toString(),binding.dialogNickName.text.toString())
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