package com.dicoding.picodiploma.githubuserapp.ui.detail.followers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.databinding.FragmentFollowersBinding
import com.dicoding.picodiploma.githubuserapp.models.IFollowersFollowing
import com.dicoding.picodiploma.githubuserapp.ui.detail.DetailUsersActivity
import com.dicoding.picodiploma.githubuserapp.ui.detail.FollowingListClickListener

class FollowersFragment (private val query: String) : Fragment(), FollowingListClickListener {

    private val followersViewModel : FollowersViewModel by activityViewModels()
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FollowersCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = FollowersCardAdapter()
        adapter.listener = this
        binding.rvFollowers.layoutManager = LinearLayoutManager(root.context)
        binding.rvFollowers.adapter = adapter

        followersViewModel.setFollowers(query)
        followersViewModel.getListFollowers().observe(viewLifecycleOwner, { listFollowers ->
            if (listFollowers != null) {
                adapter.setData(listFollowers)

                if (listFollowers.size == 0) {
                    binding.txtNoticeFragmentFollowers.text = getString(R.string.dont_following_anyone)
                    binding.txtNoticeFragmentFollowers.visibility = View.VISIBLE
                } else {
                    binding.rvFollowers.visibility = View.VISIBLE
                    binding.txtNoticeFragmentFollowers.visibility = View.GONE
                }
            } else {
                binding.txtNoticeFragmentFollowers.text = getString(R.string.cant_connect)
                binding.txtNoticeFragmentFollowers.visibility = View.VISIBLE
            }
            showLoading(false)
        })
        // Inflate the layout for this fragment
        return root
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarFollowers.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowers.visibility = View.GONE
        }
    }

    override fun onItemClicked(view: View, user: IFollowersFollowing) {
        val iDetailUsers = Intent(activity, DetailUsersActivity::class.java)
        iDetailUsers.putExtra(DetailUsersActivity.EXTRA_USERNAME, user.username)
        activity?.startActivity(iDetailUsers)
    }
}
