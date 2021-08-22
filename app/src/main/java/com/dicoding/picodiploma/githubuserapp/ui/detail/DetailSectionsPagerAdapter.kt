package com.dicoding.picodiploma.githubuserapp.ui.detail

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.picodiploma.githubuserapp.ui.detail.followers.FollowersFragment
import com.dicoding.picodiploma.githubuserapp.ui.detail.following.FollowingFragment

class DetailSectionsPagerAdapter (
        private val username: String,
        fm: FragmentManager
    ): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titles = arrayOf("Followers", "Following")

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowersFragment(username)
            1 -> fragment = FollowingFragment(username)
        }

        return fragment as Fragment
    }

    override fun getCount(): Int = 2

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}