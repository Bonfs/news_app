package com.bonfs.newsapplication.news.app.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonfs.newsapplication.news.data.model.ArticleDTO
import com.bonfs.newsapplication.news.data.repository.LocalArticleRepository
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository
import com.bonfs.newsapplication.news.domain.usecase.FetchArticlesUseCase
import com.bonfs.newsapplication.news.domain.usecase.LoadNetworkImageUseCase
import kotlinx.coroutines.launch

class NewsFeedViewModel: ViewModel() {
    private val articleRepository: ArticleRepository = LocalArticleRepository()
    private val fetchArticlesUseCase = FetchArticlesUseCase(articleRepository)
    private val loadNetworkImageUseCase = LoadNetworkImageUseCase(articleRepository)

    private val _articles = MutableLiveData<List<ArticleDTO>>()
    val articles: LiveData<List<ArticleDTO>> get() = _articles

    fun fetchArticles(subject: String) {
        viewModelScope.launch {
            when(val response = fetchArticlesUseCase.execute(subject)) {
                is ResponseResultStatus.Error -> {
                    _articles.value = emptyList()
                }
                is ResponseResultStatus.Success -> {
                    _articles.value = response.data
                }
            }
        }
    }

    fun retrieveArticleImage(url: String): Bitmap? {
        var articleImage: Bitmap? = null
        viewModelScope.launch {
            articleImage = when(val response = loadNetworkImageUseCase.execute(url)) {
                is ResponseResultStatus.Error -> null
                is ResponseResultStatus.Success -> response.data
            }
        }

        return articleImage
    }
}