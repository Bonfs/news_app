package com.bonfs.newsapplication.news.domain.model

data class User(
    val firstName: String,
    val lastName:String,
    val isSessionValid: Boolean = true
)
