package com.example.githubsearch

import com.example.githubsearch.data.remote.NetworkInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkInterceptorTest {

    @Test
    @Throws(Exception::class)
    fun testHttpInterceptor() {
        val mockWebServer = MockWebServer()
        mockWebServer.start()
        mockWebServer.enqueue(MockResponse())
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(NetworkInterceptor()).build()
        okHttpClient.newCall(Request.Builder().url(mockWebServer.url("/")).build()).execute()
        val request: RecordedRequest = mockWebServer.takeRequest()
        assertEquals(null, request.getHeader("Pragma"))
        assertEquals(null, request.getHeader("Cache-Control"))
        mockWebServer.shutdown()
    }

}