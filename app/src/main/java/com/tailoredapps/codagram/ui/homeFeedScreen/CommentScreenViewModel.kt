package com.tailoredapps.codagram.ui.homeFeedScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.models.Comment
import com.tailoredapps.codagram.models.CommentBody
import com.tailoredapps.codagram.models.Post
import com.tailoredapps.codagram.remote.CodaGramApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class CommentScreenViewModel(private val codaGramApi: CodaGramApi) : ViewModel(){

    @ExperimentalCoroutinesApi
    private val myPost = MutableLiveData<Post>()
    @ExperimentalCoroutinesApi
    fun getMyPost():LiveData<Post> = myPost


    @ExperimentalCoroutinesApi
    private val myComments = MutableLiveData<List<Comment>>()
    @ExperimentalCoroutinesApi
    fun getMyComments():LiveData<List<Comment>> = myComments


    fun getPostById(id:String){
        try {
            viewModelScope.launch(Dispatchers.IO){
                val response = codaGramApi.getPostId(id)
               // updatePost(response)
            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }

    fun getCommentPost(id:String){
        try {
            viewModelScope.launch(Dispatchers.IO){
                val response = codaGramApi.getComment(id)
                updateComment(response.comments)
            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }

    fun updatePost(post:Post){
        viewModelScope.launch(Dispatchers.Main){
            myPost.value = post
        }
    }

    @ExperimentalCoroutinesApi
    fun updateComment(comment:List<Comment>) {
        viewModelScope.launch(Dispatchers.Main) {
            myComments.value = comment
        }

    }

    fun deleteComment(id: String, commentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = codaGramApi.deleteComment(id, commentId)
            if (response.isSuccessful){
                val update = codaGramApi.getComment(id)
                updateComment(update.comments)
            }

        }
    }

        @ExperimentalCoroutinesApi
        fun postComment(id: String, text: CommentBody) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = codaGramApi.postComment(id, text)

                if (response.isSuccessful){
                    val updates = codaGramApi.getComment(id)
                    updateComment(updates.comments)
                }


            }
        }


}