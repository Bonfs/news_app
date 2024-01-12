package com.bonfs.newsapplication.news.domain.model

sealed class ResponseResult<out T : Any> {
    data class Success(val responseData: NewsModel): ResponseResult<NewsModel>()
    data class Error(val error: ErrorStatus): ResponseResult<Nothing>()
}