package com.example.githubsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.example.githubsearch.data.model.GithubUser
import com.example.githubsearch.databinding.ItemUserDetailBinding

class UserAdapter(private val onUserClickListener: UserViewHolder.OnUserClickListener) :
    PagedListAdapter<GithubUser, UserViewHolder>(GithubUser.DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemUserDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, onUserClickListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}