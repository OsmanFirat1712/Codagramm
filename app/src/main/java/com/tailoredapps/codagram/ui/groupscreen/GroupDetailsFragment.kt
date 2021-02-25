package com.tailoredapps.codagram.ui.groupscreen

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentGroupDetailsBinding
import com.tailoredapps.codagram.databinding.RegisterDialogBinding
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.UpdateGroup
import com.tailoredapps.codagram.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import timber.log.Timber


class GroupDetailsFragment : Fragment() {

    private lateinit var binding: FragmentGroupDetailsBinding

    @ExperimentalCoroutinesApi
    private val viewModel: GroupDetailsViewModel by inject()
    private val adapter: SearchAdapter by inject()
    private val groupAdapter: GroupDetailsAdapter by inject()
    private var groupId: String? = null
    private var creatorId:String? = null
    private lateinit var alertDialogBinding: RegisterDialogBinding






    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for his fragment
        binding = FragmentGroupDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


/*
        binding.tvGroupTitle.text = args.groupId?.name
*/
        //getGroupName()
        val groupName = arguments?.getString("name")
         groupId = arguments?.getString("id")
        creatorId = arguments?.getString("creatorId")
        //if post crashes, comment theses

        viewModel.getGroupById(groupId.toString())
        bindToLiveData()
        bindgetmyGroupToLiveData()

        binding.tvGroupTitle.text = groupName.toString()

        binding.searchEditRecyclerview.apply {
            adapter = this@GroupDetailsFragment.adapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        binding.membersRecyclerview.apply {
            adapter = this@GroupDetailsFragment.groupAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        binding.auto.setOnClickListener {
            searchKey()
        }

        createAlertDialog()
    }


    @ExperimentalCoroutinesApi
    fun bindToLiveData() {
        viewModel.getSearchedUser().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
        })

    }

    @ExperimentalCoroutinesApi
    private fun searchKey() {

        val input = binding.auto.text.toString()

        viewModel.searchUser(input)
        Timber.d(input)

    }


    @ExperimentalCoroutinesApi
    private fun bindgetmyGroupToLiveData() {
        viewModel.getMyGroupMembers().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            groupAdapter.submitList(it)

            groupAdapter.setUpListener(object : GroupDetailsAdapter.ItemRemoveClickListener {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onItemClicked(user: User) {

                    viewModel.deleteMember(groupId.toString(), user.id.toString())
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("löschen")
                        .setNeutralButton("cancel") { dialogInterface, i ->
                        }
                        .setNegativeButton("löschen") { dialogInterface, i ->
                            viewModel.deleteMember(user.id.toString(), groupId.toString())
                            viewModel.getGroupById(groupId.toString())
                            groupAdapter.currentList
                            groupAdapter.notifyDataSetChanged()
                            groupAdapter.submitList(it)
                        }
                }

            })
        })

    }

    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalCoroutinesApi
    fun createAlertDialog() {
        binding.tvGroupTitle.setOnClickListener {
/*
            val view: View = layoutInflater.inflate(R.layout.register_dialog, alertDialogBinding.root)
*/
            alertDialogBinding = RegisterDialogBinding.inflate(layoutInflater)
            val alertDialog = MaterialAlertDialogBuilder(requireContext())
            alertDialog.setView(alertDialogBinding.root)
            alertDialog.create()
            val alert = alertDialog.show()

            alertDialogBinding.button.setOnClickListener {
                val firstNameFire = alertDialogBinding.editTextTextPersonName.text.toString()
                viewModel.updateGroup(groupId.toString(), UpdateGroup(firstNameFire))
                viewModel.getMyGroups()
                groupAdapter.currentList
                groupAdapter.notifyDataSetChanged()

                when {

                    alertDialogBinding.editTextTextPersonName.text.toString().isEmpty() -> Toast.makeText(
                        context,
                        "nick name can not be empty!",
                        Toast.LENGTH_LONG
                    ).show()

                    else -> {
                    /*    viewModel.getMyGroups()
                        viewModel.getGroupById(groupId.toString())*/
                    } }

            }
            alertDialogBinding.button2.setOnClickListener {
                Log.e("message", "clicked")
                alert.dismiss()
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_group_delete, menu)
        val down = menu.findItem(R.id.delete)


    }
    @ExperimentalCoroutinesApi
    fun getDelete(){
        if (FirebaseAuth.getInstance().currentUser!!.uid == creatorId){
            viewModel.deleteGroup(groupId.toString())


        }else {
            viewModel.exitGroup(groupId.toString())
            val toast = Toast.makeText(requireContext(), "Task Saved", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    @ExperimentalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                getDelete()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }





    }

/*    @ExperimentalCoroutinesApi
    fun updateGroup(){
        MaterialAlertDialogBuilder(requireContext())
        binding2 = RegisterDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
            .setView(R.layout.register_dialog)
            .setTitle("löschen")
            .setNeutralButton("cancel") { dialogInterface, i ->

            viewModel.updateGroup()}
            .setNegativeButton("löschen") { dialogInterface, i ->
            }
    }*/
/*
    fun updateGroup(){

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.register_dialog, null)

        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
            .setTitle("Login Form")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        //login button click of custom layout

        mDialogView.button.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            //get text from EditTexts of custom layout
            val name = mDialogView.edit.text.toString()
            //set the input text in TextView
            mainInfoTv.setText("Name:"+ name +"\nEmail: "+ email +"\nPassword: "+ password)
        }
        //cancel button click of custom layout
        mDialogView.dialogCancelBtn.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }*/

}



      /*  item.setOnMenuItemClickListener {
            if (item.itemId == R.id.delete) {
                if (groupId != null) {
                    viewModel.deleteGroup(groupId)
                }
            }

            return@setOnMenuItemClickListener super.onOptionsItemSelected(item)
        }*/
/*
        return super.onOptionsItemSelected(item)

*/





