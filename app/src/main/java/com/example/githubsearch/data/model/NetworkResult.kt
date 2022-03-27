package com.example.githubsearch.data.model

import android.view.View

enum class NetworkResult(val progressbarVisibility: Int) {
    ERROR(View.GONE),
    LOADING(View.VISIBLE),
    SUCCESS(View.GONE),
    NOT_FOUND(View.GONE)
}