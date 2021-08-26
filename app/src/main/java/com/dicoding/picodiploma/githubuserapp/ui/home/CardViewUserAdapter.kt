package com.dicoding.picodiploma.githubuserapp.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.models.detailuser.DetailUser
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers
import com.dicoding.picodiploma.githubuserapp.ui.detail.DetailUsersActivity
import com.dicoding.picodiploma.githubuserapp.utils.ApiService
import com.dicoding.picodiploma.githubuserapp.utils.RetroInstance
import kotlinx.android.synthetic.main.item_github_users.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardViewUserAdapter : RecyclerView.Adapter<CardViewUserAdapter.CardViewViewHolder> () {
    private val listUsers = ArrayList<GithubUsers>()

    private val retrofit = RetroInstance.buildRetrofit()
    private val api = retrofit.create(ApiService::class.java)

    var listener: UserListClickListener? = null

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(userItems: GithubUsers) {

            with(itemView) {
                txt_name.text = userItems.username

                Glide.with(itemView.context)
                    .load(userItems.photoProfile)
                    .apply(RequestOptions().override(350, 550))
                    .into(img_users_photo)

                //get number of followers and repositories from profile
                api.profileUserCardView(userItems.username).enqueue(object : Callback<DetailUser> {
                    override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                        //d("load repo", "onFailure $t")
                    }

                    override fun onResponse(
                        call: Call<DetailUser>,
                        response: Response<DetailUser>
                    ) {
                        if (response.code() == 200) {
                            response.body()?.let {
                                tv_number_of_followers.text = response.body()?.followers.toString()
                                tv_number_of_repos.text = response.body()?.repos.toString()
                            }
                        } else {
                            tv_number_of_followers.text = "0"
                            tv_number_of_repos.text = "0"
                        }
                    }
                })

                star_icon.setOnClickListener {
                    listener?.onItemClicked(it, userItems)
                }
            }

            //action on recycler click
            itemView.setOnClickListener {
                val iDetailUsers = Intent(itemView.context, DetailUsersActivity::class.java)
                iDetailUsers.putExtra(DetailUsersActivity.EXTRA_USERNAME, userItems)
                itemView.context.startActivity(iDetailUsers)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_github_users, parent, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: ArrayList<GithubUsers>) {
        listUsers.clear()
        listUsers.addAll(items)
        notifyDataSetChanged()
    }

    fun clearData() {
        listUsers.clear()
    }
}

