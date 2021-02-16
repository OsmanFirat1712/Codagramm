package com.tailoredapps.codagram.ui.loginscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tailoredapps.codagram.MainView
import com.tailoredapps.codagram.R

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var loginButton: Button
    private lateinit var createButton: Button
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        viewModel = LoginViewModel(requireContext())

        createButton = view.findViewById(R.id.btnNewAccount)
        userEmail = view.findViewById(R.id.etEmail)
        userPassword = view.findViewById(R.id.etPassword)
        loginButton = view.findViewById(R.id.btnLogin)

        loginButton.setOnClickListener {
            if (userEmail.text.trim().toString().isNotEmpty() || userPassword.text.trim().toString()
                    .isNotEmpty()
            ) {
                login(userEmail.text.trim().toString(), userPassword.text.trim().toString())
                //val tokenId = auth.currentUser!!.getIdToken(true)
                Log.e("token", auth.currentUser!!.getIdToken(true).toString())

            } else {
                Toast.makeText(requireContext(), "input required", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun login(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.e("task message", "Successfully")
                    val user = auth.currentUser
                    updateUI(user, userEmail.text.toString())
                    viewModel.retrieveAndStoreToken()
                    viewModel.getToken()
                    //var intent = Intent(context, MainView::class.java)
                    //ContextCompat.startActivity(intent)
                } else {
                    Log.e("task message", "Failed" + task.exception)
                }
            }
    }

    fun updateUI(currentUser: FirebaseUser?, email: String) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
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
}
