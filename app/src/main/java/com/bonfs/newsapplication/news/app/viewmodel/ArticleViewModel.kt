package com.bonfs.newsapplication.news.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonfs.newsapplication.news.app.ui.commons.ARTICLE_ID_KEY
import com.bonfs.newsapplication.news.data.repository.LocalArticleRepository
import com.bonfs.newsapplication.news.domain.model.Article
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository
import com.bonfs.newsapplication.news.domain.usecase.GetArticleByAuthorUseCase
import kotlinx.coroutines.launch

class ArticleViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    private val articleRepository: ArticleRepository = LocalArticleRepository()
    private val getArticleByAuthorUseCase = GetArticleByAuthorUseCase(articleRepository)

    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article> get() = _article

    fun getArticle() {
        viewModelScope.launch {
            _article.value = getArticleByAuthorUseCase
                .execute(checkNotNull(savedStateHandle[ARTICLE_ID_KEY]))
        }
    }
}