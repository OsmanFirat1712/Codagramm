package com.tailoredapps.codagram.ui.homeFeedScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.CommentScreenItemsBinding
import com.tailoredapps.codagram.models.Comment
import com.tailoredapps.codagram.remote.CodaGramApi

class CommentScreenAdapter(val codaGramApi: CodaGramApi) : ListAdapter<Comment, CommentScreenAdapter.CountryItem>(DiffCallback()) {

    lateinit var mItemCLicked: ItemRemove2ClickListener



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
        val currentItem = getItem(position)

        holder.remove.setOnClickListener {
            mItemCLicked.let {
                mItemCLicked.onItemClicked(getItem(position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItem {

        return CountryItem(CommentScreenItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    fun setUpListener(itemCLicked: ItemRemove2ClickListener) {
        mItemCLicked = itemCLicked
    }

    class CountryItem(private val binding: CommentScreenItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val remove:TextView = itemView.findViewById(R.id.tvRemove)

        fun bind(postData: Comment) {

            binding.tvUserName.text = postData.user?.firstname.toString()
            binding.tvUserComment.text = postData.text

        }
    }

    interface ItemRemove2ClickListener{
        fun onItemClicked(comment: Comment)
    }

}


