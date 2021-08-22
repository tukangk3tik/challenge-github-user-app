package com.dicoding.picodiploma.githubuserapp.ui.home

import android.view.View
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers

interface UserListClickListener {
    fun onItemClicked(view: View, user: GithubUsers)
}