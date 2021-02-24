package com.tailoredapps.codagram.ui.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentGroupBinding
import com.tailoredapps.codagram.databinding.FragmentSettingsBinding
import com.tailoredapps.codagram.ui.groupscreen.GroupViewModel
import org.koin.android.ext.android.inject

class SettingsFragment:Fragment() {

    private lateinit var binding:FragmentSettingsBinding
    private val viewModel: SettingsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changePassword()
        changeFirstName()
        changeLastName()
        changeNickName()
        changeEmail()

    }

    private fun changePassword(){
        binding.tvPassword.setOnClickListener{
            

        }
    }

    private fun changeFirstName(){
        binding.tvUserName.setOnClickListener {

        }
    }

    private fun changeLastName(){
        binding.tvLastName.setOnClickListener {

        }
    }

    private fun changeNickName(){
        binding.tvNickName.setOnClickListener {

        }
    }

    private fun changeEmail(){
        binding.tvEmail.setOnClickListener {

        }
    }
}