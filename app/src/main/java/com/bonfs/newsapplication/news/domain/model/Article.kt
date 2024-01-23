package com.bonfs.newsapplication.news.domain.model

data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val imageURL: String,
    val publishedAt: String,
    val content: String?
)
