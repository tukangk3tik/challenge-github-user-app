package com.dicoding.picodiploma.githubuserapp.repositories

import android.app.Application
import com.dicoding.picodiploma.githubuserapp.db.FavoritDatabase
import com.dicoding.picodiploma.githubuserapp.db.FavoritEntity
import kotlinx.coroutines.flow.Flow

class FavoritRepository(application: Application) {

    private val db = FavoritDatabase.getInstance(application.applicationContext).getFavoritDao()
    private var _favoritList: Flow<List<FavoritEntity>>? = null

    companion object {
        @Volatile
        private var INSTANCE: FavoritRepository? = null

        fun getInstance(application: Application): FavoritRepository = INSTANCE ?: synchronized(this) {
            val instance = FavoritRepository(application)
            INSTANCE = instance
            instance
        }
    }

    init {
        _favoritList = db.getAll()
    }

    fun getUserFavorit(): Flow<List<FavoritEntity>>? = _favoritList

    suspend fun insertFavorit(favorit: FavoritEntity) {
        val getAvailable = db.findOneByUsername(favorit.username) //checking if data already inserted
        if (getAvailable == null) {
            db.insert(favorit)
        }
    }

    suspend fun deleteFavorit(favorit: FavoritEntity) {
        db.delete(favorit)
    }
}