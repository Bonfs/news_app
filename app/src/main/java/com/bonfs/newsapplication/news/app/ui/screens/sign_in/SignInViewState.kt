package com.bonfs.newsapplication.news.app.ui.screens.sign_in

data class SignInViewState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)
