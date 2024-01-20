package com.bonfs.newsapplication.news.domain.repository

import android.graphics.Bitmap
import com.bonfs.newsapplication.news.domain.model.Article
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus

interface ArticleRepository {
    suspend fun fetchArticles(subject: String): ResponseResultStatus<List<Article>>
    suspend fun loadImage(url: String): ResponseResultStatus<Bitmap>
}