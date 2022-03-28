package com.example.githubsearch

import android.view.View
import com.example.githubsearch.data.model.NetworkResult
import com.example.githubsearch.data.model.NetworkStatus
import org.junit.Assert
import org.junit.Test

class NetworkStatusTest {

    @Test
    fun testNetworkStateSuccess() {
        val networkStatus = NetworkStatus(NetworkResult.SUCCESS)
        Assert.assertEquals(SUCCESS, networkStatus.status.progressbarVisibility)
    }

    @Test
    fun testNetworkStateLOADING() {
        val networkStatus = NetworkStatus(NetworkResult.LOADING)
        Assert.assertEquals(LOADING, networkStatus.status.progressbarVisibility)
    }

    companion object {
        const val SUCCESS = View.GONE
        const val NOT_FOUND = View.GONE
        const val ERROR = View.GONE
        const val LOADING = View.VISIBLE
    }

}