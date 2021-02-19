package com.tailoredapps.codagram.ui.loginscreen

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.databinding.RegisterFragmentBinding
import com.tailoredapps.codagram.models.SendUser
import com.tailoredapps.codagram.models.User
import org.koin.android.ext.android.inject
import java.io.IOException

class RegisterFragment : Fragment() {

    private val viewModel: LoginViewModel by inject()
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: RegisterFragmentBinding
    lateinit var imageData: Uri
    var selectedImage: Bitmap? = null
    var selectImage: ImageView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()


        binding.btnDialogCreate.setOnClickListener {
            createUser(binding.dialogEmail.text.toString(), binding.dialogPassword.text.toString())
            it.findNavController().navigate(RegisterFragmentDirections.actionLoginToHome())

        }

        binding.ivImageView.setOnClickListener {
            Toast.makeText(requireContext(),"Clicked", Toast.LENGTH_LONG).show()
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(),
                    Array(1){ Manifest.permission.READ_EXTERNAL_STORAGE},121)
            }
            listImages()
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
                                viewModel.postUser(SendUser(nickname,firstName,lastName) )
                            } else {
                                Log.e("task message", "Failed" + task.exception)
                            }
                        }
                }
            }
    }

    private fun listImages(){
        val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intentToGallery, 2)
    }

}