package com.tailoredapps.codagram.ui.thirdView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentThirdBinding
import com.tailoredapps.codagram.ui.loginscreen.LoginFragmentDirections

class ThirdScreen : Fragment() {

    private lateinit var binding: FragmentThirdBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAdd.setOnClickListener {
            it.findNavController().navigate(ThirdScreenDirections.actionFirstViewToGroupscreen())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


}