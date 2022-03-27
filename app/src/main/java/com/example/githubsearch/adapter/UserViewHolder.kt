package com.example.githubsearch.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.data.model.GithubUser
import com.example.githubsearch.databinding.ItemUserDetailBinding

class UserViewHolder(
    private val binding: ItemUserDetailBinding,
    private val onUserClickListener: OnUserClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: GithubUser) {
        binding.user = user

        binding.root.setOnClickListener {
            onUserClickListener.onUserClick(user)
        }
    }

    interface OnUserClickListener {
        fun onUserClick(user: GithubUser)
    }
}