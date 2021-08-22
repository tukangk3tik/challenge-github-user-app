package com.dicoding.picodiploma.githubuserapp.ui.favorit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.databinding.ActivityFavoritBinding
import com.dicoding.picodiploma.githubuserapp.db.FavoritEntity

class FavoritActivity : AppCompatActivity(), FavoritListClickListener {

    private val favoritViewModel: FavoritViewModel by viewModels()
    private lateinit var favoritBinding: ActivityFavoritBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoritBinding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(favoritBinding.root)

        val adapter = CardViewFavoritAdapter()
        adapter.listener = this
        favoritBinding.rvFavorit.layoutManager = LinearLayoutManager(this)
        favoritBinding.rvFavorit.adapter = adapter

        favoritViewModel.getListFavorit()?.observe(this, {
            isDataAvailable("loading")

            if (it.isNotEmpty()) {
                adapter.setData(it)
                isDataAvailable("available")
            } else {
                isDataAvailable("nodata")
            }
        })
    }

    private fun isDataAvailable(status: String) {
        when (status) {
            "available" -> {
                favoritBinding.txtNoData.visibility = View.GONE
                favoritBinding.rvFavorit.visibility = View.VISIBLE
            }
            "nodata" -> {
                favoritBinding.txtNoData.visibility = View.VISIBLE
                favoritBinding.rvFavorit.visibility = View.GONE
            }
            "loading" -> {
                favoritBinding.rvFavorit.visibility = View.GONE
                favoritBinding.txtNoData.visibility = View.GONE
            }
        }
    }

    override fun onItemClicked(view: View, favorit: FavoritEntity) {
        Toast.makeText(this,
            "${favorit.username} has been removed to favorit",
            Toast.LENGTH_SHORT)
            .show()

        favoritViewModel.deleteFavorit(favorit)
    }
}