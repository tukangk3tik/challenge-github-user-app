package com.dicoding.picodiploma.githubuserapp.favorit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.githubuserapp.R

class FavoritActivity : AppCompatActivity() {

    private lateinit var favModelView: FavoritViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorit)

        val adapter = CardViewFavoritAdapter()
        adapter.notifyDataSetChanged()

        favModelView = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FavoritViewModel::class.java)
        favModelView.setInit(applicationContext)

        favModelView.setDataFav()

        favModelView.getDataFav().observe(this, Observer { listFav ->
            if (listFav != null) {
                adapter.setData(listFav)
            }
        })
    }
}