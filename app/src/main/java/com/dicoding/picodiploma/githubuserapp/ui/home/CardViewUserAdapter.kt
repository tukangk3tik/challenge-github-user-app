package com.dicoding.picodiploma.githubuserapp.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuserapp.databinding.ItemGithubUsersBinding
import com.dicoding.picodiploma.githubuserapp.models.detailuser.DetailUser
import com.dicoding.picodiploma.githubuserapp.models.userlist.GithubUsers
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

    inner class CardViewViewHolder(private val binding: ItemGithubUsersBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(userItems: GithubUsers) {

            with(binding) {
                txtName.text = userItems.username

                Glide.with(itemView.context)
                    .load(userItems.photoProfile)
                    .apply(RequestOptions().override(350, 550))
                    .into(imgUsersPhoto)

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
                                tvNumberOfFollowers.text = response.body()?.followers.toString()
                                tvNumberOfRepos.text = response.body()?.repos.toString()
                            }
                        } else {
                            tvNumberOfFollowers.text = "0"
                            tvNumberOfRepos.text = "0"
                        }
                    }
                })

                starIcon.setOnClickListener {
                    listener?.onDeleteIconClicked(it, userItems)
                }
            }

            //action on recycler click
            itemView.setOnClickListener {
                listener?.onItemClick(it, userItems)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewViewHolder {
        val binding = ItemGithubUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewViewHolder(binding)
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

