package com.bonfs.newsapplication.news.data.repository

import com.bonfs.newsapplication.news.data.model.FetchArticleResponseDTO
import com.bonfs.newsapplication.news.domain.model.NewsModel
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository
import com.google.gson.Gson

class LocalArticleRepository: ArticleRepository {
    override suspend fun fetchArticles(subject: String): ResponseResultStatus<NewsModel> {
        val articlesResponse = Gson().fromJson(subject, FetchArticleResponseDTO::class.java)

        return ResponseResultStatus.Success(NewsModel(articlesResponse.articles))
    }
}
