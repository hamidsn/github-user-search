package com.example.githubsearch.di

import android.content.Context
import com.example.githubsearch.BuildConfig
import com.example.githubsearch.remote.NetworkInterceptor
import com.example.githubsearch.remote.UserEndpoint
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(applicationContext: Context): Retrofit {
        val httpCacheDirectory = File(applicationContext.cacheDir, "http-cache")
        val cacheSize: Long = 10 * 2014 * 1024
        val cache = Cache(httpCacheDirectory, cacheSize)

        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(NetworkInterceptor())
            .cache(cache)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserEndpoint(retrofit: Retrofit): UserEndpoint =
        retrofit.create(UserEndpoint::class.java)
}