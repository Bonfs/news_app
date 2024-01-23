package com.bonfs.newsapplication.news.domain.usecase

import com.bonfs.newsapplication.news.domain.model.Article
import com.bonfs.newsapplication.news.domain.model.ErrorStatus
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetArticleByAuthorUseCase(
    private val repository: ArticleRepository
) {
    suspend fun execute(authorName: String): Article = withContext(Dispatchers.IO) {
        when(val article = repository.getArticleByAuthorName(authorName)) {
            is ResponseResultStatus.Error -> throw (article.error as ErrorStatus.AuthorNotFound).throwable
            is ResponseResultStatus.Success -> article.data
        }
    }
}