package com.tailoredapps.codagram.ui.homeFeedScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isNotEmpty
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.CommentScreenItemsBinding
import com.tailoredapps.codagram.databinding.FragmentCommentScreenBinding
import com.tailoredapps.codagram.models.Comment
import com.tailoredapps.codagram.models.CommentBody
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject


class CommentScreenFragment : Fragment() {

    private val viewModel:CommentScreenViewModel by inject()
    private val  commentScreenAdapter: CommentScreenAdapter by inject()
    private var postId: String? = null
    lateinit var commentAdapterBinding:CommentScreenItemsBinding

    var postIds: String? = null
    var postImage:String ? = null
    var image:String? = null

    private lateinit var binding:FragmentCommentScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postIds = arguments?.getString("name")
        postImage = arguments?.getString("image")



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCommentScreenBinding.inflate(inflater, container, false)
        commentAdapterBinding = CommentScreenItemsBinding.inflate(inflater,container,false)
        return binding.root



    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCommentsRecyclerview.apply {
            adapter = this@CommentScreenFragment.commentScreenAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)


        }

        bindToLiveData()
        getTest()
        viewModel.getCommentPost(postIds.toString())
        PostComment()




    }

    fun getTest(){
        viewModel.getPostById(postIds.toString())
    }

    @ExperimentalCoroutinesApi
    fun bindToLiveData() {
        viewModel.getMyComments().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            commentScreenAdapter.submitList(it)
            commentScreenAdapter.notifyDataSetChanged()
            it.forEach {
                val image1 = it.user?.image?.url
                Glide.with(requireContext())
                    .load(image1)
                    .placeholder(R.drawable.person)
                    .into(binding.ivUserCommentPageImage)
            }

        })
        commentScreenAdapter.setUpListener(object :CommentScreenAdapter.ItemRemove2ClickListener{
            override fun onItemClicked(comment: Comment) {
                viewModel.deleteComment(postIds.toString(),comment.id)
/*                    viewModel.getPostById(null.toString())*/
            }
        })
    }



    @ExperimentalCoroutinesApi
    fun PostComment(){
        binding.btnAdd.setOnClickListener{

            val test = binding.etWriteComment.text.toString()
            viewModel.postComment(postIds.toString(), CommentBody(test))
            viewModel.getPostById(postIds.toString())

        }

    }


    fun getPhoto(){
        viewModel.getMyPost().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
        })


    }

}