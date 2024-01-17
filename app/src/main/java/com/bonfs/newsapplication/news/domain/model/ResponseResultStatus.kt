package com.bonfs.newsapplication.news.domain.model

sealed class ResponseResultStatus<out T : Any> {
    data class Success(val data: NewsModel): ResponseResultStatus<NewsModel>()
    data class Error(val error: ErrorStatus): ResponseResultStatus<Nothing>()
}