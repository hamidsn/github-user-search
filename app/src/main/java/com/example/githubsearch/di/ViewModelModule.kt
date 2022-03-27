package com.example.githubsearch.di

import com.example.githubsearch.repo.UserRepository
import com.example.githubsearch.view.viewmodel.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideMainViewModel(userRepository: UserRepository): MainViewModelFactory {
        return MainViewModelFactory(userRepository)
    }

}