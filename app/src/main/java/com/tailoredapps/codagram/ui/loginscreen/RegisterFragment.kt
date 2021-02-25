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
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.RegisterFragmentBinding
import com.tailoredapps.codagram.models.SendUser
import com.tailoredapps.codagram.models.User
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import java.io.IOException

class RegisterFragment : Fragment() {

    private val viewModel: LoginViewModel by inject()
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: RegisterFragmentBinding
    val REQUEST_IMAGE_CAPTURE = 2
    lateinit var imageData: Uri
    var selectedImage: Bitmap? = null
    var selectImage: ImageView? = null
    var image:String? = null

    val check:Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        statusInfo()
        createActive(view)
        uploadImage(view)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null){
            imageData = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imageData)
            image = binding.ivImageView.setImageBitmap(bitmap).toString()
        }
    }




    private fun createUser(email: String, password: String) {
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
                                val password = binding.dialogPassword.text.toString()
                                viewModel.postUser(SendUser(nickname,firstName,lastName) )
                                view?.findNavController()?.navigate(RegisterFragmentDirections.actionLoginToHome())

                            } else {
                                Log.e("task message", "Failed" + task.exception)
                            }
                        }
                }
            }
    }

    private fun listImages(){
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "Choose Picture"), REQUEST_IMAGE_CAPTURE)
    }

    private fun statusInfo(){
        viewModel.infoMessage(binding.ivFirstNameStatus,binding.ivLastNameStatus,binding.ivNickNameStatus,binding.ivEmailStatus,binding.ivPasswordStatus)

    }

    private fun createActive(view: View){
        binding.btnDialogCreate.setOnClickListener {

            if (!viewModel.statusRulesFirstName(binding.dialogFirstName,binding.ivFirstNameStatus) || !viewModel.statusRulesLastName(binding.dialogLastName,binding.ivLastNameStatus) || !viewModel.statusRulesNickName(binding.dialogNickName,binding.ivNickNameStatus) || !viewModel.statusRulesEmail(binding.dialogEmail) || !viewModel.statusRulesPassword(binding.dialogPassword,binding.ivPasswordStatus)){
                return@setOnClickListener
            }else{
                createUser(binding.dialogEmail.text.toString(),binding.dialogPassword.text.toString())

            }

        }
    }

    private fun uploadImage(view: View) {
        binding.ivImageView.setOnClickListener {
            Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_LONG).show()
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(requireActivity(),
                    Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE }, 121
                )
            }
            listImages()
        }


    }


}