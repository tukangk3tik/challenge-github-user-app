package com.dicoding.picodiploma.githubuserapp.ui.detail

import android.view.View
import com.dicoding.picodiploma.githubuserapp.models.IFollowersFollowing

interface FollowingListClickListener {
    fun onItemClicked(view: View, user: IFollowersFollowing)
}