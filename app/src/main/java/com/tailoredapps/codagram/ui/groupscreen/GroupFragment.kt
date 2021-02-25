package com.tailoredapps.codagram.ui.groupscreen

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tailoredapps.codagram.databinding.FragmentGroupBinding
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.*
import java.util.Collections.emptyList


class GroupFragment : Fragment(), Get {

    private val adapter1: SearchAdapter by inject()
    private lateinit var binding: FragmentGroupBinding
    private val viewModel: GroupViewModel by inject()
    private var user: List<User> = emptyList()
    private var bundle: Bundle? = null
    var navController: NavController? = null


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
            searchKey()
        }

    }

    @ExperimentalCoroutinesApi
    private fun searchKey() {

        val input = binding.auto.text.toString()

        viewModel.searchUser(input)
        Timber.d(input)

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
            viewModel.createGroup(nameGroup)
           // (viewModel.createGroup(nameGroup))


            view.findNavController()
                .navigate(GroupFragmentDirections.actionGroupScreenToGroupDetailScreens())
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


}

interface Get {
    fun onItemClicked(group: Group)
}