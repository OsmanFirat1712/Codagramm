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
import timber.log.Timber
import java.util.*

class MyGroupScreen : Fragment() {
    private lateinit var bindingGroup: FragmentThirdBinding

    @ExperimentalCoroutinesApi
    private val viewModel: MyGroupScreenViewMode by inject()
    private val myGroupsAdapter: GroupAdapter by inject()


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
        bindingGroup.fabAdd.setOnClickListener {
            it.findNavController().navigate(MyGroupScreenDirections.actionFirstViewToGroupscreen())
        }
        bindgetmyGroupToLiveData()

    }

    @ExperimentalCoroutinesApi
    private fun bindgetmyGroupToLiveData() {
        viewModel.getMyGroups().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myGroupsAdapter.submitList(it)

            val str = "3db4cadc-769d-4af0-9336-71ea9c599fbf"
            val uuid: UUID = UUID.nameUUIDFromBytes(str.toByteArray())

            Timber.e("lalal + $uuid.toString())")
        })
    }


}