package com.tailoredapps.codagram.ui.groupscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.databinding.SearchItemsBinding
import com.tailoredapps.codagram.models.SearchResult


class SearchAdapter : ListAdapter<SearchResult, SearchViewHolder>(object: DiffUtil.ItemCallback<SearchResult>(){

    override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean = oldItem.users == newItem.users

    override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean =
        oldItem == newItem

}){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            SearchItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class SearchViewHolder(private val binding:SearchItemsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(postData: SearchResult) {
/*
        binding.resultText.text = postData.users.toString()
*/
        binding.resultText.text = postData.users.get(1).id


    }
}