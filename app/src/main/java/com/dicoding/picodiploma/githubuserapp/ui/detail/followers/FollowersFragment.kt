package com.dicoding.picodiploma.githubuserapp.ui.detail.followers

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
import kotlinx.android.synthetic.main.fragment_followers.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 */
class FollowersFragment (private val query: String) : Fragment() {
    private lateinit var followersViewModel : FollowersViewModel

    private val retrofit = RetroInstance.buildRetrofit()
    private val api = retrofit.create(ApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = FollowersCardAdapter(activity)
        adapter.notifyDataSetChanged()

        rv_followers.layoutManager = LinearLayoutManager(parentFragment?.context)
        rv_followers.adapter = adapter

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersViewModel::class.java)

        followersViewModel.setListFollowers(query, api)
        txt_notice_fragment_followers.visibility = View.GONE

        followersViewModel.getListFollowers().observe(this, Observer { listFollowers ->
            rv_followers.visibility = View.GONE
            txt_notice_fragment_followers.visibility = View.GONE
            showLoading(true)

            if (listFollowers != null) {
                adapter.setData(listFollowers)

                if (listFollowers.size == 0) {
                    txt_notice_fragment_followers.text = getString(R.string.dont_have_followers)
                    txt_notice_fragment_followers.visibility = View.VISIBLE
                } else {
                    rv_followers.visibility = View.VISIBLE
                }
                showLoading(false)
            } else {
                runBlocking {
                    delay(1500L)
                    showLoading(false)

                    txt_notice_fragment_followers.text = getString(R.string.cant_connect)
                    txt_notice_fragment_followers.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar_followers.visibility = View.VISIBLE
        } else {
            progressBar_followers.visibility = View.GONE
        }
    }
}
