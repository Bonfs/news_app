package com.bonfs.newsapplication.news.domain.usecase

import android.graphics.Bitmap
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadNetworkImageUseCase(val repository: ArticleRepository) {
    suspend fun execute(url: String): ResponseResultStatus<Bitmap> = withContext(Dispatchers.IO) {
        repository.loadImage(url)
    }
}
