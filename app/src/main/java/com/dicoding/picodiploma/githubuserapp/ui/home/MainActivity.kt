package com.dicoding.picodiploma.githubuserapp.ui.home

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.databinding.ActivityMainBinding
import com.dicoding.picodiploma.githubuserapp.db.FavoritEntity
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.ui.detail.DetailUsersActivity
import com.dicoding.picodiploma.githubuserapp.ui.favorit.FavoritActivity
import com.dicoding.picodiploma.githubuserapp.ui.favorit.FavoritViewModel
import com.dicoding.picodiploma.githubuserapp.ui.reminder.ReminderActivity
import com.dicoding.picodiploma.githubuserapp.utils.Resource
import com.dicoding.picodiploma.githubuserapp.utils.alarm.AlarmReceiver
import com.dicoding.picodiploma.githubuserapp.utils.alarm.AlarmStateManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), UserListClickListener {

    private var doubleBackToExitPressedOnce : Boolean = false

    private lateinit var activityMainBinding: ActivityMainBinding
    private val listUserViewModel : ListUserViewModel by viewModels()
    private val favoritViewModel : FavoritViewModel by viewModels()
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var alarmStateManager: AlarmStateManager

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        alarmStateManager = AlarmStateManager(this)
        alarmReceiver = AlarmReceiver()

        //set default reminder
        if (!alarmReceiver.isAlarmSet(this)){
            val time = "09:00"
            alarmReceiver.cancelAlarm(this)
            alarmStateManager.setAlarm(time)
            alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.APP_NAME, time)
        }

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
        adapter.listener = this //for listen click event from adapter
        activityMainBinding.rvUsers.layoutManager = LinearLayoutManager(this)
        activityMainBinding.rvUsers.adapter = adapter

        //observer
        listUserViewModel.getListUsers().observe(this, { response ->
            when(response) {
                is Resource.Success -> {
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
                is Resource.Error -> {
                    response.message?.let {
                        Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                        activityMainBinding.txtWelcome.text = getString(R.string.cant_connect)
                        activityMainBinding.txtWelcome.visibility = View.VISIBLE
                        showLoading(false)
                    }
                }
                is Resource.Loading -> {
                    adapter.clearData()
                    showLoading(true)
                }
            }
        })
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.go_to_favorit -> {
                val viewFav = Intent(this@MainActivity, FavoritActivity::class.java)
                startActivity(viewFav)
            }

            R.id.go_to_reminder -> {
                val reminder = Intent(this@MainActivity, ReminderActivity::class.java)
                startActivity(reminder)
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

    override fun onDeleteIconClicked(view: View, user: GithubUsers) {
        val newUser = FavoritEntity(user.username, user.photoProfile)
        favoritViewModel.insertFavorit(newUser)

        Toast.makeText(this,
            "${user.username} has been added to favorit",
            Toast.LENGTH_SHORT)
            .show()
    }

    override fun onItemClick(view: View, user: GithubUsers) {
        val iDetailUsers = Intent(this, DetailUsersActivity::class.java)
        iDetailUsers.putExtra(DetailUsersActivity.EXTRA_USERNAME, user)
        startActivity(iDetailUsers)
    }

}
