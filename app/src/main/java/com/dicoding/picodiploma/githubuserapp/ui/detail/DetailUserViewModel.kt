package com.dicoding.picodiploma.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.githubuserapp.models.detailuser.DetailUser
import com.dicoding.picodiploma.githubuserapp.repositories.UserRepositories
import com.dicoding.picodiploma.githubuserapp.utils.Resource
import kotlinx.coroutines.launch
import java.io.IOException

class DetailUserViewModel : ViewModel() {

    private var userRepository = UserRepositories.getInstance()
    private val _detailUser = MutableLiveData<Resource<DetailUser>>()

    fun setDetailUser(params: String) = viewModelScope.launch {
        _detailUser.postValue(Resource.Loading())
        try {
            val user = userRepository.getDetailUser(params)
            _detailUser.postValue(Resource.Success(user))
        } catch (e: IOException) {
            _detailUser.postValue(Resource.Error(e.message))
        }
    }

    fun getDetailUser(): LiveData<Resource<DetailUser>> = _detailUser

}