package com.tailoredapps.codagram.ui.settings

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentGroupBinding
import com.tailoredapps.codagram.databinding.FragmentSettingsBinding
import com.tailoredapps.codagram.databinding.RegisterDialogBinding
import com.tailoredapps.codagram.models.UpdateGroup
import com.tailoredapps.codagram.ui.groupscreen.GroupViewModel
import org.koin.android.ext.android.inject

class SettingsFragment:Fragment() {

    private lateinit var binding:FragmentSettingsBinding
    private val viewModel: SettingsViewModel by inject()

    private lateinit var alertDialogBinding: RegisterDialogBinding

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


        changeFirstLastNick()

    }

    private fun changePassword(){
        binding.tvPassword.setOnClickListener{
            

        }
    }

    private fun changeFirstLastNick() {
        binding.tvUserName.setOnClickListener {

        }
    }
    
    private fun changeEmail(){
        binding.tvEmail.setOnClickListener {

        }
    }
}