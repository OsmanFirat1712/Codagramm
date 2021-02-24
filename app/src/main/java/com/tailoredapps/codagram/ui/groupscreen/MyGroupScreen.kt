package com.tailoredapps.codagram.ui.groupscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tailoredapps.codagram.databinding.FragmentThirdBinding
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.GroupInvite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import java.util.*

class MyGroupScreen() : Fragment()  {
    private lateinit var bindingGroup: FragmentThirdBinding

    @ExperimentalCoroutinesApi
    private val viewModel: MyGroupScreenViewMode by inject()
    private val myGroupsAdapter: GroupAdapter by inject()
    private val myGroupInviteAdapter: GroupInviteAdapter by inject()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingGroup = FragmentThirdBinding.inflate(layoutInflater, container, false)
        return bindingGroup.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingGroup.myGroups.apply {
            adapter = this@MyGroupScreen.myGroupsAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)

        }

        bindingGroup.membersRecyclerview.apply {
            adapter = this@MyGroupScreen.myGroupInviteAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }


        bindingGroup.fabAdd.setOnClickListener {
            it.findNavController().navigate(MyGroupScreenDirections.actionFirstViewToGroupscreen())

        }
        respond()
        bindgetMyInvites()

        bindgetmyGroupToLiveData()
        viewModel.getInvites()
    }


    @ExperimentalCoroutinesApi
    private fun bindgetmyGroupToLiveData() {
        viewModel.getMyGroups().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myGroupsAdapter.submitList(it)

        })

    }


    @ExperimentalCoroutinesApi
    private fun bindgetMyInvites() {
        viewModel.getMyInvites().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myGroupInviteAdapter.submitList(it)

            myGroupInviteAdapter.setUpListener(object : GroupInviteAdapter.ItemCLickedListener {
                override fun onItemClicked(accept: Boolean,id:String,) {

                    viewModel.answerInvites(id,accept)
                    viewModel.getInvites()
                    myGroupInviteAdapter.currentList
                    myGroupInviteAdapter.notifyDataSetChanged()
                    myGroupInviteAdapter.submitList(it)
                }

            })

        })


    }



    @ExperimentalCoroutinesApi
    private fun respond() {
        viewModel.getSearchedUser().observe(viewLifecycleOwner, androidx.lifecycle.Observer {



        })

    }

    }


/*   @ExperimentalCoroutinesApi
    override fun replyInvite(accept: Group) {
        viewModel.getInvites(accept)
        TODO("Not yet implemented")
    }*/



