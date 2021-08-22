package com.dicoding.picodiploma.githubuserapp.repositories

import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.utils.ApiService
import com.dicoding.picodiploma.githubuserapp.utils.RetroInstance

class UserRepositories {

    private val retrofit = RetroInstance.buildRetrofit()
    private var api: ApiService = retrofit.create(ApiService::class.java)

    companion object {
        @Volatile
        private var INSTANCE: UserRepositories? = null

        fun getInstance(): UserRepositories = INSTANCE ?: synchronized(this) {
            val instance = UserRepositories()
            INSTANCE = instance
            instance
        }
    }

    suspend fun findUser(params: String): ArrayList<GithubUsers>? {
        val response = api.searchUsers(params)
        return if (response.isSuccessful) {
            response.body()?.items
        } else {
            //throw Exception(response.message())
            null
        }
    }

    fun getDetailUser(username: String) {

    }
}