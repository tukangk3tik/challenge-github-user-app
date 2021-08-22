package com.dicoding.picodiploma.githubuserapp.githubusers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.models.detailuser.DetailUser
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.utils.ApiService
import com.dicoding.picodiploma.githubuserapp.utils.RetroInstance
import kotlinx.android.synthetic.main.activity_detail_users.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUsersActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var username : String

    private val retrofit = RetroInstance.buildRetrofit()
    private val api = retrofit.create(ApiService::class.java)

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater =  menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_users)

        val toolbar = findViewById<Toolbar>(R.id.htab_toolbar)
        setSupportActionBar(toolbar)

        if (supportActionBar != null) supportActionBar?.title = resources.getString(R.string.detail_user_toolbar_string)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val users: GithubUsers? = intent.getParcelableExtra(EXTRA_USERNAME)
        username = users?.username.toString()
        val srcPhotoProfile = users?.photoProfile.toString()
        loadUser(username,this)

        val sectionsPagerAdapter = DetailSectionsPagerAdapter(username, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f

        detail_tv_repo.isClickable = true
        detail_tv_repo.movementMethod = LinkMovementMethod.getInstance()
        detail_tv_repo.setOnClickListener(this)
    }

    private fun loadUser(username: String, context: Context){
        api.profileUser(username).enqueue(object: Callback<DetailUser> {
            override fun onFailure(call: Call<DetailUser>, t: Throwable) {

            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                when (response.code()) {
                    200 -> response.body().let {
                        val userName = response.body()?.username.toString()
                        detail_tv_username.text = "@${userName}"
                        detail_tv_name.text = "aka " + response.body()?.name.toString()
                        detail_tv_repo.text = response.body()?.repos.toString() + " repositories"

                        Glide.with(context)
                            .load(response.body()?.profileUrl)
                            .apply(RequestOptions().override(350, 550))
                            .into(detail_user_photo)

                        Glide.with(context).load(response.body()?.profileUrl)
                            .into(object : SimpleTarget<Drawable?>() {
                                override fun onResourceReady(
                                    resource: Drawable,
                                    transition: Transition<in Drawable?>?
                                ) {
                                    profile_background.background = resource
                                    profile_background.background.alpha = 50
                                }
                            })

                        }

                    408 -> Toast.makeText(context,"Request timeout", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.detail_tv_repo -> {
                val viewGithub = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/$username?tab=repositories"))
                if (intent.resolveActivity(packageManager) != null){
                    startActivity(viewGithub)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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

}
