package com.example.githubsearch.model

enum class NetworkResult(val code: Int) {
    ERROR(-1),
    LOADING(1),
    SUCCESS(2),
    NOT_FOUND(3)
}