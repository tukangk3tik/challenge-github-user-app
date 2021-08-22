package com.dicoding.picodiploma.githubuserapp.models.userlist

import com.google.gson.annotations.SerializedName

data class FollowerDataList(
    @SerializedName("login")
    var listUsername : String?
)