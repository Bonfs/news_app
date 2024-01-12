package com.bonfs.newsapplication.news.domain.model

sealed class ErrorStatus {
    data class NetworkError(val throwable: Throwable): ErrorStatus()
}