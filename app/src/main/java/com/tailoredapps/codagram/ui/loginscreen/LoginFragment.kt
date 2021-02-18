package com.tailoredapps.codagram.ui.loginscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.GoogleAuthUtil.getToken
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tailoredapps.codagram.databinding.LoginFragmentBinding
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {


    private  val viewModel: LoginViewModel by inject()
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: LoginFragmentBinding

    private val action = LoginFragmentDirections.actionLoginToHome()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.text.trim().toString().isNotEmpty() || binding.etPassword.text.trim().toString().isNotEmpty()) {
                login(binding.etEmail.text.trim().toString(), binding.etPassword.text.trim().toString())
                //val tokenId = auth.currentUser!!.getIdToken(true)
                Log.e("token", auth.currentUser!!.getIdToken(true).toString())

            } else {
                Toast.makeText(requireContext(), "input required", Toast.LENGTH_LONG).show()
            }        }

        binding.btnNewAccount.setOnClickListener {
            it.findNavController().navigate(LoginFragmentDirections.actionLoginToRegister())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    fun login(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.e("task message", "Successfully")
                    val user = auth.currentUser
                    updateUI(user, binding.etEmail.text.toString())
                    viewModel.getToken()
                    viewModel.getUser()
                    findNavController().navigate(action)
                } else {
                    Log.e("task message", "Failed" + task.exception)
                }
            }
    }

    fun updateUI(currentUser: FirebaseUser?, email: String) {
        if (currentUser != null) {
            if (!currentUser.isEmailVerified) {
                findNavController().navigate(action)
                //var intent = Intent(context,MainView::class.java)
                //ContextCompat.startActivity(intent)
            } else {
                Toast.makeText(
                    context, "Email address is not verified.Please verify your email address!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        viewModel.getToken()
        if (user != null) {
            findNavController().navigate(action)

        }

    }


}
