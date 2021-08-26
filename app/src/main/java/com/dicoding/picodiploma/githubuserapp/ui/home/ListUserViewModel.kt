package com.dicoding.picodiploma.githubuserapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.repositories.UserRepositories
import com.dicoding.picodiploma.githubuserapp.utils.Resource
import kotlinx.coroutines.launch
import java.io.IOException

class ListUserViewModel : ViewModel() {

    private var userRepository = UserRepositories.getInstance()
    private val _listUsers = MutableLiveData<Resource<ArrayList<GithubUsers>>>()

    fun findUsers(params: String) = viewModelScope.launch {
        _listUsers.postValue(Resource.Loading())
        try {
            val listUser = userRepository.findUser(params)
            _listUsers.postValue(Resource.Success(listUser))
        } catch (e: IOException) {
            _listUsers.postValue(Resource.Error(e.message))
        }
    }

    fun getListUsers(): LiveData<Resource<ArrayList<GithubUsers>>> = _listUsers

}