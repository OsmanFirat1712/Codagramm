package com.tailoredapps.codagram.ui.homeFeedScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.models.Comment
import com.tailoredapps.codagram.models.CommentBody
import com.tailoredapps.codagram.models.Post
import com.tailoredapps.codagram.remote.CodagramApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class CommentScreenViewModel(private val codagramApi: CodagramApi) : ViewModel(){

    @ExperimentalCoroutinesApi
    private val myComments = MutableLiveData<List<Comment>>()
    @ExperimentalCoroutinesApi
    fun getMyComments():LiveData<List<Comment>> = myComments


    fun getanasiniSikim(id:String){
        try {
            viewModelScope.launch(Dispatchers.IO){
                val response = codagramApi.getPostId(id)

            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }

    fun getCommentPost(id:String){
        try {
            viewModelScope.launch(Dispatchers.IO){
                val response = codagramApi.getComment(id)
                updateComment(response.comments)
            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }

    @ExperimentalCoroutinesApi
    fun updateComment(comment:List<Comment>) {
        viewModelScope.launch(Dispatchers.Main) {
            myComments.value = comment
        }

    }

    @ExperimentalCoroutinesApi
    fun postComment(id:String,text:CommentBody){
        viewModelScope.launch ( Dispatchers.IO ){
            var response = codagramApi.postComment(id,text)
        }
    }

}