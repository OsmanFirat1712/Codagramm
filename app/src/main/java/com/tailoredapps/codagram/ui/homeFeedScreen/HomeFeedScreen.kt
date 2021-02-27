package com.tailoredapps.codagram.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentFirstBinding
import com.tailoredapps.codagram.ui.homeFeedScreen.HomeFeedAdapter
import com.tailoredapps.codagram.ui.homeFeedScreen.HomeFeedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import timber.log.Timber

class HomeFeedScreen : Fragment() {

    private val adapter: HomeFeedAdapter by inject()
    private val viewModel: HomeFeedViewModel by inject()
    private val navController by lazy(::findNavController)  //Method referencing
    private lateinit var binding: FragmentFirstBinding
    private lateinit var text:String
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
        test2()
        groupId = arguments?.getString("spinner")
        Timber.d("$groupId")
        viewModel.getStoryPost(null.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_first_view, menu)
        inflater.inflate(R.menu.menu_filter_groups, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    @ExperimentalCoroutinesApi
    fun test(){
        //val hello = viewModel.getStoryPost()
        viewModel.getMyPost().observe(viewLifecycleOwner,androidx.lifecycle.Observer {
            adapter.submitList(it)
            adapter.setUpListener(object : HomeFeedAdapter.ItemCLickedListener{
                override fun onItemClicked(like: Boolean, id: String) {

                    viewModel.likeComment(id,like)
                    adapter.notifyDataSetChanged()
                    adapter.submitList(it)
                }
            })


        })
    }

    fun test2(){

    }


}
