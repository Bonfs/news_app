package com.bonfs.newsapplication.news.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bonfs.newsapplication.news.data.model.ArticleDTO
import com.bonfs.newsapplication.news.data.model.FetchArticleResponseDTO
import com.bonfs.newsapplication.news.domain.model.ErrorStatus
import com.bonfs.newsapplication.news.domain.model.NewsModel
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository
import com.google.gson.Gson
import java.net.URL

class LocalArticleRepository: ArticleRepository {
    override suspend fun fetchArticles(subject: String): ResponseResultStatus<List<ArticleDTO>> {
        val articlesResponse = Gson().fromJson(subject, FetchArticleResponseDTO::class.java)

        return ResponseResultStatus.Success(articlesResponse.articles)
    }

    override suspend fun loadImage(url: String): ResponseResultStatus<Bitmap> {
        val bitmap = BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
            ?: return ResponseResultStatus
                .Error(ErrorStatus.UnavailableImageError(NullPointerException()))

        return ResponseResultStatus.Success(bitmap)
    }
}
