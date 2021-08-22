package com.dicoding.picodiploma.githubuserapp.models.following

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataFollowing (
    @SerializedName("login")
    val username: String?,

    @SerializedName("avatar_url")
    var photoProfile : String?
) : Parcelable