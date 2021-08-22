package com.dicoding.picodiploma.githubuserapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoritEntity::class], version = 2, exportSchema = false)
abstract class FavoritDatabase: RoomDatabase() {

    abstract fun getFavoritDao(): FavoritDao

    companion object {
        fun getInstance(context: Context): FavoritDatabase {
            return synchronized(FavoritDatabase::class) {
                Room.databaseBuilder(context.applicationContext,FavoritDatabase::class.java, "userfavorites.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }

}