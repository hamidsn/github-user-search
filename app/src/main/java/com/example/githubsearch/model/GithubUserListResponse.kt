package com.example.githubsearch.model

import com.google.gson.annotations.SerializedName

data class GithubUserListResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<GithubUser>,
    @SerializedName("total_count")
    val totalCount: Int
)