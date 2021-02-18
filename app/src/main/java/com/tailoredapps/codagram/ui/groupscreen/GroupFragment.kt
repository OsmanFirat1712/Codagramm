package com.tailoredapps.codagram.ui.groupscreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.ActivityMainBinding.inflate
import com.tailoredapps.codagram.databinding.FragmentGroupBinding
import com.tailoredapps.codagram.databinding.LoginFragmentBinding
import com.tailoredapps.codagram.ui.loginscreen.LoginViewModel
import org.koin.android.ext.android.inject

class GroupFragment : Fragment() {

    private lateinit var binding: FragmentGroupBinding
    private val viewModel: GroupViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchKey()
        createButtonAction()
    }

    fun searchKey() {
        val names = arrayOf("test", "test2", "test", "test2", "test", "test2", "test", "test2",)
        val adapter2: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
        binding.auto.setAdapter(adapter2)


    }

    fun createButtonAction() {
        binding.btnCreateGroup.setOnClickListener {
            val nameGroup = binding.etCreateGroup.text.toString()
            //getSelectedlist
            //apiaddgroup must be here
        }
    }
}