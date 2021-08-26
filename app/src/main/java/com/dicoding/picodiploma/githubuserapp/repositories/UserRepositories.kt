package com.dicoding.picodiploma.githubuserapp.repositories

import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.githubuserapp.models.detailuser.DetailUser
import com.dicoding.picodiploma.githubuserapp.models.followers.DataFollowers
import com.dicoding.picodiploma.githubuserapp.models.following.DataFollowing
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.utils.ApiService
import com.dicoding.picodiploma.githubuserapp.utils.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositories {

    private val retrofit = RetroInstance.buildRetrofit()
    private var api: ApiService = retrofit.create(ApiService::class.java)

    private val userFollowers = MutableLiveData<ArrayList<DataFollowers>>()
    private val userFollowing = MutableLiveData<ArrayList<DataFollowing>>()
    private val detailUser = MutableLiveData<DetailUser>()

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
            null
        }
    }

    suspend fun getDetailUser(params: String): DetailUser? {
        val response = api.profileUser(params)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    fun setFollowers(username: String) {
        userFollowers.postValue(ArrayList())
        api.followersUser(username).enqueue(object : Callback<ArrayList<DataFollowers>> {
            override fun onResponse(
                call: Call<ArrayList<DataFollowers>>,
                response: Response<ArrayList<DataFollowers>>
            ) {
                if (response.code() == 200) {
                    userFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<DataFollowers>>, t: Throwable) {
                d("test", "onFailure $t")
                userFollowers.postValue(ArrayList())
            }
        })
    }

    fun getUserFollowers(): MutableLiveData<ArrayList<DataFollowers>> = userFollowers

    fun setFollowing(username: String) {
        userFollowing.postValue(ArrayList())
        api.followingUser(username).enqueue(object : Callback<ArrayList<DataFollowing>> {
            override fun onResponse(
                call: Call<ArrayList<DataFollowing>>,
                response: Response<ArrayList<DataFollowing>>
            ) {
                if (response.code() == 200) {
                    d("test", "Response: ${response.body()}")
                    userFollowing.postValue(response.body())
                    d("RESULT_TEST", "Response: ${userFollowing})}")
                }
            }

            override fun onFailure(call: Call<ArrayList<DataFollowing>>, t: Throwable) {
                d("test", "onFailure $t")
                userFollowing.postValue(ArrayList())
            }
        })
    }

    fun getUserFollowing(): MutableLiveData<ArrayList<DataFollowing>> = userFollowing
}