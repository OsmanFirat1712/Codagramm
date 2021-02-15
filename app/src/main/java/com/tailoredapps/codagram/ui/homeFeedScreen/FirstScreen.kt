package com.tailoredapps.codagram.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentFirstBinding
import com.tailoredapps.codagram.ui.homeFeedScreen.HomeFeedViewModel
import org.koin.android.ext.android.inject

class FirstScreen : Fragment() {

    private val viewModel:HomeFeedViewModel by inject()
    private val adapter: HomeFeedAdapter by inject()

    private val navController by lazy(::findNavController)  //Method referencing
    private lateinit var binding: FragmentFirstBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            adapter = this@FirstScreen.adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_first_view, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

}
