package com.dicoding.picodiploma.githubuserapp.ui.favorit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.githubuserapp.favoritconstumer.databinding.ItemFavoritBinding
import app.githubuserapp.favoritconstumer.db.FavoritEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CardViewFavoritAdapter : RecyclerView.Adapter<CardViewFavoritAdapter.ViewHolder> () {

    private val listFav = ArrayList<FavoritEntity>()
    var listener: FavoritListClickListener? = null

    inner class ViewHolder(private val binding: ItemFavoritBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favItem: FavoritEntity) {
            with(binding) {
                txtUsername.text = favItem.username

                Glide.with(itemView.context)
                    .load(favItem.avatar_url)
                    .apply(RequestOptions().override(350, 550))
                    .into(imgPhoto)

                deleteFavorit.setOnClickListener {
                    listener?.onDeleteClicked(it, favItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoritBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listFav.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFav[position])
    }

    fun setData(items: List<FavoritEntity>) {
        listFav.clear()
        listFav.addAll(items)
        notifyDataSetChanged()
    }
}