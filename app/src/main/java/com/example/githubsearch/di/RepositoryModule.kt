package com.example.githubsearch.di

import com.example.githubsearch.view.data.repo.UserRepository
import com.example.githubsearch.view.data.repo.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

}