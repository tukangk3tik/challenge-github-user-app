package com.dicoding.picodiploma.githubuserapp.ui.favorit

import android.view.View
import com.dicoding.picodiploma.githubuserapp.db.FavoritEntity

interface FavoritListClickListener {
    fun onItemClicked(view: View, favorit: FavoritEntity)
}