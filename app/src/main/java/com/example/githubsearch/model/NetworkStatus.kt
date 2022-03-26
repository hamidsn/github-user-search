package com.example.githubsearch.model

data class NetworkStatus(
    var status: Int,
    var showMessage: Boolean = false,
    var message: String = ""
)