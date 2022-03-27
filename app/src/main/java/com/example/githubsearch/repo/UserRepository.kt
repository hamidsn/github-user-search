package com.example.githubsearch.repo

import com.example.githubsearch.data.model.GithubUser
import io.reactivex.Observable

interface UserRepository {
    fun search(query: String, page: Int): Observable<List<GithubUser>>
}
