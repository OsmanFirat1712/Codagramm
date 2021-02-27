package com.tailoredapps.codagram.ui.homeFeedScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.CommentScreenItemsBinding
import com.tailoredapps.codagram.databinding.HomeFeedScreenBinding
import com.tailoredapps.codagram.databinding.SearchDetailPageBinding
import com.tailoredapps.codagram.models.Comment
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.Post
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.remote.CodagramApi
import com.tailoredapps.codagram.ui.HomeFeedScreenDirections
import com.tailoredapps.codagram.ui.groupscreen.GroupDetailsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFeedAdapter(val codagramApi: CodagramApi) : ListAdapter<Post, HomeFeedAdapter.CountryItem>(DiffCallback()) {
    lateinit var mItemCLicked: ItemCLickedListener

    class DiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }
    }
    override fun onBindViewHolder(holder: CountryItem, position: Int) {
        holder.bind(getItem(position))
        val currentItem = getItem(position)

        holder.apply {

            userName.text = currentItem.user?.firstname
            likeCount.text = currentItem.likes.toString()
            writtenBy.text = currentItem.comments?.firstOrNull()?.user?.firstname.toString()
            firstComment.text = currentItem.comments?.firstOrNull()?.text
            commentCount2.text = currentItem.comments?.size.toString()+" "+"Comment"

            like.setOnClickListener {
                like.setImageResource(R.drawable.ic_baseline_favoritelike_24)
                currentItem.userLiked = currentItem.userLiked.not()

                when{
                    currentItem.userLiked -> {
                        mItemCLicked.let {
                            mItemCLicked.onItemClicked(true,currentItem.id)
                            like.setImageResource(R.drawable.ic_baseline_favoritelike_24)

                        }
                    }
                    else->{
                        mItemCLicked.onItemClicked(false,currentItem.id)
                        like.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }
                }
            }
        }
        holder.itemView.setOnClickListener {
            mItemCLicked.let {
                mItemCLicked.onItemClicked(currentItem.userLiked,currentItem.id)
            }
        }

        holder.delete.setOnClickListener {

            GlobalScope.launch (Dispatchers.IO ){
                codagramApi.deletePost(currentItem.id)
            }
            //codagramApi.deletePost(currentItem.id)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryItem {
        return CountryItem(
            HomeFeedScreenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun setUpListener(itemCLicked:ItemCLickedListener) {
        mItemCLicked = itemCLicked
    }

    class CountryItem(private val binding: HomeFeedScreenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val like: ImageView = itemView.findViewById(R.id.like_image)
        val userName:TextView = itemView.findViewById(R.id.username_text)
        val likeCount:TextView = itemView.findViewById(R.id.likes_text)
        val firstComment:TextView = itemView.findViewById(R.id.tvFirstComment)
        val writtenBy:TextView = itemView.findViewById(R.id.tvWrittenBy)
        val commentCount2:TextView = itemView.findViewById(R.id.comment_text)
        val delete:ImageView = itemView.findViewById(R.id.ivDelete)

        fun bind(postData: Post) {

            val bundle = bundleOf(
                "name" to postData.id,
            )


            binding.captionText.text = postData.description.toString()
            binding.commentImage.setOnClickListener {

                it.findNavController()
                    .navigate(R.id.action_firstView_to_CommentScreenFragment, bundle)
            }


            binding.commentText.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_firstView_to_CommentScreenFragment, bundle)
            }

            binding.lastCvv.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_firstView_to_CommentScreenFragment, bundle)
            }


        }

    fun test(update:List<Post>){
        GlobalScope.launch (Dispatchers.IO){
            val myPosts = MutableLiveData<List<Post>>()
            fun refreshPosts(): LiveData<List<Post>> = myPosts
            myPosts.value = update
        }
    }

    }
    interface ItemCLickedListener {
        fun onItemClicked(accept: Boolean, id: String)
    }
}
