package com.tailoredapps.codagram.ui.homeFeedScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.GlideApp
import com.bumptech.glide.Glide
import com.google.android.play.core.assetpacks.dd
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.CommentScreenItemsBinding
import com.tailoredapps.codagram.models.Comment
import com.tailoredapps.codagram.models.Post
import com.tailoredapps.codagram.models.User
import com.tailoredapps.codagram.remote.CodaGramApi
import com.tailoredapps.codagram.ui.HomeFeedScreenDirections
import com.tailoredapps.codagram.ui.groupscreen.GroupDetailsAdapter
import kotlinx.android.synthetic.main.comment_screen_items.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.time.days



class CommentScreenAdapter(val codaGramApi: CodaGramApi) : ListAdapter<Comment, CommentScreenAdapter.CountryItem>(DiffCallback()) {


    lateinit var mItemCLicked: ItemRemove2ClickListener



    class DiffCallback : DiffUtil.ItemCallback<Comment>() {

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }
    }
    override fun onBindViewHolder(holder: CountryItem, position: Int) {
        holder.bind(getItem(position))
        val currentItem = getItem(position)

        holder.apply {
            Glide.with(itemView)
                .load(currentItem.user?.image?.url)
                .placeholder(R.drawable.person)
                .into(itemView.ivUserImage)

        }


        when{
            FirebaseAuth.getInstance().currentUser!!.uid == currentItem.user?.id -> holder.remove.visibility = View.VISIBLE
            FirebaseAuth.getInstance().currentUser!!.uid != currentItem.user?.id -> holder.remove.visibility = View.INVISIBLE
            //We need a creatorId after creatorId you can delete all comments!

        }

        holder.remove.setOnClickListener {
            mItemCLicked.let {
                mItemCLicked.onItemClicked(getItem(position))
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItem {

        return CountryItem(CommentScreenItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    fun setUpListener(itemCLicked: ItemRemove2ClickListener) {
        mItemCLicked = itemCLicked
    }

    class CountryItem(private val binding: CommentScreenItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val deleteText:TextView = itemView.findViewById(R.id.tvRemove)

        val remove:TextView = itemView.findViewById(R.id.tvRemove)

        fun bind(postData: Comment) {

            binding.tvUserName.text = postData.user?.firstname.toString()
            binding.tvUserComment.text = postData.text
            binding.tvCommendTime.text = postData.createdAt

        }
    }

    interface ItemRemove2ClickListener{
        fun onItemClicked(comment: Comment)
    }

}


