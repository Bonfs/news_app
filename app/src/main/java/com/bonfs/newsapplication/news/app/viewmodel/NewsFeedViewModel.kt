package com.bonfs.newsapplication.news.app.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonfs.newsapplication.news.data.repository.LocalArticleRepository
import com.bonfs.newsapplication.news.domain.model.Article
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository
import com.bonfs.newsapplication.news.domain.usecase.FetchArticlesUseCase
import com.bonfs.newsapplication.news.domain.usecase.LoadNetworkImageUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.async

class NewsFeedViewModel: ViewModel() {
    private val articleRepository: ArticleRepository = LocalArticleRepository()
    private val fetchArticlesUseCase = FetchArticlesUseCase(articleRepository)
    private val loadNetworkImageUseCase = LoadNetworkImageUseCase(articleRepository)

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    fun fetchArticles(subject: String) {
        viewModelScope.launch {
            when(val response = fetchArticlesUseCase.execute(subject)) {
                is ResponseResultStatus.Error -> {
                    _articles.value = emptyList()
                }
                is ResponseResultStatus.Success -> {
                    _articles.value = response.data
                }

                else -> {}
            }
        }
    }

    suspend fun retrieveArticleImage(url: String, onComplete: () -> Unit): Bitmap? {
        return when(val response = loadNetworkImageUseCase.execute(url)) {
            is ResponseResultStatus.Error -> {
                onComplete()
                null
            }
            is ResponseResultStatus.Success -> {
                onComplete()
                response.data
            }
            else -> {
                onComplete()
                null
            }
        }
    }
}