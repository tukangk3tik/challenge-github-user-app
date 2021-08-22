package com.dicoding.picodiploma.githubuserapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface FavoritDao {
    @Query("SELECT * FROM userfavorites")
    fun getAll(): List<FavoritEntity>

    @Insert(onConflict = REPLACE)
    fun insert(favorit: List<FavoritEntity>)

    @Delete
    fun delete(favorit: FavoritEntity)

    @Query("UPDATE userfavorites SET username = :userName, avatar_url = :avatarUrl WHERE id_fav = :idFav")
    fun update(idFav: Long, userName: String, avatarUrl: String)

    @Query("SELECT * FROM userfavorites WHERE username LIKE :userName")
    fun findByUsername(userName: String?): List<FavoritEntity>
}