package com.dicoding.picodiploma.githubuserapp.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "userfavorites")
data class FavoritEntity (
    @ColumnInfo(name = "username")
    var username : String?,

    @ColumnInfo(name = "avatar_url")
    var avatar_url : String?,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_fav")
    var id_fav : Long = 0
) : Parcelable