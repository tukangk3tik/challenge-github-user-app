package com.dicoding.picodiploma.githubuserapp.models.followers

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataFollowers (
    @SerializedName("login")
    val username: String?,

    @SerializedName("avatar_url")
    var photoProfile : String?
) : Parcelable