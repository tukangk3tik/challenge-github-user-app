package com.dicoding.picodiploma.githubuserapp.githubusers.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuserapp.models.following.DataFollowing
import com.dicoding.picodiploma.githubuserapp.utils.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel(): ViewModel() {
    private val listFollowing = MutableLiveData<ArrayList<DataFollowing>> ()

    fun setListFollowing(params: String, api: ApiService){
        api.followingUser(params).enqueue(object : Callback<ArrayList<DataFollowing>> {
            override fun onResponse(
                call: Call<ArrayList<DataFollowing>>,
                response: Response<ArrayList<DataFollowing>>
            ) {
                //d("test", "Response: ${response.body().items}")
                if (response.code() == 200) {
                    listFollowing.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<DataFollowing>>, t: Throwable) {
                Log.d("test", "onFailure $t")
                listFollowing.postValue(null)
            }
        })

    }

    fun getListFollowing() : LiveData<ArrayList<DataFollowing>> = listFollowing

}