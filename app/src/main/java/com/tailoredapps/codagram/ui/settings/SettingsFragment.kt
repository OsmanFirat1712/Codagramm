package com.tailoredapps.codagram.ui.settings

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentGroupBinding
import com.tailoredapps.codagram.databinding.FragmentSettingsBinding
import com.tailoredapps.codagram.ui.groupscreen.GroupViewModel
import org.koin.android.ext.android.inject
import java.io.File

class SettingsFragment:Fragment() {

    private lateinit var binding:FragmentSettingsBinding
    private lateinit var file: File
    private val viewModel: SettingsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changePassword()
        changeFirstName()
        changeLastName()
        changeNickName()
        changeEmail()

    }

    fun alert() {
        binding.ivProfileImage.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireContext())

            Snackbar.make(requireView(), "saasd", Snackbar.LENGTH_SHORT).show()
            // build alert dialog

            // set message of alert dialog
            dialogBuilder.setMessage("Willst du das Bild ändern oder Löschen?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("EDIT", DialogInterface.OnClickListener { dialog, id ->
                    uploadClickAction()

                })
                // negative button text and action
                .setNegativeButton("DELETE", DialogInterface.OnClickListener { dialog, id ->
                    deleteUserImage()
                    dialog.cancel()

                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("AlertDialogExample")
            // show alert dialog
            alert.show()
        }


    }


    fun deleteUserImage() {
        viewModel.deleteUserImage()
    }

    private fun uploadClickAction(){
        Toast.makeText(requireContext(),"Clicked", Toast.LENGTH_LONG).show()
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()


        /*if (ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),
                Array(1){Manifest.permission.READ_EXTERNAL_STORAGE},121)
        }*/
        listImages()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            binding.ivProfileImage.setImageURI(fileUri)

            //You can get File object from intent
            file = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath:String = ImagePicker.getFilePath(data)!!
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Snackbar.make(requireView(), ImagePicker.getError(data), Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(requireView(), "Task Cancelled", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun listImages(){
        var i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
    }


    private fun changePassword(){
        binding.tvPassword.setOnClickListener{
            

        }
    }

    private fun changeFirstName(){
        binding.tvUserName.setOnClickListener {

        }
    }

    private fun changeLastName(){
        binding.tvLastName.setOnClickListener {

        }
    }

    private fun changeNickName(){
        binding.tvNickName.setOnClickListener {

        }
    }

    private fun changeEmail(){
        binding.tvEmail.setOnClickListener {

        }
    }
}