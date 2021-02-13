package com.example.navigationgraph.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationgraph.databinding.HomeFeedScreenBinding
import com.example.navigationgraph.models.User

class HomeFeedAdapter : ListAdapter<User, CountryItem>(object: DiffUtil.ItemCallback<User>(){

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.email == newItem.email

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
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

    fun bind(postData: User) {

    }
}