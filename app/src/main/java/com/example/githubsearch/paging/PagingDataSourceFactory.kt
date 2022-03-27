package com.example.githubsearch.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class PagingDataSourceFactory<Model : Any>(
    private val pagingListener: PagingListener<Model>
) : DataSource.Factory<Int, Model>() {

    private val pagingDataSource = MutableLiveData<UserPagingDataSource<Model>>()

    override fun create(): DataSource<Int, Model> {
        val dataSource = UserPagingDataSource(pagingListener)
        pagingDataSource.postValue(dataSource)

        return dataSource
    }
}