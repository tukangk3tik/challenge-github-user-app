package com.dicoding.picodiploma.githubuserapp.ui.detail.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuserapp.models.following.DataFollowing
import com.dicoding.picodiploma.githubuserapp.repositories.UserRepositories

class FollowingViewModel : ViewModel() {

    private var userRepository = UserRepositories.getInstance()
    private val _listFollowing: LiveData<ArrayList<DataFollowing>> = userRepository.getUserFollowing()

    fun setFollowing(params: String) {
        userRepository.setFollowing(params)
    }

    fun getListFollowing(): LiveData<ArrayList<DataFollowing>> = _listFollowing

}