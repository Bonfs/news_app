package com.bonfs.newsapplication.news.domain.usecase

import com.bonfs.newsapplication.news.domain.model.NewsModel
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository

class FetchArticlesUseCase(
    private val repository: ArticleRepository
) {
    suspend fun execute(subject: String): ResponseResultStatus<NewsModel> =
        repository.fetchArticles(subject)
}