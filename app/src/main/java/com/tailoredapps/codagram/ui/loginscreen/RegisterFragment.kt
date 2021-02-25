package com.tailoredapps.codagram.ui.loginscreen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.loader.content.CursorLoader
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.RegisterFragmentBinding
import com.tailoredapps.codagram.models.SendUser
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.koin.android.ext.android.inject
import java.io.File

class RegisterFragment : Fragment() {

    private val viewModel: LoginViewModel by inject()
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: RegisterFragmentBinding
    val REQUEST_IMAGE_CAPTURE = 2
    lateinit var imageData: Uri
    var selectedImage: Bitmap? = null
    var selectImage: ImageView? = null
    var image2:String? = null

    lateinit var requestBody:RequestBody

    lateinit var file:File


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

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            imageData = data.data!!

            //var mediaStore = MediaStore.Images.Media.getContentUri("Choose Picture",21)
            var bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageData)
            val image = binding.ivImageView.setImageBitmap(bitmap).toString()

            val downloadUrl = imageData.toString()
            Log.e("uri",downloadUrl)
            Log.e("bitmap", image)
            Log.e("imagedata",imageData.toString())
            //Log.e("mediastore",mediaStore.toString())

            getRealPathFromURI(imageData)




        }
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
                                val password = binding.dialogPassword.text.toString()
                                viewModel.postUser(SendUser(nickname,firstName,lastName,image2) )
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
                createUser(binding.dialogEmail.toString(),binding.dialogPassword.toString())
                it.findNavController().navigate(RegisterFragmentDirections.actionLoginToHome())
                findNavController().popBackStack(R.id.action_LoginToHome, true)

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

    private fun getRealPathFromURI(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(requireContext(), contentUri, proj, null, null, null)
        val cursor: Cursor? = loader.loadInBackground()
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val result = column_index?.let { cursor?.getString(it) }
        Log.e("result",result.toString())
        cursor?.close()
        return result
    }


}