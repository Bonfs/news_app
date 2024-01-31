package com.bonfs.newsapplication.news.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.bonfs.newsapplication.news.data.model.ArticleDTO
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
            .asSequence()
            .mapValidArticlesDTOAndConvertToArticle()

        ArticleCache.addArticlesToMemoryCache(articles)

        return ResponseResultStatus.Success(articles)
    }

    fun Sequence<ArticleDTO>.filterByDescriptionNotNull() = filter { it.description != null }
    fun Sequence<ArticleDTO>.filterByUrlToImageNotNull() = filter { it.urlToImage != null }
    fun Sequence<ArticleDTO>.filterByUrlNotNull() = filter { it.url != null }
    fun Sequence<ArticleDTO>.filterByAuthorNotNull() = filter { it.author != null }

    fun Sequence<ArticleDTO>.filterArticleWithValidNotNullField(): Sequence<ArticleDTO> {
        return filterByDescriptionNotNull()
            .filterByUrlToImageNotNull()
            .filterByUrlNotNull()
            .filterByAuthorNotNull()
    }

    fun Sequence<ArticleDTO>.mapValidArticlesDTOAndConvertToArticle(): List<Article> {
        return filterArticleWithValidNotNullField().map { it.toDomain() }.toList()
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
