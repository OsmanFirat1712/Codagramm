package com.tailoredapps.codagram.ui.homeFeedScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.FragmentCommentScreenBinding
import com.tailoredapps.codagram.databinding.FragmentFirstBinding
import com.tailoredapps.codagram.models.Comment
import com.tailoredapps.codagram.models.CommentBody
import com.tailoredapps.codagram.models.Post
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject


class CommentScreenFragment : Fragment() {

    private val viewModel:CommentScreenViewModel by inject()
    private val  commentScreenAdapter: CommentScreenAdapter by inject()
    private var postId: String? = null

    var countryName: String? = null

    private lateinit var binding:FragmentCommentScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        countryName = arguments?.getString("name")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCommentScreenBinding.inflate(inflater, container, false)
        return binding.root

        postId = arguments?.getString("name")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCommentsRecyclerview.apply {
            adapter = this@CommentScreenFragment.commentScreenAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        bindToLiveData()

        getTest()
        buttonClickListener()

        viewModel.getCommentPost(countryName.toString())
    }

    fun getTest(){
        viewModel.getanasiniSikim(countryName.toString())
    }

    @ExperimentalCoroutinesApi
    fun bindToLiveData() {
        viewModel.getMyComments().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            commentScreenAdapter.submitList(it)

            commentScreenAdapter.setUpListener(object :CommentScreenAdapter.ItemRemove2ClickListener{
                override fun onItemClicked(comment: Comment) {
                    viewModel.deleteComment(countryName.toString(),comment.id)
                    viewModel.getCommentPost(countryName.toString())
                }
            })
        })
    }

    fun buttonClickListener(){

        binding.btnAdd.setOnClickListener {
            val test = binding.etWriteComment.text.toString()
            viewModel.postComment(countryName.toString(), CommentBody(test))
            viewModel.getCommentPost(countryName.toString())
        }
    }
}