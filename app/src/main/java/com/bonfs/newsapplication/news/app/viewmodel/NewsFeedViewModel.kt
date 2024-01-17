package com.bonfs.newsapplication.news.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonfs.newsapplication.news.data.model.ArticleDTO
import com.bonfs.newsapplication.news.data.repository.LocalArticleRepository
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository
import com.bonfs.newsapplication.news.domain.usecase.FetchArticlesUseCase
import kotlinx.coroutines.launch

class NewsFeedViewModel: ViewModel() {
    private val articleRepository: ArticleRepository = LocalArticleRepository()
    private val fetchArticlesUseCase = FetchArticlesUseCase(articleRepository)

    private val _articles = MutableLiveData<List<ArticleDTO>>()
    val articles: LiveData<List<ArticleDTO>> get() = _articles

    @Suppress("UNCHECKED_CAST")
    fun fetchArticles(subject: String) {
        viewModelScope.launch {
            when(val response = fetchArticlesUseCase.execute(subject)) {
                is ResponseResultStatus.Error -> {
                    _articles.value = emptyList()
                }
                is ResponseResultStatus.Success -> {
                    _articles.value = response.data.getResult(List::class.java) as List<ArticleDTO>
                }
            }
        }
    }
}