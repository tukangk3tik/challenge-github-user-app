package com.dicoding.picodiploma.githubuserapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuserapp.databinding.ActivityMainBinding
import com.dicoding.picodiploma.githubuserapp.favorit.FavoritActivity
import com.dicoding.picodiploma.githubuserapp.githubusers.CardViewUserAdapter
import com.dicoding.picodiploma.githubuserapp.githubusers.UserListResource
import com.dicoding.picodiploma.githubuserapp.githubusers.ListUserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce : Boolean = false

    private lateinit var activityMainBinding: ActivityMainBinding
    private val listUserViewModel : ListUserViewModel by viewModels()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        activityMainBinding.svSearchUser.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        activityMainBinding.svSearchUser.queryHint = resources.getString(R.string.search_user_hint)
        activityMainBinding.svSearchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                activityMainBinding.txtWelcome.visibility = View.GONE
                activityMainBinding.rvUsers.visibility = View.GONE

                listUserViewModel.findUsers(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        val adapter = CardViewUserAdapter()
        activityMainBinding.rvUsers.layoutManager = LinearLayoutManager(this)
        activityMainBinding.rvUsers.adapter = adapter

        //observer
        listUserViewModel.getListUsers().observe(this, { response ->
            when(response) {
                is UserListResource.Success -> {
                    response.data?.let {
                        if (it.size == 0) {
                            activityMainBinding.txtWelcome.text = getString(R.string.cant_find_user)
                            activityMainBinding.txtWelcome.visibility = View.VISIBLE
                        } else {
                            adapter.setData(it)
                            activityMainBinding.rvUsers.visibility = View.VISIBLE
                        }
                        showLoading(false)
                    }
                }
                is UserListResource.Error -> {
                    response.message?.let {
                        Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                        activityMainBinding.txtWelcome.text = getString(R.string.cant_connect)
                        activityMainBinding.txtWelcome.visibility = View.VISIBLE
                        showLoading(false)
                    }
                }
                is UserListResource.Loading -> showLoading(true)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.go_to_favorit -> {
                val viewFav = Intent(this@MainActivity, FavoritActivity::class.java)
                startActivity(viewFav)
            }

            R.id.go_to_github -> {
                val viewGithub = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com"))
                if (intent.resolveActivity(packageManager) != null){
                    startActivity(viewGithub)
                }
            }

            R.id.change_language -> {
                val changeLanguage = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(changeLanguage)
            }
        }
        return false
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true)
        }

        if (!doubleBackToExitPressedOnce) Toast.makeText(this, "Tap once more for exit", Toast.LENGTH_SHORT).show()
        doubleBackToExitPressedOnce = true
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 1500)
    }

    fun showLoading(state: Boolean) {
        if (state) {
            activityMainBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityMainBinding.progressBar.visibility = View.GONE
        }
    }

}
