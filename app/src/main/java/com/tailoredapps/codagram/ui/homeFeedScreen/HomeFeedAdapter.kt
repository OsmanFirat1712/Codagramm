package com.tailoredapps.codagram.ui.homeFeedScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.google.firebase.auth.FirebaseAuth
import com.tailoredapps.codagram.R
import com.tailoredapps.codagram.databinding.HomeFeedScreenBinding
import com.tailoredapps.codagram.models.Post
import com.tailoredapps.codagram.remote.CodaGramApi
import com.tailoredapps.codagram.remote.SessionManager
import kotlinx.android.synthetic.main.fragment_group.view.*
import kotlinx.android.synthetic.main.fragment_group_details.view.*
import kotlinx.android.synthetic.main.home_feed_screen.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFeedAdapter(val codaGramApi: CodaGramApi, val context: Context) : ListAdapter<Post, HomeFeedAdapter.CountryItem>(DiffCallback()) {
    lateinit var mItemCLicked: ItemCLickedListener
    lateinit var mItemRemoveClicked: ItemGroupRemoveListener
    private val sessionManager = SessionManager(context)
    private lateinit var token:String

    class DiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areContentsTheSame(oldItem: Post, newItem: Post
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }
    }
    override fun onBindViewHolder(holder: CountryItem, position: Int) {
        holder.bind(getItem(position))
        val currentItem = getItem(position)

        holder.apply {
            Glide.with(itemView)
                .load(currentItem.image?.url)
                .into(itemView.post_image)

            Glide.with(itemView)
                .load(currentItem.user?.image?.url)
                .into(itemView.ivUserImage)

            Glide.with(itemView)
                .load(currentItem.user?.image?.url)
                .into(itemView.ivUser2Image)

            Glide.with(itemView)
                .load(currentItem.user?.image?.url)
                .into(itemView.user_photo_image)

            userName.text = currentItem.user?.firstname
            likeCount.text = currentItem.likes.toString()
            tagSize.text = currentItem.tags.size.toString()
            tvGroupName.text = currentItem.group?.name.toString()
            tvNickName.text = "(" + currentItem.user?.nickname.toString() + ")"


            when{
                currentItem.comments!!.isNotEmpty() -> {
                    cardCommend.visibility = View.VISIBLE
                    writtenBy.text = currentItem.comments?.firstOrNull()?.user?.firstname.toString()
                    firstComment.text = currentItem.comments?.firstOrNull()?.text
                }
                else -> {
                    cardCommend.visibility = View.INVISIBLE
                }

            }

            writtenBy2.text = currentItem.user?.firstname
            secondComment.text = currentItem.comments.getOrNull(1)?.text.toString()
            commentCount2.text = currentItem.comments?.size.toString()+" "+"Comment"

            like.setOnClickListener {
                like.setImageResource(R.drawable.ic_baseline_favoritelike_24)
                currentItem.userLiked = currentItem.userLiked.not()

                when{
                    currentItem.userLiked -> {
                        mItemCLicked.let {
                            mItemCLicked.onItemClicked(true,getItem(position))
                            like.setImageResource(R.drawable.ic_baseline_favoritelike_24)
                            if (FirebaseAuth.getInstance().currentUser!!.uid == currentItem.member.toString()){
                                like.setImageResource(R.drawable.ic_baseline_favoritelike_24)
                            }
                        }
                    }
                    else->{
                        mItemCLicked.onItemClicked(false,getItem(position))

                        like.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }
                }
            }
        }
        holder.itemView.setOnClickListener {
            mItemCLicked.let {
                mItemCLicked.onItemClicked(currentItem.userLiked,getItem(position))
            }
        }

        when {
            FirebaseAuth.getInstance().currentUser!!.uid == currentItem.user?.id-> holder.delete.visibility = View.VISIBLE
            else->holder.delete.visibility = View.INVISIBLE

        }




        holder.delete.setOnClickListener {
            mItemRemoveClicked.let{
                mItemRemoveClicked.onGroupRemoved(getItem(position))
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryItem {
        return CountryItem(
            HomeFeedScreenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun setUpListener(itemCLicked:ItemCLickedListener) {
        mItemCLicked = itemCLicked
    }

    fun removeUpListener(itemRemoved:ItemGroupRemoveListener) {
        mItemRemoveClicked = itemRemoved
    }


    class CountryItem(private val binding: HomeFeedScreenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val like: ImageView = itemView.findViewById(R.id.like_image)
        val userName:TextView = itemView.findViewById(R.id.username_text)
        val likeCount:TextView = itemView.findViewById(R.id.likes_text)
        val firstComment:TextView = itemView.findViewById(R.id.tvFirstComment)
        val secondComment:TextView = itemView.findViewById(R.id.tvFirstComment2)
        val writtenBy2:TextView = itemView.findViewById(R.id.tvWrittenBy2)
        val writtenBy:TextView = itemView.findViewById(R.id.tvWrittenBy)
        val commentCount2:TextView = itemView.findViewById(R.id.comment_text)
        val delete:ImageView = itemView.findViewById(R.id.ivDelete)
        val cardCommend:CardView = itemView.findViewById(R.id.cv1)
        val cardCommend2:CardView = itemView.findViewById(R.id.cvLastCommend2)
        val tagSize:TextView = itemView.findViewById(R.id.tvTagSize)
        val tvGroupName:TextView = itemView.findViewById(R.id.tvGroupName)
        val tvNickName:TextView = itemView.findViewById(R.id.tvNickName)

        fun bind(postData: Post) {

            val bundle = bundleOf(
                "name" to postData.id,
            )




            binding.captionText.text = postData.description.toString()
            binding.commentImage.setOnClickListener {

                it.findNavController()
                    .navigate(R.id.action_firstView_to_CommentScreenFragment, bundle)
            }


            binding.commentText.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_firstView_to_CommentScreenFragment, bundle)
            }

            binding.lastCvv.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_firstView_to_CommentScreenFragment, bundle)
            }


        }


    }
    interface ItemCLickedListener {
        fun onItemClicked(like: Boolean, post: Post)
    }
    interface ItemGroupRemoveListener {
        fun onGroupRemoved(post:Post)
    }
}
