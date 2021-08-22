package com.dicoding.picodiploma.githubuserapp.ui.detail.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuserapp.models.followers.DataFollowers
import com.dicoding.picodiploma.githubuserapp.utils.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel(): ViewModel() {
    private val listFollowers = MutableLiveData<ArrayList<DataFollowers>> ()

    fun setListFollowers(params: String, api: ApiService){
        api.followersUser(params).enqueue(object : Callback<ArrayList<DataFollowers>> {
            override fun onResponse(
                call: Call<ArrayList<DataFollowers>>,
                response: Response<ArrayList<DataFollowers>>
            ) {
                if (response.code() == 200) {
                    listFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<DataFollowers>>, t: Throwable) {
                Log.d("test", "onFailure $t")
                listFollowers.postValue(null)
            }
        })

    }

    fun getListFollowers() : LiveData<ArrayList<DataFollowers>> = listFollowers

}

