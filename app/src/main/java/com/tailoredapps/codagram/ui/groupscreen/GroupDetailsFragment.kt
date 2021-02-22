package com.tailoredapps.codagram.ui.groupscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.tailoredapps.codagram.databinding.FragmentGroupDetailsBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject


class GroupDetailsFragment : Fragment() {

    private lateinit var binding:FragmentGroupDetailsBinding
    private val viewModel : GroupDetailsViewModel by inject()
    val args: GroupDetailsFragmentArgs by navArgs()
    var countryName: String? = null
    private var countryFlag: String? = null

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        countryName = arguments?.getString("name")
        countryFlag = arguments?.getString("id")
        viewModel.getGroupById(countryFlag.toString())
        
         */

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for his fragment
        binding = FragmentGroupDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        binding.tvGroupTitle.text = args.groupId?.name
*/
        //getGroupName()
        binding.tvGroupTitle.text = countryName.toString()
    }


    fun getGroupName(){


    }

    fun invite(){

    }


}

