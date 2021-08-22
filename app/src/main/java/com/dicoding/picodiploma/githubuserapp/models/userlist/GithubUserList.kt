package com.dicoding.picodiploma.githubuserapp.models.userlist

import com.google.gson.annotations.SerializedName

data class GithubUserList (
    @SerializedName("total_count")
    val totalCount: Int?,

    @SerializedName("items")
    val items: ArrayList<GithubUsers>
)