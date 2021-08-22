package com.dicoding.picodiploma.githubuserapp.repositories

import android.util.Log
import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUserList
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.utils.ApiService
import com.dicoding.picodiploma.githubuserapp.utils.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class GithubUserRepositories {

    private val retrofit = RetroInstance.buildRetrofit()
    private var api: ApiService = retrofit.create(ApiService::class.java)

    companion object {
        @Volatile
        private var INSTANCE: GithubUserRepositories? = null

        fun getInstance(): GithubUserRepositories = INSTANCE ?: synchronized(this) {
            val instance = GithubUserRepositories()
            INSTANCE = instance
            instance
        }
    }

    suspend fun findUser(params: String): ArrayList<GithubUsers>? {
        val response = api.searchUsers(params)
        if (response.isSuccessful) {
            return response.body()?.items
        } else {
            throw Exception(response.message())
        }
    }

}