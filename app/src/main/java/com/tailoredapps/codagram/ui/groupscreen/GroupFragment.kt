package com.tailoredapps.codagram.ui.groupscreen

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentGroupBinding
import com.tailoredapps.codagram.databinding.FragmentThirdBinding
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.SearchResult
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.ui.loginscreen.LoginFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
import org.koin.android.ext.android.inject
import java.util.*
import java.util.Collections.emptyList


class GroupFragment : Fragment() {

    private val adapter1:SearchAdapter by inject()
    private lateinit var binding: FragmentGroupBinding
    private val viewModel: GroupViewModel by inject()
    private var user: List<User> = emptyList()



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



        binding.auto.setOnClickListener {
            searchKey() }

    }
    @ExperimentalCoroutinesApi
    private fun searchKey() {

        val input = binding.auto.text.toString()

            viewModel.searchUser(input)

    }


    @ExperimentalCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.N)
    fun createButtonAction() {
        binding.btnCreateGroup.setOnClickListener {

            val nameGroup = binding.etCreateGroup.text.toString()
            adapter1.currentList

            viewModel.createGroup(nameGroup)

            it.findNavController().navigate(GroupFragmentDirections.actionGroupScreenToGroupDetailScreen())

        }
    }



    @ExperimentalCoroutinesApi
    fun bindToLiveData() {
        viewModel.getSearchedUser().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter1.submitList(it)
        })

    }




}