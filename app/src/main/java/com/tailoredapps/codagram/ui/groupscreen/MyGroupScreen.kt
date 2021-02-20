package com.tailoredapps.codagram.ui.groupscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tailoredapps.codagram.databinding.FragmentThirdBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject

class MyGroupScreen: Fragment() {
    private val myGroupsAdapter:GroupAdapter by inject()
    private lateinit var bindingGroup: FragmentThirdBinding
    private val viewModel: MyGroupScreenViewMode by inject()




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingGroup = FragmentThirdBinding.inflate(layoutInflater,container, false)
        return bindingGroup.root
    }
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingGroup.myGroups.apply {
            adapter = this@MyGroupScreen.myGroupsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        bindgetmyGroupToLiveData()

        bindingGroup.fabAdd.setOnClickListener {
            it.findNavController().navigate(MyGroupScreenDirections.actionFirstViewToGroupscreen())
        }

    }
    @ExperimentalCoroutinesApi
    fun bindgetmyGroupToLiveData() {
        viewModel.getMyGroups().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myGroupsAdapter.submitList(it)
        })
    }
}