package com.example.navigationgraph.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.navigationgraph.databinding.FragmentSecondBinding

class SecondScreen : Fragment() {
    lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAdd.setOnClickListener {
            it.findNavController().navigate(SecondScreenDirections.actionSecondToThird())
        }

        binding.btnToDetails.setOnClickListener {
            it.findNavController().navigate(SecondScreenDirections.actionSecondToDetails("Successful"))
        }
    }
}