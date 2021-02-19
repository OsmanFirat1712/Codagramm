package com.tailoredapps.codagram.ui.groupscreen

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.databinding.SearchItemsBinding
import com.tailoredapps.codagram.models.SearchResult
import com.tailoredapps.codagram.models.User


class SearchAdapter : ListAdapter<SelectedUser, SearchViewHolder>(object: DiffUtil.ItemCallback<SelectedUser>(){

    override fun areItemsTheSame(oldItem: SelectedUser, newItem: SelectedUser): Boolean = oldItem.user.id == newItem.user.id


    override fun areContentsTheSame(oldItem: SelectedUser, newItem: SelectedUser): Boolean =
        oldItem == newItem

}){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            SearchItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class SearchViewHolder(private val binding:SearchItemsBinding) : RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun bind(postData: SelectedUser) {
        binding.root.setOnClickListener {
            postData.selected = postData.selected.not()
        }

        binding.selectedUser.visibility = if (postData.selected){
            View.VISIBLE
        }else {
            View.GONE
        }

/*
        binding.resultText.text = postData.users.toString()

*/

            binding.resultText.text = postData.user.firstname


        }


    }
