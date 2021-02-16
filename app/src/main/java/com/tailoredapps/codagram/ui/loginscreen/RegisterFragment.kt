package com.tailoredapps.codagram.ui.loginscreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.databinding.RegisterFragmentBinding
import org.koin.android.ext.android.inject

class RegisterFragment : Fragment() {

    private  val viewModel: LoginViewModel by inject()
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: RegisterFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()


        binding.btnDialogCreate.setOnClickListener {
            createUser(binding.dialogEmail.text.toString(),binding.dialogPassword.text.toString())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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