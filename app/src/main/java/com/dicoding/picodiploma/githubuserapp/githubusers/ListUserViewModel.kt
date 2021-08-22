package com.dicoding.picodiploma.githubuserapp.githubusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.repositories.GithubUserRepositories
import kotlinx.coroutines.launch
import java.io.IOException


class ListUserViewModel : ViewModel() {

    private var userRepository = GithubUserRepositories.getInstance()
    private val _listUsers = MutableLiveData<UserListResource<ArrayList<GithubUsers>>>()

    fun findUsers(params: String) = viewModelScope.launch {
        _listUsers.postValue(UserListResource.Loading())
        try {
            val listUser = userRepository.findUser(params)
            _listUsers.postValue(UserListResource.Success(listUser))
        } catch (e: IOException) {
            _listUsers.postValue(UserListResource.Error(e.message))
        }
    }

    fun getListUsers(): LiveData<UserListResource<ArrayList<GithubUsers>>> = _listUsers

}