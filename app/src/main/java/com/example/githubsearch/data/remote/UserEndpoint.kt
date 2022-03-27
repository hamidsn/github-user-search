package com.example.githubsearch.data.remote

import com.example.githubsearch.data.model.GithubUserListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface UserEndpoint {

    @GET("search/users")
    fun search(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Observable<GithubUserListResponse>
}