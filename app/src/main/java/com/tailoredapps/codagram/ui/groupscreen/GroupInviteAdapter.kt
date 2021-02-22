package com.tailoredapps.codagram.ui.groupscreen

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.databinding.GroupInviteListBinding
import com.tailoredapps.codagram.interfaces.ReplyInviteCallBack
import com.tailoredapps.codagram.models.GroupInvite


class GroupInviteAdapter(/* var mItemCLicked: ReplyInviteCallBack*/) : ListAdapter<GroupInvite, GroupInviteViewHolder>(object: DiffUtil.ItemCallback<GroupInvite>(){

    override fun areItemsTheSame(oldItem: GroupInvite, newItem: GroupInvite): Boolean = oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: GroupInvite, newItem: GroupInvite): Boolean =
        oldItem == newItem

}){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupInviteViewHolder {
        return GroupInviteViewHolder(
            GroupInviteListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: GroupInviteViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {


        }

    }


 /*   fun setUpListener(itemCLicked: ReplyInviteCallBack){
        mItemCLicked = itemCLicked
    }*/

}

class GroupInviteViewHolder(private val binding: GroupInviteListBinding) : RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun bind(postData: GroupInvite) {
        binding.root.setOnClickListener {
            postData.selected = postData.selected.not()

            binding.acceptInvite.visibility = if (postData.selected){
                View.VISIBLE
            }else {
                View.GONE
            }
        }

        binding.acceptInvite.visibility = if (postData.selected){
            View.VISIBLE
        }else {
            View.GONE
        }


        /*    postData.members.forEach {
                val userName = it.firstname

            }*/

        binding.resultText.text = postData.inviter.firstname.toString()

    }

}
