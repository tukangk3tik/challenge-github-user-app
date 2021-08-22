package com.dicoding.picodiploma.githubuserapp.githubusers.followers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.models.followers.DataFollowers
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.githubusers.DetailUsersActivity
import kotlinx.android.synthetic.main.item_followers_following.view.*

class FollowersCardAdapter(private val mActivity: FragmentActivity?): RecyclerView.Adapter<FollowersCardAdapter.ViewHolder> () {
    private val listFollowers = ArrayList<DataFollowers>()

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(followersList: DataFollowers){
            with(itemView) {
                txt_follower_following.text = followersList.username

                Glide.with(itemView.context)
                    .load(followersList.photoProfile)
                    .apply(RequestOptions().override(350,550))
                    .into(img_following_followers_photo)

                itemView.setOnClickListener {
                    //show toast on user click
                    Toast.makeText(itemView.context, followersList.username, Toast.LENGTH_SHORT).show()

                    if (mActivity != null){
                        val iDetailUsers = Intent(itemView.context, DetailUsersActivity::class.java)
                        val usernameFollowers = followersList.username
                        val photoProfileFollowers = followersList.photoProfile

                        val followersData = GithubUsers(
                            usernameFollowers,
                            photoProfileFollowers)

                        iDetailUsers.putExtra(DetailUsersActivity.EXTRA_USERNAME, followersData)
                        mActivity.finish()
                        itemView.context.startActivity(iDetailUsers)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowersCardAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_followers_following, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listFollowers.size

    override fun onBindViewHolder(holder: FollowersCardAdapter.ViewHolder, position: Int) {
        holder.bind(listFollowers[position])
    }

    fun setData(items: ArrayList<DataFollowers>) {
        listFollowers.clear()
        listFollowers.addAll(items)
        notifyDataSetChanged()
    }
}