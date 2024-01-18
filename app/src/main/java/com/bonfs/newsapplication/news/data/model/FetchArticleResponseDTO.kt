package com.bonfs.newsapplication.news.data.model

import com.bonfs.newsapplication.news.domain.model.Article

data class FetchArticleResponseDTO(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDTO>
)
{
    fun articlesToDomain(): List<Article> {
        return articles.map { it.toDomain() }
    }
}
