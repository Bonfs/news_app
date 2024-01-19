package com.bonfs.newsapplication.news.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bonfs.newsapplication.news.data.model.FetchArticleResponseDTO
import com.bonfs.newsapplication.news.data.util.BitmapCache
import com.bonfs.newsapplication.news.domain.model.Article
import com.bonfs.newsapplication.news.domain.model.ErrorStatus
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository
import com.google.gson.Gson
import java.net.URL

class LocalArticleRepository: ArticleRepository {
    override suspend fun fetchArticles(subject: String): ResponseResultStatus<List<Article>> {
        val articlesResponse = Gson().fromJson(subject, FetchArticleResponseDTO::class.java)
        val articles = articlesResponse.articles
            .filter { it.description != null }
            .filter { it.urlToImage != null }
            .map { it.toDomain() }

        return ResponseResultStatus.Success(articles)
    }

    override suspend fun loadImage(url: String): ResponseResultStatus<Bitmap> {
        if(BitmapCache.existInCache(url)) {
            return ResponseResultStatus.Success(BitmapCache.getBitmapFromCache(url)!!)
        }

        val bitmap = BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
            ?: return ResponseResultStatus
                .Error(ErrorStatus.UnavailableImageError(NullPointerException()))
        BitmapCache.addBitmapToMemoryCache(url, bitmap)

        return ResponseResultStatus.Success(bitmap)
    }
}
