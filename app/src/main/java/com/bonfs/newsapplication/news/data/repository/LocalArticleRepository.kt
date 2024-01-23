package com.bonfs.newsapplication.news.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.bonfs.newsapplication.news.data.model.FetchArticleResponseDTO
import com.bonfs.newsapplication.news.data.util.ArticleCache
import com.bonfs.newsapplication.news.data.util.BitmapCache
import com.bonfs.newsapplication.news.domain.model.Article
import com.bonfs.newsapplication.news.domain.model.ErrorStatus
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository
import com.google.gson.Gson
import java.lang.IllegalArgumentException
import java.net.URL
import javax.net.ssl.SSLHandshakeException

class LocalArticleRepository: ArticleRepository {
    override suspend fun fetchArticles(subject: String): ResponseResultStatus<List<Article>> {
        val articlesResponse = Gson().fromJson(subject, FetchArticleResponseDTO::class.java)
        val articles = articlesResponse.articles
            .filter { it.description != null }
            .filter { it.urlToImage != null }
            .filter { it.url != null }
            .filter { it.author != null }
            .map { it.toDomain() }

        ArticleCache.addArticlesToMemoryCache(articles)

        return ResponseResultStatus.Success(articles)
    }

    override suspend fun loadImage(url: String): ResponseResultStatus<Bitmap> {
        if(BitmapCache.existInCache(url)) {
            return ResponseResultStatus.Success(BitmapCache.getBitmapFromCache(url)!!)
        }

        try {
            val bitmap = BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
                ?: return ResponseResultStatus
                    .Error(ErrorStatus.UnavailableImageError(NullPointerException()))

            BitmapCache.addBitmapToMemoryCache(url, bitmap)


            return ResponseResultStatus.Success(bitmap)
        } catch (e: SSLHandshakeException) {
            Log.d("loadImage", url)
        }

        return ResponseResultStatus
            .Error(ErrorStatus.UnavailableImageError(NullPointerException()))
    }

    override suspend fun getArticleByAuthorName(author: String): ResponseResultStatus<Article> {
        val article  = ArticleCache.getArticleFromCache(author) ?:
            return ResponseResultStatus.Error(ErrorStatus.AuthorNotFound(IllegalArgumentException()))

        return ResponseResultStatus.Success(article)
    }
}
