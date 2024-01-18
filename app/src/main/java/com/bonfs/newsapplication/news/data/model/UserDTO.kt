package com.bonfs.newsapplication.news.data.model

import com.bonfs.newsapplication.news.domain.model.User

data class UserDTO(
    val firstName: String,
    val lastName:String
) {
    fun toDomain(): User {
        return User(
            firstName,
            lastName
        )
    }
}