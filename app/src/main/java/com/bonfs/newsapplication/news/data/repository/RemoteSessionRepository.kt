package com.bonfs.newsapplication.news.data.repository

import com.bonfs.newsapplication.news.data.model.UserDTO
import com.bonfs.newsapplication.news.domain.model.ErrorStatus
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.model.User
import com.bonfs.newsapplication.news.domain.repository.SessionRepository

class RemoteSessionRepository : SessionRepository {
    override suspend fun signIn(email: String, password: String): ResponseResultStatus<User> {
        Thread.sleep(1_000)

        return try {
            ResponseResultStatus.Success(UserDTO("Matheus", "Bonfim").toDomain())
        } catch (e: Exception) {
            ResponseResultStatus.Error(ErrorStatus.NetworkError(e))
        }
    }
}