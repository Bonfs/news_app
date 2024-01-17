package com.bonfs.newsapplication.news.domain.model

sealed class ResponseResultStatus<out T : Any> {
    data class Success<out T : Any>(val data: T): ResponseResultStatus<T>()
    data class Error(val error: ErrorStatus): ResponseResultStatus<Nothing>()
}