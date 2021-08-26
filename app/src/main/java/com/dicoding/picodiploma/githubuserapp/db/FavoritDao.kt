package com.dicoding.picodiploma.githubuserapp.db

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritDao {
    @Query("SELECT * FROM userfavorites")
    fun getAll(): Flow<List<FavoritEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(favorit: FavoritEntity): Long

    @Delete
    suspend fun delete(favorit: FavoritEntity)

    @Query("DELETE FROM userfavorites WHERE id = :idFavorit")
    suspend fun deleteById(idFavorit: Int): Int

    @Query("UPDATE userfavorites SET username = :userName, avatar_url = :avatarUrl WHERE id = :id")
    suspend fun update(id: Long, userName: String, avatarUrl: String)

    @Query("SELECT * FROM userfavorites WHERE username = :userName")
    suspend fun findOneByUsername(userName: String?): FavoritEntity?

    @Query("SELECT * FROM userfavorites WHERE id = :id")
    suspend fun findOneById(id: Int): FavoritEntity?

    @Query("SELECT * FROM userfavorites")
    fun getAllProvider(): Cursor

}