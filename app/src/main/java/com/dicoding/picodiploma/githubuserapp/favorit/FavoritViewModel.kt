package com.dicoding.picodiploma.githubuserapp.favorit

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuserapp.db.FavoritDao
import com.dicoding.picodiploma.githubuserapp.db.FavoritDatabase
import com.dicoding.picodiploma.githubuserapp.db.FavoritEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoritViewModel(): ViewModel() {
    private val listFav = MutableLiveData<ArrayList<FavoritEntity>>()
    //private lateinit var mContext: Context
    private lateinit var db : FavoritDao

    fun setInit(context: Context) {
        //mContext = context
        db = FavoritDatabase.getInstance(context).getFavoritDao()
    }

    fun setDataFav() {
        GlobalScope.launch (Dispatchers.Main) {
            //var resultList = ArrayList<FavoritEntity>()
            val deferredResult = async (Dispatchers.IO) {
                val result = db.getAll()
                val resultList = ArrayList<FavoritEntity>()
                result.forEach {
                    resultList.add(FavoritEntity(it.username, it.avatar_url, it.id_fav))
                    //Log.d("FavViewModel", "Result: ${it.username}")
                }
            }

            val resultFav = deferredResult.await()

            /*if (resultFav.size > 0) {
                adapter.listNotes = notes
                listFav.postValue(resultList)
            }*/
        }
    }

    fun getDataFav(): LiveData<ArrayList<FavoritEntity>> = listFav
}