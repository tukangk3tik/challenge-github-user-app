package com.dicoding.picodiploma.githubuserapp.favorit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.db.FavoritEntity
import kotlinx.android.synthetic.main.item_followers_following.view.*

class CardViewFavoritAdapter : RecyclerView.Adapter<CardViewFavoritAdapter.ViewHolder> () {
    private val listFav = ArrayList<FavoritEntity>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favItem: FavoritEntity) {
            with(itemView) {
                txt_follower_following.text = favItem.username

                Glide.with(itemView)
                    .load(favItem.avatar_url)
                    .apply(RequestOptions().override(350, 550))
                    .into(img_following_followers_photo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_followers_following, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listFav.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFav[position])
    }

    fun setData(items: ArrayList<FavoritEntity>) {
        listFav.clear()
        listFav.addAll(items)
        notifyDataSetChanged()
    }
}