package com.dicoding.picodiploma.githubuserapp.ui.detail.following

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.databinding.FragmentFollowingBinding
import com.dicoding.picodiploma.githubuserapp.models.IFollowersFollowing
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.ui.detail.DetailUsersActivity
import com.dicoding.picodiploma.githubuserapp.ui.detail.FollowingListClickListener

class FollowingFragment(private val query: String) : Fragment(), FollowingListClickListener {

    private val followingViewModel: FollowingViewModel by activityViewModels()
    private var _binding: FragmentFollowingBinding? = null
    private lateinit var adapter: FollowingCardAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = FollowingCardAdapter()
        adapter.listener = this
        binding.rvFollowing.layoutManager = LinearLayoutManager(root.context)
        binding.rvFollowing.adapter = adapter

        followingViewModel.setFollowing(query)
        followingViewModel.getListFollowing().observe(viewLifecycleOwner, { listFollowing ->

            if (listFollowing != null) {
                adapter.setData(listFollowing)

                if (listFollowing.size == 0) {
                    binding.txtNoticeFragmentFollowing.text = getString(R.string.dont_following_anyone)
                    binding.txtNoticeFragmentFollowing.visibility = View.VISIBLE
                } else {
                    binding.rvFollowing.visibility = View.VISIBLE
                    binding.txtNoticeFragmentFollowing.visibility = View.GONE
                }
            } else {
                binding.txtNoticeFragmentFollowing.text = getString(R.string.cant_connect)
                binding.txtNoticeFragmentFollowing.visibility = View.VISIBLE
            }

            showLoading(false)
        })
        // Inflate the layout for this fragment
        return root
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarFollowing.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowing.visibility = View.GONE
        }
    }

    override fun onItemClicked(view: View, user: IFollowersFollowing) {
        val dataUser = GithubUsers(user.username, user.photoProfile)
        val iDetailUsers = Intent(activity, DetailUsersActivity::class.java)
        iDetailUsers.putExtra(DetailUsersActivity.EXTRA_USERNAME, dataUser)
        activity?.startActivity(iDetailUsers)
        activity?.finish()
    }

}
