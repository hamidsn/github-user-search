package com.example.githubsearch.paging

import androidx.paging.PageKeyedDataSource

interface PagingListener<Model> {

    fun loadInitial(loadInitialCallback: PageKeyedDataSource.LoadInitialCallback<Int, Model>)

    fun loadAfter(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, Model>
    )
}