package com.example.githubsearch.view.data.repo

import com.example.githubsearch.model.GithubUser
import io.reactivex.Observable


interface UserRepository {
    fun search(query: String, page: Int): Observable<List<GithubUser>>
}
