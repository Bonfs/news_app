package com.bonfs.newsapplication.news.data.model

import com.bonfs.newsapplication.news.domain.model.Article
import com.bonfs.newsapplication.news.domain.model.Source
import com.google.gson.annotations.SerializedName

data class ArticleDTO(
    @SerializedName("source")
    val source: SourceDTO,
    @SerializedName("author")
    val author: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("content")
    val content: String?
) {
    fun toDomain(): Article {
        return Article(
            source.toDomain(),
            author,
            title,
            description,
            urlToImage,
            publishedAt,
            content
        )
    }
}