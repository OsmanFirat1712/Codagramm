package com.tailoredapps.codagram.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.databinding.HomeFeedScreenBinding
import com.tailoredapps.codagram.models.Post
import com.tailoredapps.codagram.models.User

class HomeFeedAdapter : ListAdapter<Post, CountryItem>(object: DiffUtil.ItemCallback<Post>(){

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.description == newItem.description

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem == newItem

}){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItem {
        return CountryItem(
            HomeFeedScreenBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: CountryItem, position: Int) {
        holder.bind(getItem(position))
    }

}

class CountryItem(private val binding: HomeFeedScreenBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(postData: Post) {
       binding.captionText.text = postData.description.toString()
    }
}