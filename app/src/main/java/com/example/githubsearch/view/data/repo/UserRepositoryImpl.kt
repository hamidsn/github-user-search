package com.example.githubsearch.view.data.repo

import com.example.githubsearch.model.GithubUser
import com.example.githubsearch.remote.UserEndpoint
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