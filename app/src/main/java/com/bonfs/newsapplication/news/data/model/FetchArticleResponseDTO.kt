package com.bonfs.newsapplication.news.data.model

data class FetchArticleResponseDTO(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDTO>
)
