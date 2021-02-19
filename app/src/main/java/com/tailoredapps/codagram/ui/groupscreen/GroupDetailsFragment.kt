package com.tailoredapps.codagram.ui.groupscreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentGroupBinding
import com.tailoredapps.codagram.databinding.FragmentGroupDetailsBinding
import org.koin.android.ext.android.inject


class GroupDetailsFragment : Fragment() {

    private lateinit var binding:FragmentGroupDetailsBinding
    private val viewModel : GroupDetailsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getGroupName()
    }


    fun getGroupName(){
        val result = viewModel.getGroupById("843d55cb-2f95-4099-9809-43828ff21836")
        Log.e("result",result.toString())
        viewModel.setTitle(binding.tvGroupTitle,result.toString())
    }
}