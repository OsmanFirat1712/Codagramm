package com.tailoredapps.codagram.ui.groupscreen

import android.os.Bundle
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

        //binding.tvGroupTitle.setText()
    }


}