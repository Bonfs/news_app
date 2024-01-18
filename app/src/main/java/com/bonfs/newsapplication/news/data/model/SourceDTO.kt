package com.bonfs.newsapplication.news.data.model

import com.bonfs.newsapplication.news.domain.model.Source
import com.google.gson.annotations.SerializedName

data class SourceDTO(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String
) {
    fun toDomain(): Source {
        return Source(id, name)
    }
}