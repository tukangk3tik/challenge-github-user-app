package com.dicoding.picodiploma.githubuserapp.models.following

import android.os.Parcelable
import com.dicoding.picodiploma.githubuserapp.models.IFollowersFollowing
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataFollowing (
    @SerializedName("login")
    override val username: String?,

    @SerializedName("avatar_url")
    override val photoProfile: String?
) : IFollowersFollowing, Parcelable