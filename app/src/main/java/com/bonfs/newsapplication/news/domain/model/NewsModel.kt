package com.bonfs.newsapplication.news.domain.model

@Suppress("UNCHECKED_CAST")
data class NewsModel(
    val result: Any
) {
    fun <T> getResult(model: Class<T>): T = result as T
}
