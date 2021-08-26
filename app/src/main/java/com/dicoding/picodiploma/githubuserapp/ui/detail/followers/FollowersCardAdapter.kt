package com.dicoding.picodiploma.githubuserapp.ui.detail.followers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuserapp.databinding.ItemFollowersFollowingBinding
import com.dicoding.picodiploma.githubuserapp.models.followers.DataFollowers
import com.dicoding.picodiploma.githubuserapp.ui.detail.FollowingListClickListener

class FollowersCardAdapter: RecyclerView.Adapter<FollowersCardAdapter.ViewHolder> () {

    private val listFollowers = ArrayList<DataFollowers>()
    var listener: FollowingListClickListener? = null

    inner class ViewHolder (private val binding: ItemFollowersFollowingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(followersList: DataFollowers) {
            with(binding) {
                txtFollowerFollowing.text = followersList.username

                Glide.with(itemView.context)
                    .load(followersList.photoProfile)
                    .apply(RequestOptions().override(350,550))
                    .into(imgFollowingFollowersPhoto)

                itemView.setOnClickListener {
                    listener?.onItemClicked(itemView, followersList)
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

    override fun getItemCount(): Int = listFollowers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFollowers[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<DataFollowers>) {
        listFollowers.clear()
        listFollowers.addAll(items)
        notifyDataSetChanged()
    }
}