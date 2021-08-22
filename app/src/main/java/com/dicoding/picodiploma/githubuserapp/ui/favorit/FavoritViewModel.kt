package com.dicoding.picodiploma.githubuserapp.ui.favorit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.githubuserapp.db.FavoritEntity
import com.dicoding.picodiploma.githubuserapp.repositories.FavoritRepository
import kotlinx.coroutines.launch

class FavoritViewModel(application: Application): AndroidViewModel(application) {

    private val _favoritRepository = FavoritRepository.getInstance(application)
    private var _favoritList: LiveData<List<FavoritEntity>>? = _favoritRepository.getUserFavorit()?.asLiveData()

    fun getListFavorit(): LiveData<List<FavoritEntity>>? = _favoritList

    fun insertFavorit(favorit: FavoritEntity) = viewModelScope.launch {
        _favoritRepository.insertFavorit(favorit)
    }

    fun deleteFavorit(favorit: FavoritEntity) = viewModelScope.launch {
        _favoritRepository.deleteFavorit(favorit)
    }
}