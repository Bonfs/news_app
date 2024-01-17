package com.bonfs.newsapplication.news.data.model

data class ArticleDTO(
    val source: SourceDTO,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)