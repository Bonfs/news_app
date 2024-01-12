package com.bonfs.newsapplication.news.domain.repository

import com.bonfs.newsapplication.news.domain.model.NewsModel
import com.bonfs.newsapplication.news.domain.model.ResponseResult

interface SessionRepository {
    suspend fun signIn(email: String, password: String): ResponseResult<NewsModel>
}