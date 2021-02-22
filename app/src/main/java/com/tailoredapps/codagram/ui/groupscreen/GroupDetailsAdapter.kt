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
import com.tailoredapps.codagram.databinding.SearchDetailPageBinding
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.User


class GroupDetailsAdapter : ListAdapter<User, GroupDetailsSearchViewHolder>(object: DiffUtil.ItemCallback<User>(){

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem

}){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupDetailsSearchViewHolder {
        return GroupDetailsSearchViewHolder(
            SearchDetailPageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: GroupDetailsSearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class GroupDetailsSearchViewHolder(private val binding: SearchDetailPageBinding) : RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun bind(postData: User) {

    /*    postData.members.forEach {
            val userName = it.firstname

        }*/

        binding.resultText.text = postData.firstname.toString()

    }

}
