package com.tailoredapps.codagram.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentFirstBinding
import com.tailoredapps.codagram.ui.homeFeedScreen.HomeFeedViewModel
import com.tailoredapps.codagram.ui.newStoryScreen.NewStoryViewModel
import org.koin.android.ext.android.inject
import kotlin.math.log

class HomeFeedScreen : Fragment() {

    private val adapter: HomeFeedAdapter by inject()
    private val viewModel: HomeFeedViewModel by inject()
    private val navController by lazy(::findNavController)  //Method referencing
    private lateinit var binding: FragmentFirstBinding
    var groupId: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeFeedScreen.apply {
            adapter = this@HomeFeedScreen.adapter
        }

        test()
        groupId = arguments?.getString("spinner")
        viewModel.getStoryPost(groupId.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_first_view, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    fun test(){
        //val hello = viewModel.getStoryPost()
        viewModel.getMyPost().observe(viewLifecycleOwner,androidx.lifecycle.Observer {
            adapter.submitList(it)
            it.forEach {

            }

        })
    }





}
