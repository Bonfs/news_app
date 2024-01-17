package com.bonfs.newsapplication.news.domain.usecase

import com.bonfs.newsapplication.news.data.model.ArticleDTO
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchArticlesUseCase(
    private val repository: ArticleRepository
) {
    suspend fun execute(subject: String): ResponseResultStatus<List<ArticleDTO>>
    = withContext(Dispatchers.IO) {
        when(val articlesResponse = repository.fetchArticles(subject)) {
            is ResponseResultStatus.Error -> articlesResponse
            is ResponseResultStatus.Success -> {
                val articles = articlesResponse.data.filter { it.source.id != null }
                ResponseResultStatus.Success(articles)
            }
        }
    }
}