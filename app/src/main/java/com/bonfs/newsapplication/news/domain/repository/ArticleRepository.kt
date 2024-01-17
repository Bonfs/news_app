package com.bonfs.newsapplication.news.domain.repository

import com.bonfs.newsapplication.news.domain.model.NewsModel
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus

interface ArticleRepository {
    suspend fun fetchArticles(subject: String): ResponseResultStatus<NewsModel>
}