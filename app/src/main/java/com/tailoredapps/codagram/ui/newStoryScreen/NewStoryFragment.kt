package com.tailoredapps.codagram.ui.newStoryScreen

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.tailoredapps.codagram.databinding.FragmentSecondBinding


class NewStoryFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    lateinit var imageData: Uri
    val REQUEST_IMAGE_CAPTURE = 2
    private lateinit var getSpinnerItem:String

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

        binding.spinner1.onItemSelectedListener =object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(requireContext(),"You Selected ${adapterView?.getItemAtPosition(position).toString()}",Toast.LENGTH_LONG).show()
                getSpinnerItem = adapterView?.getItemAtPosition(position).toString()
                Log.e("spinner",getSpinnerItem)
            }
        }


        //Click listener methods
        uploadClickAction()
        postButtonAction()

    }

    private fun listImages(){
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "Choose Picture"), REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null){
            imageData = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imageData)
            val image = binding.tvUpload.setImageBitmap(bitmap)
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


}