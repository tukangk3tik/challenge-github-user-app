package com.dicoding.picodiploma.githubuserapp.ui.home

import android.view.View
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers

interface UserListClickListener {
    fun onDeleteIconClicked(view: View, user: GithubUsers)

    fun onItemClick(view: View, user: GithubUsers)
}