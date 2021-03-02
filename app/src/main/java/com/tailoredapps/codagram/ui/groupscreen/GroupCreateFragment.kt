package com.tailoredapps.codagram.ui.groupscreen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.tailoredapps.codagram.databinding.FragmentGroupBinding
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.io.File
import java.util.*
import java.util.Collections.emptyList


class GroupFragment : Fragment(), Get {

    private val adapter1: SearchAdapter by inject()
    private lateinit var binding: FragmentGroupBinding
    private val viewModel: GroupViewModel by inject()
    private var user: List<User> = emptyList()
    private var bundle: Bundle? = null
    var navController: NavController? = null
    private var  file:File? = null
    private lateinit var input:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchResult.apply {
            adapter = this@GroupFragment.adapter1
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        bindToLiveData()
        createButtonAction()
        uploadClickAction()


        binding.auto.setOnClickListener {
            searchKey()
        }

    }

    @ExperimentalCoroutinesApi
    private fun searchKey() {

         input = binding.auto.text.toString()
        if (input.isEmpty()){
            Snackbar.make(requireView(),"Bitte einen User eingeben",Snackbar.LENGTH_SHORT).show()
        } else {
            viewModel.searchUser(input)
            Timber.d(input)

        }


    }

    override fun onItemClicked(group: Group) {
        bundle = bundleOf(
            "names" to group.name,
            "ids" to group.id
        )

    }

    @ExperimentalCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.N)
    fun createButtonAction() {
        binding.btnCreateGroup.setOnClickListener { view ->

            val nameGroup = binding.etCreateGroup.text.toString()

            // (viewModel.createGroup(nameGroup))
            if (nameGroup.isEmpty()&& input.isEmpty()){
                Snackbar.make(requireView(),"Es muss eine Description gesetzt werden",Snackbar.LENGTH_SHORT).show()
            } else if (file != null){
                viewModel.createGroup(nameGroup, Uri.fromFile(file))
                view.findNavController()
                    .navigate(GroupFragmentDirections.actionGroupScreenToMyGroupScreen())
            } else{
                Snackbar.make(requireView(),"Bitte noch ein Gruppenfoto auswählen",Snackbar.LENGTH_SHORT).show()
            }



/*
            view?.findNavController()?.navigate(R.id.action_GroupScreen_to_GroupDetailScreens,bundle)
*/

        }
    }


    @ExperimentalCoroutinesApi
    fun bindToLiveData() {
        viewModel.getSearchedUser().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter1.submitList(it)
        })

    }

    private fun uploadClickAction(){
        binding.groupImage.setOnClickListener {
            Toast.makeText(requireContext(),"Clicked", Toast.LENGTH_LONG).show()
            ImagePicker.with(this)
                .crop()
                .compress(524)
                .maxResultSize(1080, 1080)
                .start()

            listImages()
        }
    }
    private fun listImages(){
        var i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            binding.groupImage.setImageURI(fileUri)

            //You can get File object from intent
            file = ImagePicker.getFile(data)!!
            val filePath:String = ImagePicker.getFilePath(data)!!
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Snackbar.make(requireView(), ImagePicker.getError(data), Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(requireView(), "Task Cancelled", Snackbar.LENGTH_SHORT).show()
        }
    }

}

interface Get {
    fun onItemClicked(group: Group)
}