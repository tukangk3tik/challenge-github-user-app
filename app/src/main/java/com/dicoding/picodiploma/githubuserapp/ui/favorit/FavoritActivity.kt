package com.dicoding.picodiploma.githubuserapp.ui.favorit

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuserapp.databinding.ActivityFavoritBinding
import com.dicoding.picodiploma.githubuserapp.db.FavoritEntity
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.ui.detail.DetailUsersActivity

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

    override fun onDeleteClicked(view: View, favorit: FavoritEntity) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Delete User")
        alertDialogBuilder
            .setMessage("Are you sure delete ${favorit.username} from favorit?")
            .setCancelable(false)
            .setPositiveButton("Confirm") { dialog, id ->
                favoritViewModel.deleteFavorit(favorit)

                Toast.makeText(this,
                    "${favorit.username} has been removed to favorit",
                    Toast.LENGTH_SHORT)
                    .show()
            }
            .setNegativeButton("Cancel") { dialog, id -> dialog.cancel() }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onItemClicked(view: View, favorit: FavoritEntity) {
        val user = GithubUsers(favorit.username, favorit.avatar_url)
        val iDetailUsers = Intent(this, DetailUsersActivity::class.java)
        iDetailUsers.putExtra(DetailUsersActivity.EXTRA_USERNAME, user)
        startActivity(iDetailUsers)
    }
}