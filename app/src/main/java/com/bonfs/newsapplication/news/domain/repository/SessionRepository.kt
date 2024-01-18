package com.bonfs.newsapplication.news.domain.repository

import com.bonfs.newsapplication.news.data.model.UserDTO
import com.bonfs.newsapplication.news.domain.model.NewsModel
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.model.User

interface SessionRepository {
    suspend fun signIn(email: String, password: String): ResponseResultStatus<User>
}