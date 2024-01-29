package com.bonfs.newsapplication.news.app.ui.screens.sign_in

sealed class SignInEvent {
    data object Idle : SignInEvent()
    data object Loading : SignInEvent()
    data object NavigateToArticleScreen : SignInEvent()
}