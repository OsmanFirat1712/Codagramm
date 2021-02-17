package com.tailoredapps.codagram.ui.loginscreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.databinding.RegisterFragmentBinding
import com.tailoredapps.codagram.models.User
import org.koin.android.ext.android.inject

class RegisterFragment : Fragment() {

    private val viewModel: LoginViewModel by inject()
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: RegisterFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()


        binding.btnDialogCreate.setOnClickListener {
            createUser(binding.dialogEmail.text.toString(), binding.dialogPassword.text.toString())
            it.findNavController().navigate(RegisterFragmentDirections.actionLoginToHome())

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(layoutInflater, container, false)

        return binding.root
    }


    fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.e("task message", "Successfully")
                                viewModel.getToken()
                                val nickname = binding.dialogNickName.text.toString()
                                val firstName = binding.dialogFirstName.text.toString()
                                val lastName = binding.dialogLastName.text.toString()
                                val user = User(nickname, firstName, lastName, null, null, null)
                                viewModel.postUser(user)
                            } else {
                                Log.e("task message", "Failed" + task.exception)
                            }
                        }
                }
            }
    }

}