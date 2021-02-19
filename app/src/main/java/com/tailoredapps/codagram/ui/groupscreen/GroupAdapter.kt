package com.tailoredapps.codagram.ui.groupscreen

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.databinding.GroupscreenMygroupsBinding
import com.tailoredapps.codagram.models.Group

class GroupAdapter : ListAdapter<Group,GroupScreenViewHolder>(object: DiffUtil.ItemCallback<Group>(){

    override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean = oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean =
        oldItem == newItem

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupScreenViewHolder {
        return GroupScreenViewHolder(
            GroupscreenMygroupsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: GroupScreenViewHolder, position: Int) {
        holder.bind(getItem(position))

    }
}
    class GroupScreenViewHolder(private val binding: GroupscreenMygroupsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(postData: Group) {

            binding.textView.text = postData.name
            binding.textView2.text = postData.id

        }
    }

