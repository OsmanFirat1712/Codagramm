package com.tailoredapps.codagram.ui.settings

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentGroupBinding
import com.tailoredapps.codagram.databinding.FragmentSettingsBinding
import com.tailoredapps.codagram.databinding.RegisterDialogBinding
import com.tailoredapps.codagram.models.UpdateGroup
import com.tailoredapps.codagram.ui.groupscreen.GroupViewModel
import com.tailoredapps.codagram.ui.loginscreen.LoginFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import java.io.File

class SettingsFragment:Fragment() {

    private lateinit var binding:FragmentSettingsBinding
    private lateinit var file: File
    private val viewModel: SettingsViewModel by inject()
    private lateinit var auth: FirebaseAuth
    private lateinit var firstName:String
    private lateinit var lastName:String
    private lateinit var nickname:String

    private lateinit var alertDialogBinding: RegisterDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)

        setHasOptionsMenu(true)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        changeFirstLastNick()
        bindToLiveData()
        viewModel.getUsers()
        deleteUser()

        alert()


        binding.btnUpload.setOnClickListener {
            viewModel.addPhoto(Uri.fromFile(file))
            Snackbar.make(requireView()," Bild wurde Hochgeladen",Snackbar.LENGTH_SHORT).show()
            viewModel.getUsers()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_group_exit,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when{
            R.id.logout == id -> {

                auth.signOut()
                findNavController().navigate(SettingsFragmentDirections.actionSettingsViewToLogin())

            }

        }
        return true
    }


    fun alert() {
        binding.ivProfileImage.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireContext())

            Snackbar.make(requireView(), "saasd", Snackbar.LENGTH_SHORT).show()
            dialogBuilder.setMessage("Willst du das Bild ändern oder Löschen?")
                .setCancelable(true)
                .setPositiveButton("EDIT", DialogInterface.OnClickListener { dialog, id ->
                    uploadClickAction()
                    viewModel.getUsers()

                })
                .setNegativeButton("DELETE", DialogInterface.OnClickListener { dialog, id ->
                    deleteUserImage()
                    viewModel.getUsers()
                    Snackbar.make(requireView()," Das Bild wurde gelöscht",Snackbar.LENGTH_SHORT).show()

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


 /*   private fun changePassword(){
        binding.tvPassword.setOnClickListener{
            

        }
    }*/

    private fun changeFirstLastNick() {
        binding.tvUserName.setOnClickListener {

        }
    }
    



    @ExperimentalCoroutinesApi
    fun bindToLiveData() {
        viewModel.getMyGroupMembers().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding.tvLastName.text =Editable.Factory.getInstance().newEditable(it.firstname)
            binding.tvUserName.text = Editable.Factory.getInstance().newEditable(it.lastname)
            binding.tvNickName.text = Editable.Factory.getInstance().newEditable(it.nickname)
            Glide.with(requireContext())
                .load(it.image?.url)
                .placeholder(R.drawable.ic_baseline_image_48)
                .into(binding.ivProfileImage)
        })
        binding.btnSaveChanges.setOnClickListener {
            viewModel.updateNickName(binding.tvNickName.text.toString(),binding.tvUserName.text.toString(),binding.tvLastName.text.toString())
            viewModel.getUsers()
            Snackbar.make(requireView(),"Profileinstellungen wurden geändert",Snackbar.LENGTH_SHORT).show()
        }

    }

    @ExperimentalCoroutinesApi
    fun deleteUser() {
        binding.deleteUser.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireContext())

            dialogBuilder.setMessage("Möchst du wirklich dein Account löschen?")
                .setCancelable(true)
                .setPositiveButton("CANCEL", DialogInterface.OnClickListener { dialog, id ->
                })
                // negative button text and action
                .setNegativeButton("DELETE", DialogInterface.OnClickListener { dialog, id ->
                    viewModel.deleteUser()
/*
                    view?.findNavController()?.navigate(SettingsFragmentDirections.actionSettingsViewToLogin())
*/
                    dialog.cancel()
                    dialog.dismiss()

                })


            val alert = dialogBuilder.create()
            alert.setTitle("AlertDialogExample")
            alert.cancel()
            alert.show()
        }

    }

}