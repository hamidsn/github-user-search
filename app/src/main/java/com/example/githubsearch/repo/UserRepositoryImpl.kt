package com.example.githubsearch.repo

import com.example.githubsearch.data.model.GithubUser
import com.example.githubsearch.data.remote.UserEndpoint
import io.reactivex.Observable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userEndpoint: UserEndpoint) :
    UserRepository {

    override fun search(query: String, page: Int): Observable<List<GithubUser>> {
        return userEndpoint.search(query, page)
            .flatMap {
                Observable.just(it.items)
            }
    }
}