package com.dicoding.picodiploma.githubuserapp.ui.detail

import android.annotation.SuppressLint
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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.databinding.ActivityDetailUsersBinding
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.utils.Resource
import kotlinx.android.synthetic.main.activity_detail_users.*


class DetailUsersActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var username : String
    private lateinit var binding: ActivityDetailUsersBinding
    private val detailViewModel: DetailUserViewModel by viewModels()

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

        binding = ActivityDetailUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.htab_toolbar)
        setSupportActionBar(toolbar)

        if (supportActionBar != null) supportActionBar?.title =
            resources.getString(R.string.detail_user_toolbar_string)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val users: GithubUsers? = intent.getParcelableExtra(EXTRA_USERNAME)
        username = users?.username.toString()


        detailViewModel.setDetailUser(username)
        detailViewModel.getDetailUser().observe(this, { response ->
            when (response) {
                is Resource.Success -> {
                    with(binding) {
                        response.data?.let {
                            if (it.username == null) {
                                resetUserProfile()
                            } else {
                                detailTvUsername.text = it.username
                                detailTvName.text = it.name

                                "${it.repos.toString()} repositories".also {
                                    detailTvRepo.text = it
                                }

                                Glide.with(this@DetailUsersActivity)
                                    .load(it.profileUrl)
                                    .apply(RequestOptions().override(350, 550))
                                    .into(detailUserPhoto)

                                Glide.with(this@DetailUsersActivity).load(it.profileUrl)
                                    .into(object : SimpleTarget<Drawable?>() {
                                        override fun onResourceReady(
                                            resource: Drawable,
                                            transition: Transition<in Drawable?>?
                                        ) {
                                            profileBackground.background = resource
                                            profileBackground.background.alpha = 50
                                        }
                                    })
                            }
                            showLoading(false)
                        }
                    }
                }
                is Resource.Error -> {
                    response.message?.let {
                        Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                        resetUserProfile()
                    }
                    showLoading(false)
                }
                is Resource.Loading -> {
                    showLoading(true)
                    resetUserProfile()
                }
            }
        })

        val sectionsPagerAdapter = DetailSectionsPagerAdapter(username, supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f

        binding.detailTvRepo.isClickable = true
        binding.detailTvRepo.movementMethod = LinkMovementMethod.getInstance()
        binding.detailTvRepo.setOnClickListener(this)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarArea.visibility = View.VISIBLE
        } else {
            binding.progressBarArea.visibility = View.GONE
        }
    }

    private fun resetUserProfile() {
        binding.detailTvUsername.text = null
        binding.detailTvName.text = null
        binding.detailUserPhoto.setImageDrawable(null)
        binding.profileBackground.background = null
    }

    @SuppressLint("QueryPermissionsNeeded")
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

    @SuppressLint("QueryPermissionsNeeded")
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
