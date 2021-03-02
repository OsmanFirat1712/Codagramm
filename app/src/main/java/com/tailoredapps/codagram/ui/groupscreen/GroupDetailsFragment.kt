package com.tailoredapps.codagram.ui.groupscreen

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
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
import java.io.File


class GroupDetailsFragment : Fragment() {

    private lateinit var binding: FragmentGroupDetailsBinding

    @ExperimentalCoroutinesApi
    private val viewModel: GroupDetailsViewModel by inject()
    private val adapter: SearchAdapter by inject()
    private val groupAdapter: GroupDetailsAdapter by inject()
    private lateinit var groupId: String
    private var creatorId: String? = null
    private lateinit var alertDialogBinding: RegisterDialogBinding
    private lateinit var file: File
    private lateinit var dialog: AlertDialog
    private lateinit var groupName: String
    private lateinit var image:String
    private lateinit var creatorName:String


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

        viewModel.getAllGroups()

/*
        binding.tvGroupTitle.text = args.groupId?.name
*/
        //getGroupName()

        bindToLiveData()
        bindgetmyGroupToLiveData()


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


        binding.inviteButton.setOnClickListener {
            viewModel.sendGroupInvites(groupId.toString())

        }
        createAlertDialog()

        alert()
        groupName = arguments?.getString("name").toString()
        groupId = arguments?.getString("id").toString()
        creatorId = arguments?.getString("creatorId")
        image  = arguments?.getString("imageUrl").toString()
        creatorName = arguments?.getString("creatorName").toString()
        //if post crashes, comment theses


        viewModel.getGroupById(groupId.toString())
        binding.tvGroupTitle.text = groupName.toString()
        binding.tvGroupCreatedBy.text = creatorName

        loadGroupImage()
    }


    @ExperimentalCoroutinesApi
    fun alert() {
        binding.imageView2.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireContext())

            Snackbar.make(requireView(), "saasd", Snackbar.LENGTH_SHORT).show()
            dialogBuilder.setMessage("Willst du das Bild ändern oder Löschen?")
                .setPositiveButton("EDIT", DialogInterface.OnClickListener { dialog, id ->
                    if (FirebaseAuth.getInstance().currentUser!!.uid == creatorId) {
                        uploadClickAction()

                    } else {
                        Snackbar.make(
                            requireView(),
                            "Du kannst nur als Ersteller der Gruppe das Foto bearbeiten",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                })
                // negative button text and action
                .setNegativeButton("DELETE", DialogInterface.OnClickListener { dialog, id ->
                    deleteGroupImage()
                    dialog.cancel()

                })

            val alert = dialogBuilder.create()
            alert.setTitle("AlertDialogExample")
            alert.show()
        }


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
        if (input.isEmpty()){
            Snackbar.make(requireView(),"Bitte einen User eingeben",Snackbar.LENGTH_SHORT).show()
        } else {
            viewModel.searchUser(input)
            Timber.d(input)

        }


    }


    @ExperimentalCoroutinesApi
    private fun bindgetmyGroupToLiveData() {
        viewModel.getMyGroupMembers().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            groupAdapter.submitList(it)
            groupAdapter.setUpListener(object : GroupDetailsAdapter.ItemRemoveClickListener {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onItemClicked(user: User) {
                    val dialogBuilder = AlertDialog.Builder(requireContext())
                    dialogBuilder.setMessage("Möchst den folgenden User ${user.firstname} aus der Gruppe entfernen")
                        .setCancelable(false)
                        .setPositiveButton("CANCEL", DialogInterface.OnClickListener { dialog, id ->
                        })
                        // negative button text and action
                        .setNegativeButton("DELETE", DialogInterface.OnClickListener { dialog, id ->
                            if (FirebaseAuth.getInstance().currentUser?.uid == creatorId) {
                                viewModel.deleteMember(groupId.toString(), user.id.toString())
                                Snackbar.make(
                                    requireView(),
                                    " User ${user.firstname} wurde aus der Gruppe entfernt",
                                    Snackbar.LENGTH_SHORT
                                ).show()

                            } else {
                                Snackbar.make(
                                    requireView(),
                                    "Du kannst nur als Ersteller der Gruppe Mitglieder entfernen",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }



                            viewModel.getGroupById(groupId.toString())
                            dialog.cancel()

                        })

                    val alert = dialogBuilder.create()
                    alert.setTitle("AlertDialogExample")
                    alert.show()


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
                if (FirebaseAuth.getInstance().currentUser!!.uid == creatorId) {
                    val firstNameFire = alertDialogBinding.editTextTextPersonName.text.toString()
                    viewModel.updateGroup(groupId.toString(), UpdateGroup(firstNameFire))

                    groupAdapter.currentList
                    groupAdapter.notifyDataSetChanged()
                } else {
                    Snackbar.make(
                        requireView(),
                        "Du kannst nur als Ersteller den Gruppennamen ändern",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                when {

                    alertDialogBinding.editTextTextPersonName.text.toString()
                        .isEmpty() -> Toast.makeText(
                        context,
                        "nick name can not be empty!",
                        Toast.LENGTH_LONG
                    ).show()

                    else -> {
                        /*    viewModel.getMyGroups()
                            viewModel.getGroupById(groupId.toString())*/
                    }
                }

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

    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalCoroutinesApi
    fun getDelete() {


        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setMessage("Möchst du die folgende Gruppe  ${groupName} wirklich löschen?")
            .setCancelable(false)
            .setPositiveButton("CANCEL", DialogInterface.OnClickListener { dialog, id ->
            })
            // negative button text and action
            .setNegativeButton("DELETE", DialogInterface.OnClickListener { dialog, id ->
                if (FirebaseAuth.getInstance().currentUser!!.uid == creatorId) {
                    viewModel.deleteGroup(groupId.toString())
                    view?.findNavController()?.popBackStack()

                } else {
                    viewModel.exitGroup(groupId.toString())
                    Snackbar.make(
                        requireView(),
                        "Möchstes du wirklich die Gruppe $groupName verlassen?",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    dialogBuilder.setMessage("Möchst du die folgende Gruppe  ${groupName} wirklich löschen?")
                    view?.findNavController()?.popBackStack()
                }


                dialog.cancel()

            })

        val alert = dialogBuilder.create()
        alert.setTitle("AlertDialogExample")
        alert.show()

    }

    @RequiresApi(Build.VERSION_CODES.N)
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

    @ExperimentalCoroutinesApi
    fun deleteGroupImage() {
        viewModel.deleteGroupImage(groupId.toString())
    }


    private fun listImages() {
        var i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
    }

    private fun updateGroupImage() {
        viewModel.updateGroupImage(groupId.toString(), Uri.fromFile(file))
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            binding.imageView2.setImageURI(fileUri)

            //You can get File object from intent
            file = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath: String = ImagePicker.getFilePath(data)!!
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Snackbar.make(requireView(), ImagePicker.getError(data), Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(requireView(), "Task Cancelled", Snackbar.LENGTH_SHORT).show()
        }
    }


    private fun uploadClickAction() {
        Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_LONG).show()
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()
        /*if (ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),
                Array(1){Manifest.permission.READ_EXTERNAL_STORAGE},121)
        }*/
        listImages()
    }

    fun loadGroupImage(){
        Glide.with(requireContext())
            .load(image)
            .into(binding.imageView2)
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





