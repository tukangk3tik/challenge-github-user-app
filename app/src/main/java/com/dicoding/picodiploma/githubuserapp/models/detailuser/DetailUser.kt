package com.dicoding.picodiploma.githubuserapp.models.detailuser

import com.google.gson.annotations.SerializedName

data class DetailUser (
    @SerializedName("login")
    var username: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("avatar_url")
    var profileUrl: String? = null,

    @SerializedName("followers")
    var followers: Int? = null,

    @SerializedName("following")
    var following: Int? = null,

    @SerializedName("public_repos")
    var repos: Int? = null
)