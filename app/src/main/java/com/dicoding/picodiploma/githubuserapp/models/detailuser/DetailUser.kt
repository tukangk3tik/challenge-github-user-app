package com.dicoding.picodiploma.githubuserapp.models.detailuser

import com.google.gson.annotations.SerializedName

data class DetailUser (
    @SerializedName("login")
    var username: String?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("avatar_url")
    var profileUrl: String?,

    @SerializedName("followers")
    var followers: Int?,

    @SerializedName("following")
    var following: Int?,

    @SerializedName("public_repos")
    var repos: Int?
)