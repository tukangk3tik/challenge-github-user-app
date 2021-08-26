package com.dicoding.picodiploma.githubuserapp.utils

import com.dicoding.picodiploma.githubuserapp.models.detailuser.DetailUser
import com.dicoding.picodiploma.githubuserapp.models.userlist.*
import com.dicoding.picodiploma.githubuserapp.models.followers.*
import com.dicoding.picodiploma.githubuserapp.models.following.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("/search/users")
    suspend fun searchUsers(@Query("q") str: String?): Response<GithubUserList>

    @GET("/users/{username}")
    suspend fun profileUser(@Path("username") username: String?): Response<DetailUser>

    @GET("/users/{username}")
    fun profileUserCardView(@Path("username") username: String?): Call<DetailUser>

    @GET("/users/{username}/followers")
    fun followersUser(@Path("username") username: String?): Call<ArrayList<DataFollowers>>

    @GET("/users/{username}/following")
    fun followingUser(@Path("username") username: String?): Call<ArrayList<DataFollowing>>
}