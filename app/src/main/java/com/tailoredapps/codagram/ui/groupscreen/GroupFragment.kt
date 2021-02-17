package com.tailoredapps.codagram.ui.groupscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.ActivityMainBinding.inflate
import com.tailoredapps.codagram.databinding.FragmentGroupBinding
import com.tailoredapps.codagram.databinding.LoginFragmentBinding
import com.tailoredapps.codagram.ui.loginscreen.LoginViewModel
import org.koin.android.ext.android.inject

class GroupFragment : Fragment() {

    private lateinit var binding: FragmentGroupBinding
    private  val viewModel: GroupViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewAction()
    }

    private fun searchViewAction(){
        binding.svSearchBar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(location: String?): Boolean {
                view?.let { searchKey(it) }
                binding.svSearchBar.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                binding.svSearchBar.query.isNullOrEmpty()
                return false
            }
        })
    }

    fun searchKey(view: View){
        lateinit var getUser: String

        getUser = binding.svSearchBar.query.toString()
    }

}