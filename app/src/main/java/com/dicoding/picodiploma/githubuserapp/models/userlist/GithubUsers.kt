package com.dicoding.picodiploma.githubuserapp.models.userlist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUsers (
    @SerializedName("login")
    var username : String?,

    @SerializedName("avatar_url")
    var photoProfile : String?
) : Parcelable