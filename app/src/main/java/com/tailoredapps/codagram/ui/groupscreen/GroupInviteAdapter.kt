package com.tailoredapps.codagram.ui.groupscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.GroupInviteListBinding
import com.tailoredapps.codagram.models.GroupInvite

class GroupInviteAdapter : ListAdapter<GroupInvite, GroupInviteAdapter.MainViewHolder>(
    DiffCallback()
) {
    lateinit var mItemCLicked: ItemCLickedListener


    class DiffCallback : DiffUtil.ItemCallback<GroupInvite>() {
        override fun areItemsTheSame(oldItem: GroupInvite, newItem: GroupInvite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GroupInvite, newItem: GroupInvite): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            GroupInviteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
        val curentItem = getItem(position)

        holder.apply {
            accept.setOnClickListener {
                accept.text = curentItem.name

                curentItem.replyToInvite = curentItem.replyToInvite.not()
                accept.visibility = if (curentItem.replyToInvite) {
                    mItemCLicked.let {
                        mItemCLicked.onItemClicked(true, curentItem.id)
                    }
                    View.VISIBLE
                } else {
                    View.VISIBLE
                }
            }
            deny.setOnClickListener {
                curentItem.replyToInvite = curentItem.replyToInvite.not()
                accept.visibility = if (curentItem.replyToInvite) {
                    mItemCLicked.let {
                        mItemCLicked.onItemClicked(false, curentItem.id)
                    }
                    View.VISIBLE
                } else {
                    View.VISIBLE
                }
            }
        }
        holder.itemView.setOnClickListener {
            mItemCLicked.let {
                mItemCLicked.onItemClicked(curentItem.replyToInvite, curentItem.id)
            }
        }
    }

    fun setUpListener(itemCLicked: ItemCLickedListener) {
        mItemCLicked = itemCLicked
    }


    class MainViewHolder(private val binding: GroupInviteListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val accept: TextView = itemView.findViewById(R.id.acceptInvite)
        val deny: TextView = itemView.findViewById(R.id.denyInvite)


        fun bind(postData: GroupInvite) {
            /*    binding.root.setOnClickListener {
                    postData.replyToInvite = postData.replyToInvite.not()
                    binding.acceptInvite.visibility = if (postData.replyToInvite){
                        mItemCLicked.let {
                        mItemCLicked.onItemClicked(true,postData.id)
                        }
                        View.VISIBLE
                    }else {
                        View.VISIBLE
                    }
                }

                binding.acceptInvite.visibility = if (postData.replyToInvite){
                    mItemCLicked.let {
                        mItemCLicked.onItemClicked(true,postData.id)
                    }
                    View.VISIBLE
                }else {
                    View.VISIBLE
                }*/
            /*    postData.members.forEach {
                    val userName = it.firstname

                }*/

            binding.resultText.text = postData.inviter.firstname.toString()

        }

    }

    interface ItemCLickedListener {
        fun onItemClicked(accept: Boolean, id: String)
    }

}