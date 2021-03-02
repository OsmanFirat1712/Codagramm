package com.tailoredapps.codagram.ui.homeFeedScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.Post
import com.tailoredapps.codagram.remote.CodaGramApi
import com.tailoredapps.codagram.remoteModels.CommentLike
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFeedViewModel(private val codaGramApi: CodaGramApi) : ViewModel() {

    @ExperimentalCoroutinesApi
    private val myPosts = MutableLiveData<List<Post>>()
    @ExperimentalCoroutinesApi
    fun getMyPost(): LiveData<List<Post>> = myPosts

    @ExperimentalCoroutinesApi
    private val myGroups = MutableLiveData<List<Group>>()
    @ExperimentalCoroutinesApi
    fun getMyGroups(): LiveData<List<Group>> = myGroups



    fun getStoryPost(id:String?){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = codaGramApi.getStoryPost(id)
                updateHomeFeed(response.posts)
            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }


    fun getStoryPostbyQuery(id:String?){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = codaGramApi.getStoryPostbyQuery(id)
                updateHomeFeed(response.posts)
            }
        }catch (ie:Exception){
            Timber.e(ie)
        }
    }

    fun getAllGroups(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = codaGramApi.getAllGroups()
            updateUi(response.groups)
        }
    }

    @ExperimentalCoroutinesApi
    private fun updateUi(update: List<Group>) {
        viewModelScope.launch(Dispatchers.Main) {
            myGroups.value = update
        }
    }


    @ExperimentalCoroutinesApi
    fun updateHomeFeed(update:List<Post>) {
        viewModelScope.launch(Dispatchers.Main) {
            myPosts.value = update
        }

    }

    @ExperimentalCoroutinesApi
    fun likeComment(id:String,like:Boolean){
        try {
            viewModelScope.launch(Dispatchers.IO){
                val response = codaGramApi.likeToComment(id, CommentLike(like))
            }


        }catch (ie:Exception){
            Timber.e(ie)
        }
    }

    fun removePost(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = codaGramApi.deletePost(id)

        }
    }

}