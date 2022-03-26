package com.example.githubsearch.view.viewmodel

import androidx.lifecycle.ViewModel
import com.example.githubsearch.view.data.repo.UserRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

}