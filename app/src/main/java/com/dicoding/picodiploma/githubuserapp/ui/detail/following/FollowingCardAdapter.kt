package com.dicoding.picodiploma.githubuserapp.ui.detail.following

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuserapp.databinding.ItemFollowersFollowingBinding
import com.dicoding.picodiploma.githubuserapp.models.following.DataFollowing
import com.dicoding.picodiploma.githubuserapp.ui.detail.FollowingListClickListener

class FollowingCardAdapter: RecyclerView.Adapter<FollowingCardAdapter.ViewHolder> () {

    private val listFollowing = ArrayList<DataFollowing>()
    var listener: FollowingListClickListener? = null

    inner class ViewHolder (private val binding: ItemFollowersFollowingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(followingList: DataFollowing){
            with(binding) {
                txtFollowerFollowing.text = followingList.username

                Glide.with(itemView.context)
                    .load(followingList.photoProfile)
                    .apply(RequestOptions().override(350,550))
                    .into(imgFollowingFollowersPhoto)

                itemView.setOnClickListener {
                    listener?.onItemClicked(itemView, followingList)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemFollowersFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listFollowing.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFollowing[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: ArrayList<DataFollowing>) {
        listFollowing.clear()
        listFollowing.addAll(items)
        notifyDataSetChanged()
    }
}