package com.dicoding.picodiploma.githubuserapp.githubusers.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.utils.ApiService
import com.dicoding.picodiploma.githubuserapp.utils.RetroInstance
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 */
class FollowingFragment(private val query: String) : Fragment() {
    private lateinit var followingViewModel : FollowingViewModel

    private val retrofit = RetroInstance.buildRetrofit()
    private val api = retrofit.create(ApiService::class.java)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = FollowingCardAdapter(activity)
        adapter.notifyDataSetChanged()

        rv_following.layoutManager = LinearLayoutManager(parentFragment?.context)
        rv_following.adapter = adapter

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)

        followingViewModel.setListFollowing(query, api)
        txt_notice_fragment_following.visibility = View.GONE

        followingViewModel.getListFollowing().observe(this, Observer { listFollowing ->
            rv_following.visibility = View.GONE
            txt_notice_fragment_following.visibility = View.GONE
            showLoading(true)

            if (listFollowing != null) {
                adapter.setData(listFollowing)

                if (listFollowing.size == 0) {
                    txt_notice_fragment_following.text = getString(R.string.dont_following_anyone)
                    txt_notice_fragment_following.visibility = View.VISIBLE
                } else {
                    rv_following.visibility = View.VISIBLE
                }
                showLoading(false)
            } else {
                runBlocking {
                    delay(1500L)
                    showLoading(false)

                    txt_notice_fragment_following.text = getString(R.string.cant_connect)
                    txt_notice_fragment_following.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar_following.visibility = View.VISIBLE
        } else {
            progressBar_following.visibility = View.GONE
        }
    }

}
