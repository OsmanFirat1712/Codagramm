package com.tailoredapps.codagram.ui.newStoryScreen

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.icu.number.NumberFormatter.with
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.Group
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentSecondBinding
import com.tailoredapps.codagram.remoteModels.GroupList
import com.tailoredapps.codagram.ui.groupscreen.GroupViewModel
import org.koin.android.ext.android.inject
import java.lang.Exception


class NewStoryFragment : Fragment() {
    private val viewModel: NewStoryViewModel by inject()
    private lateinit var binding: FragmentSecondBinding
    lateinit var imageData: Uri
    val REQUEST_IMAGE_CAPTURE = 2
    private lateinit var getSpinnerItem:String



    private val spinnerAdapter:SpinnerAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SpinnerAdapter(requireContext(),createGroupModelList())
        binding.spinner1.adapter = adapter

        //Click listener methods
        uploadClickAction()
        postButtonAction()
        getGroups()

    }

    private fun listImages(){
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "Choose Picture"), REQUEST_IMAGE_CAPTURE)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {

            imageData = data.data!!

            try {

                if (imageData != null) {
                    val source =
                        ImageDecoder.createSource(requireActivity().contentResolver, imageData!!)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    binding.tvUpload.setImageBitmap(bitmap)
                } else {
                    var bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageData)
                    val test =binding.tvUpload.setImageBitmap(bitmap).toString()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

    }

    private fun uploadClickAction(){
        binding.tvUpload.setOnClickListener {
            Toast.makeText(requireContext(),"Clicked",Toast.LENGTH_LONG).show()
            if (ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(),
                    Array(1){Manifest.permission.READ_EXTERNAL_STORAGE},121)
            }
            listImages()
        }
    }

    private fun postButtonAction(){
        binding.btnPost.setOnClickListener {
            val description = binding.etDescription.text.toString()
            //getSpinnerItem
            //image
        }
    }

    private fun getGroups(){
        val test = viewModel.getGroups().toString()
        Log.e("groups",test)
    }

    private fun createGroupModelList():ArrayList<GroupList>{

        val test = viewModel.getGroups()
        val list = ArrayList<GroupList>()
        for (i in 0 until 20){
            list.add(GroupList(test))
        }

        return list
    }


}