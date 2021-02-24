package com.tailoredapps.codagram.ui.homeFeedScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.databinding.CommentScreenItemsBinding
import com.tailoredapps.codagram.databinding.HomeFeedScreenBinding
import com.tailoredapps.codagram.models.Comment
import com.tailoredapps.codagram.models.Post
import com.tailoredapps.codagram.ui.HomeFeedScreenDirections

class CommentScreenAdapter : ListAdapter<Comment, CountryItem>(object: DiffUtil.ItemCallback<Comment>(){

    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean = oldItem.text == newItem.text

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
        oldItem == newItem

}){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItem {
        return CountryItem(
            CommentScreenItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: CountryItem, position: Int) {
        holder.bind(getItem(position))
    }

}

class CountryItem(private val binding: CommentScreenItemsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(postData: Comment) {

        binding.tvUserName.text = postData.user?.firstname.toString()
        binding.tvUserComment.text = postData.text
    }
}