package com.tailoredapps.codagram.ui.groupscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tailoredapps.codagram.databinding.FragmentGroupDetailsBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import timber.log.Timber


class GroupDetailsFragment : Fragment() {

    private lateinit var binding: FragmentGroupDetailsBinding
    @ExperimentalCoroutinesApi
    private val viewModel: GroupDetailsViewModel by inject()
    private val adapter: SearchAdapter by inject()
    private val groupAdapter: GroupDetailsAdapter by inject()


    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for his fragment
        binding = FragmentGroupDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        binding.tvGroupTitle.text = args.groupId?.name
*/
        //getGroupName()
       val groupName = arguments?.getString("name")
       val groupId = arguments?.getString("id")
        //if post crashes, comment theses
        viewModel.getGroupById(groupId.toString())
        bindToLiveData()
        bindgetmyGroupToLiveData()

        binding.tvGroupTitle.text = groupName.toString()

        binding.searchEditRecyclerview.apply {
            adapter = this@GroupDetailsFragment.adapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        binding.membersRecyclerview.apply {
            adapter = this@GroupDetailsFragment.groupAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        binding.auto.setOnClickListener {
            searchKey()
        }
    }



    @ExperimentalCoroutinesApi
    fun bindToLiveData() {
        viewModel.getSearchedUser().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
        })

    }

    @ExperimentalCoroutinesApi
    private fun searchKey() {

        val input = binding.auto.text.toString()

        viewModel.searchUser(input)
        Timber.d(input)

    }



    @ExperimentalCoroutinesApi
    private fun bindgetmyGroupToLiveData() {
        viewModel.getMyGroupMembers().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            groupAdapter.submitList(it)

        })
    }
}

