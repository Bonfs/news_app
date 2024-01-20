package com.bonfs.newsapplication.news.domain.model

sealed class ResponseResultStatus<out R> {
    data class Success<out T : Any>(val data: T): ResponseResultStatus<T>()
    data class Error(val error: ErrorStatus): ResponseResultStatus<Nothing>()
}