package com.bonfs.newsapplication.news.domain.repository

import android.graphics.Bitmap
import com.bonfs.newsapplication.news.data.model.ArticleDTO
import com.bonfs.newsapplication.news.domain.model.NewsModel
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus

interface ArticleRepository {
    suspend fun fetchArticles(subject: String): ResponseResultStatus<List<ArticleDTO>>
    suspend fun loadImage(url: String): ResponseResultStatus<Bitmap>
}