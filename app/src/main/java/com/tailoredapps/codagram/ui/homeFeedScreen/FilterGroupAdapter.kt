package com.tailoredapps.codagram.ui.homeFeedScreen


import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tailoredapps.codagram.databinding.FilterGroupItemBinding
import com.tailoredapps.codagram.databinding.SearchDetailPageBinding
import com.tailoredapps.codagram.models.Group
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.ui.groupscreen.GroupDetailsAdapter
import timber.log.Timber

class FilterGroupAdapter : ListAdapter<Group, GroupScreenViewHolder>(
    DiffCallback()
) {
    lateinit var mItemCLicked: ItemFilterListener



    class DiffCallback : DiffUtil.ItemCallback<Group>() {
        override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem.id == newItem.id
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupScreenViewHolder {
        return GroupScreenViewHolder(
            FilterGroupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }




    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: GroupScreenViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            holder.apply {
                mItemCLicked.let {
                    mItemCLicked.onItemClicked(getItem(position))
                }
            }


        }

    }
    fun setUpListener(itemCLicked: ItemFilterListener) {
        mItemCLicked = itemCLicked
    }

    interface ItemFilterListener{
        fun onItemClicked(group: Group)
    }


}



class GroupScreenViewHolder(private val binding: FilterGroupItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun bind(postData: Group) {

        val bundle = bundleOf(


            "name" to postData.name,
            "id" to postData.id,
            "creatorId" to postData.creator?.id
        )
        val name = postData.name
        Timber.e("gesndet")


      /*  binding.root.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_group_view_to_groupdetails, bundle)

        }*/
        postData.members.forEach {
            val userName = it.id
        }

        binding.tvGroupName.text = postData.name.toString()
        binding.tvCreatorName.text = postData.creator?.lastname.toString()
        binding.tvGroupInviter.text = postData.inviter?.lastname


    }





}
