package com.tailoredapps.codagram.ui.homeFeedScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.CommentScreenItemsBinding
import com.tailoredapps.codagram.databinding.HomeFeedScreenBinding
import com.tailoredapps.codagram.databinding.SearchDetailPageBinding
import com.tailoredapps.codagram.models.Comment
import com.tailoredapps.codagram.models.Post
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.remote.CodagramApi
import com.tailoredapps.codagram.ui.HomeFeedScreenDirections
import com.tailoredapps.codagram.ui.groupscreen.GroupDetailsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CommentScreenAdapter(val codagramApi: CodagramApi) : ListAdapter<Comment, CommentScreenAdapter.CountryItem>(DiffCallback()) {
    lateinit var mItemCLicked: GroupDetailsAdapter.ItemRemoveClickListener
    var countryName: String? = null



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
            GlobalScope.launch(Dispatchers.IO){

                val bundle = Bundle()
                val test = bundle.getBinder("name")
                Log.e("tester",test.toString())
                codagramApi.deleteComment(test.toString(),currentItem.id)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItem {

        return CountryItem(CommentScreenItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        )
    }


    class CountryItem(private val binding: CommentScreenItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val remove:TextView = itemView.findViewById(R.id.tvRemove)

        fun bind(postData: Comment) {

            binding.tvUserName.text = postData.user?.firstname.toString()
            binding.tvUserComment.text = postData.text

        }
    }

}


