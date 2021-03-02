/*
package com.tailoredapps.codagram.ui.newStoryScreen

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.databinding.NewStorySearchItemBinding
import com.tailoredapps.codagram.remoteModels.SelectedUser
import org.jetbrains.annotations.NotNull

/*
class NewStorySearchAdapter: ListAdapter<SelectedUser,NewStorySearchViewHolder>(object: DiffUtil.ItemCallback<SelectedUser>(){
    override fun areItemsTheSame(oldItem: SelectedUser, newItem: SelectedUser): Boolean = oldItem.user.id == newItem.user.id

    override fun areContentsTheSame(oldItem: SelectedUser, newItem: SelectedUser): Boolean = oldItem == newItem


}){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewStorySearchViewHolder {
        return NewStorySearchViewHolder(
            NewStorySearchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: NewStorySearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class NewStorySearchViewHolder(private val binding:NewStorySearchItemBinding): RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.N)
    fun bind(postData: SelectedUser) {
        binding.root.setOnClickListener {
            postData.selected = postData.selected.not()

            binding.selectedUser2.visibility = if (postData.selected){
                View.VISIBLE
            }else {
                View.GONE
            }
        }

        binding.selectedUser2.visibility = if (postData.selected){
            View.VISIBLE
        }else {
            View.GONE
        }

/*
        binding.resultText.text = postData.users.toString()

*/

        binding.resultText2.text = postData.user.firstname


    }
}
*/