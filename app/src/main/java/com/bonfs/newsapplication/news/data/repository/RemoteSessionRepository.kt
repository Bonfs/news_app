package com.bonfs.newsapplication.news.data.repository

import com.bonfs.newsapplication.news.data.model.UserDTO
import com.bonfs.newsapplication.news.domain.model.ErrorStatus
import com.bonfs.newsapplication.news.domain.model.NewsModel
import com.bonfs.newsapplication.news.domain.model.ResponseResult
import com.bonfs.newsapplication.news.domain.repository.SessionRepository
import java.lang.Exception

class RemoteSessionRepository: SessionRepository {
    override suspend fun signIn(email: String, password: String): ResponseResult<NewsModel> {
        Thread.sleep(1_000)

        return try {
            ResponseResult.Success(NewsModel(UserDTO("Matheus", "Bonfim")))
        } catch (e: Exception) {
            ResponseResult.Error(ErrorStatus.NetworkError(e))
        }
    }
}