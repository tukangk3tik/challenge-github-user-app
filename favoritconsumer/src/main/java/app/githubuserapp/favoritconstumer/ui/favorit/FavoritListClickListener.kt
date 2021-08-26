package com.dicoding.picodiploma.githubuserapp.ui.favorit

import android.view.View
import app.githubuserapp.favoritconstumer.db.FavoritEntity

interface FavoritListClickListener {

    fun onDeleteClicked(view: View, favorit: FavoritEntity)

}