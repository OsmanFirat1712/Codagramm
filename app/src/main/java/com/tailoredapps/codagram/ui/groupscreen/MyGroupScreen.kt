package com.tailoredapps.codagram.ui.groupscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tailoredapps.codagram.databinding.FragmentThirdBinding
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject

class MyGroupScreen: Fragment() {
    private lateinit var bindingGroup: FragmentThirdBinding
    private val viewModel: MyGroupScreenViewMode by inject()
    private val myGroupsAdapter:GroupAdapter by inject()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingGroup.myGroups.apply {
            adapter = this@MyGroupScreen.myGroupsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        myGroupsAdapter.notifyDataSetChanged()
        bindgetmyGroupToLiveData()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingGroup = FragmentThirdBinding.inflate(layoutInflater,container, false)
        return bindingGroup.root
    }

    fun bindgetmyGroupToLiveData() {
        viewModel.getMyGroups().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myGroupsAdapter.submitList(it)
        })
    }
}