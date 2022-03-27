package com.example.githubsearch.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.githubsearch.data.model.GithubUser
import com.example.githubsearch.data.model.NetworkResult
import com.example.githubsearch.data.model.NetworkStatus
import com.example.githubsearch.paging.PagingDataSourceFactory
import com.example.githubsearch.paging.PagingListener
import com.example.githubsearch.repo.UserRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel(),
    PagingListener<GithubUser> {
    private val mDisposable = CompositeDisposable()

    private var mNetworkStatus: MutableLiveData<NetworkStatus> = MutableLiveData()
    val networkStatus: LiveData<NetworkStatus>
        get() = mNetworkStatus
    val query: MutableLiveData<String> = MutableLiveData()
    val userPagedList: LiveData<PagedList<GithubUser>> =
        Transformations.switchMap(Transformations.distinctUntilChanged(query)) {
            search()
        }

    override fun onCleared() {
        super.onCleared()
        mDisposable.clear()
    }

    //GET/users: Default results per page = 30
    //https://docs.github.com/en/rest/reference/users#list-users
    private fun search(): LiveData<PagedList<GithubUser>> {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setInitialLoadSizeHint(30 * 2)
            .build()

        return LivePagedListBuilder(mPagingDataSourceFactory, config).build()
    }

    private val mPagingDataSourceFactory: PagingDataSourceFactory<GithubUser> =
        PagingDataSourceFactory(this)

    fun setQuery(value: String) {
        query.postValue(value)
    }

    override fun loadInitial(loadInitialCallback: PageKeyedDataSource.LoadInitialCallback<Int, GithubUser>) {
        if (query.value.isNullOrEmpty()) {
            mNetworkStatus.postValue(NetworkStatus(NetworkResult.ERROR))
            return
        }
        mNetworkStatus.postValue(NetworkStatus(NetworkResult.LOADING))
        val queryParam = query.value ?: ""

        mDisposable.add(
            userRepository.search(queryParam, 1)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.isEmpty()) {
                            mNetworkStatus.postValue(NetworkStatus(NetworkResult.NOT_FOUND))
                        } else {
                            mNetworkStatus.postValue(NetworkStatus(NetworkResult.SUCCESS))
                            loadInitialCallback.onResult(it, null, 2)
                        }
                    },
                    {
                        mNetworkStatus.postValue(NetworkStatus(NetworkResult.ERROR))
                    }
                )
        )
    }

    override fun loadAfter(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, GithubUser>
    ) {
        val queryParam = query.value ?: ""
        mDisposable.add(
            userRepository.search(queryParam, params.key)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it, params.key + 1)
                    },
                    {
                        Log.e("ERROR", it.message ?: "")
                    }
                )
        )
    }
}