package com.tailoredapps.codagram.ui.homeFeedScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.databinding.CommentScreenItemsBinding
import com.tailoredapps.codagram.databinding.HomeFeedScreenBinding
import com.tailoredapps.codagram.databinding.SearchDetailPageBinding
import com.tailoredapps.codagram.models.Comment
import com.tailoredapps.codagram.models.Post
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.ui.HomeFeedScreenDirections
import com.tailoredapps.codagram.ui.groupscreen.GroupDetailsAdapter

class CommentScreenAdapter : ListAdapter<Comment, CommentScreenAdapter.CountryItem>(
    DiffCallback()
) {
    lateinit var mItemCLicked: GroupDetailsAdapter.ItemRemoveClickListener


    class DiffCallback : DiffUtil.ItemCallback<Comment>() {


        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }


    }

    override fun onBindViewHolder(holder: CountryItem, position: Int) {
        holder.bind(getItem(position))
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryItem {
        return CountryItem(
            CommentScreenItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun setUpListener(itemCLicked: GroupDetailsAdapter.ItemRemoveClickListener) {
        mItemCLicked = itemCLicked
    }


    class CountryItem(private val binding: CommentScreenItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(postData: Comment) {

            binding.tvUserName.text = postData.user?.firstname.toString()
            binding.tvUserComment.text = postData.text
        }
    }

}


