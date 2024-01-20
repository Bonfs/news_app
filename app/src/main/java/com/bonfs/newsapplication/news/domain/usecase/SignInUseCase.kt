package com.bonfs.newsapplication.news.domain.usecase

import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.model.User
import com.bonfs.newsapplication.news.domain.repository.SessionRepository

class SignInUseCase(
    private val repository: SessionRepository
) {
    suspend fun execute(email: String, password: String): ResponseResultStatus<User> {
        return repository.signIn(email, password)
    }
}