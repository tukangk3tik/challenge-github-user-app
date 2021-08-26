package com.dicoding.picodiploma.githubuserapp.ui.detail.followers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuserapp.models.followers.DataFollowers
import com.dicoding.picodiploma.githubuserapp.repositories.UserRepositories

class FollowersViewModel : ViewModel() {

    private var userRepository = UserRepositories.getInstance()
    private val _listFollowers: MutableLiveData<ArrayList<DataFollowers>> = userRepository.getUserFollowers()

    fun setFollowers(params: String) {
        userRepository.setFollowers(params)
    }

    fun getListFollowers(): LiveData<ArrayList<DataFollowers>> = _listFollowers
}

