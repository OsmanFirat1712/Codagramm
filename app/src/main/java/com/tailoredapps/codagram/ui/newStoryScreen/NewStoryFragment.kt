package com.tailoredapps.codagram.ui.newStoryScreen

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.tailoredapps.codagram.databinding.FragmentSecondBinding
import com.tailoredapps.codagram.ui.groupscreen.GroupDetailsAdapter
import com.tailoredapps.codagram.ui.groupscreen.SearchAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.net.URI
import java.util.*


class NewStoryFragment : Fragment() {
    private val viewModel: NewStoryViewModel by inject()
    private val groupDetailsAdapter: GroupDetailsAdapter by inject()

    private val searchAdapter: SearchAdapter by inject ()
    private lateinit var binding: FragmentSecondBinding
    lateinit var imageData:Uri
            lateinit var file: File

    val REQUEST_IMAGE_CAPTURE = 2
    private lateinit var getSpinnerItem:String
    private lateinit var downloadUrl: String
    private lateinit var adapter: SpinnerAdapter
    private lateinit var image:Uri
    private lateinit var juri:URI


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalCoroutinesApi
    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SpinnerAdapter(requireContext(), EmptyArray())
        binding.spinner1.adapter = adapter

           binding.searchResult.apply {
            adapter = this@NewStoryFragment.searchAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

       /* binding.searchResult.apply {
            adapter = this@NewStoryFragment.groupDetailsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
*/

        binding.spinner1.onItemSelectedListener =object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(requireContext(), "You Selected .toString()}", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Snackbar.make(
                    requireView(),
                    "You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                    Snackbar.LENGTH_LONG
                ).show()
                getSpinnerItem = adapterView?.getItemAtPosition(position).toString()
                viewModel.searchUser(getSpinnerItem)
                    Timber.e(getSpinnerItem)
            }
        }
        bindToLiveData()
        test()
        uploadClickAction()
        postButtonAction()
        getGroups()
        bindToLiveData2()
    }

    private fun listImages(){
        var i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
    }

   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {


            imageData = data.data!!
             file = File(imageData.path.toString())
          image =  Uri.fromFile(file)
            //var mediaStore = MediaStore.Images.Media.getContentUri("Choose Picture",21)
            var bitmap = MediaStore.Images.Media.getBitmap(
                requireActivity().contentResolver,
                imageData
            )
           val image = binding.tvUpload.setImageBitmap(bitmap).toString()
            val auri = URI(imageData.toString())
             juri = URI(auri.toString())

            downloadUrl = imageData.toString()
            Log.e("uri", downloadUrl.toString())
            Log.d("bitmap", "$image")
            Log.e("imagedata", file.toString())

        }
        super.onActivityResult(requestCode, resultCode, data)

    }*/



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
           binding.tvUpload.setImageURI(fileUri)

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
/*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when(resultCode){
        Activity.RESULT_OK-> {
            val fileUri = data?.data
            Image(fileUri.toString())

        }
    }
}*/


    private fun uploadClickAction(){
        binding.tvUpload.setOnClickListener {
            Toast.makeText(requireContext(),"Clicked",Toast.LENGTH_LONG).show()
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
    }



    @ExperimentalCoroutinesApi
    private fun postButtonAction(){
        binding.btnPost.setOnClickListener {
            val description = binding.etDescription.text.toString()

            viewModel.post(description,getSpinnerItem,Uri.fromFile(file))
            view?.findNavController()?.navigate(NewStoryFragmentDirections.actionSecondViewToFirstView())
            viewModel.getGroups()
            }


        }

    @ExperimentalCoroutinesApi
    private fun spinnerSelectedItem(){
        binding.spinner1.onItemSelectedListener =object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(requireContext(),"You Selected ${adapterView?.getItemAtPosition(position).toString()}",Toast.LENGTH_LONG).show()
                getSpinnerItem = adapterView?.getItemAtPosition(position).toString()
                Log.e("spinner",getSpinnerItem)
            }
        }
    }

    private fun getGroups(){
        val test = viewModel.getGroups()

    }

    private fun EmptyArray(): ArrayList<String> {

        val list = ArrayList<String>()
        for (i in 0 until 20){
        }
        return list
    }

    @ExperimentalCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.N)
    fun test(){
        viewModel.getMyGroups().observe(viewLifecycleOwner,androidx.lifecycle.Observer{
                it.forEach {
                    //val groupName = it.name
                    val groupId = it.id
                    //adapter.data.add(groupName)
                    adapter.data.add(groupId)
                    adapter.notifyDataSetChanged()
                }
        })
    }

    @ExperimentalCoroutinesApi
    fun bindToLiveData() {
        viewModel.getSearchedUser().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            searchAdapter.submitList(it)
        })
    }

    @ExperimentalCoroutinesApi
    fun bindToLiveData2() {
        viewModel.getMyGroupMembers().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            groupDetailsAdapter.submitList(it)
        })
    }
    }

    /*
        val intent = Intent()
        // Show only images, no videos or anything else
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activity.startActivityForResult(intent, IMAGE_CHOOSER)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent ? ) {
        if (resultCode != RESULT_OK) { return }
        if (requestCode == IMAGE_CHOOSER &&
            data != null &&
            data.getData() != null) {
            //We cannot access this Uri directly in android 10
            selectedImageUri = data.getData()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    fun getBitmap(context: Context, imageUri: Uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    context.contentResolver,
                    imageUri))

        } else {

            context
                .contentResolver
                .openInputStream(imageUri) ?
            .use {
                    inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }

        }
    }
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent ? ) {
        if (resultCode != RESULT_OK) { return }

        if (requestCode == IMAGE_CHOOSER &&
            data != null &&
            data.getData() != null) {

            //We cannot access this Uri directly in android 10
            selectedImageUri = data.getData()

            //Later we will use this bitmap to create the File.
            val selectedBitmap: Bitmap = getBitmap(this, selectedImageUri)

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun convertBitmaptoFile(destinationFile: File, bitmap: Bitmap) {
        //create a file to write bitmap data
        destinationFile.createNewFile()
        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)
        val bitmapData = bos.toByteArray()
        //write the bytes in file
        val fos = FileOutputStream(destinationFile)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
    }
    @JvmName("onActivityResult1")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent ? ) {
        if (resultCode != RESULT_OK) { return }

        if (requestCode == IMAGE_CHOOSER &&
            data != null &&
            data.getData() != null) {

            //We cannot access this Uri directly in android 10
            selectedImageUri = data.getData()

            //Later we will use this bitmap to create the File.
            val selectedBitmap: Bitmap = getBitmap(requireContext(), selectedImageUri)

            *//*We can access getExternalFileDir() without asking any storage permission.*//*
            val selectedImgFile = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                getTimestamp().toString() + "_selectedImg.jpg")

            convertBitmaptoFile(selectedImgFile, selectedBitmap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
*/