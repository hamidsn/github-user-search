package com.example.githubsearch.di

import com.example.githubsearch.repo.UserRepository
import com.example.githubsearch.repo.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

}