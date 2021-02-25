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
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.Group
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentSecondBinding
import com.tailoredapps.codagram.remoteModels.GroupList
import com.tailoredapps.codagram.ui.groupscreen.GroupDetailsAdapter
import com.tailoredapps.codagram.ui.groupscreen.GroupFragmentDirections
import com.tailoredapps.codagram.ui.groupscreen.GroupViewModel
import com.tailoredapps.codagram.ui.groupscreen.SearchAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import java.lang.Exception
import java.util.*


class NewStoryFragment : Fragment() {
    private val viewModel: NewStoryViewModel by inject()
    private val groupDetailsAdapter: GroupDetailsAdapter by inject()

    private val searchAdapter: SearchAdapter by inject ()
    private lateinit var binding: FragmentSecondBinding
    lateinit var imageData: Uri
    val REQUEST_IMAGE_CAPTURE = 2
    private lateinit var getSpinnerItem:String
    private lateinit var adapter: SpinnerAdapter


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

        binding.auto.setOnClickListener {
            searchKey()
        }

        binding.spinner1.onItemSelectedListener =object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(requireContext(), "You Selected .toString()}", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Snackbar.make(requireView(), "You Selected ${adapterView?.getItemAtPosition(position).toString()}", Snackbar.LENGTH_LONG).show()
                getSpinnerItem = adapterView?.getItemAtPosition(position).toString()
                viewModel.searchUser(getSpinnerItem)
                Log.e("spinner", getSpinnerItem)
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

    @ExperimentalCoroutinesApi
    private fun searchKey() {
        val input = binding.auto.text.toString()
        viewModel.searchUser(input)
    }

    @ExperimentalCoroutinesApi
    private fun postButtonAction(){
        binding.btnPost.setOnClickListener {
            val bundle = bundleOf(
                "name" to getSpinnerItem,
            )
            val description = binding.etDescription.text.toString()
            viewModel.post(description,getSpinnerItem)

            it.findNavController().navigate(NewStoryFragmentDirections.actionSecondViewToFirstView())
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


