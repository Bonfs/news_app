package com.bonfs.newsapplication.news.data.util

import android.util.LruCache
import com.bonfs.newsapplication.news.domain.model.Article
import com.google.gson.Gson

object ArticleCache {
    private val maxCacheSize: Int = (Runtime.getRuntime().maxMemory() / 1024).toInt() / 10
    private val cache: LruCache<String, Article> = object: LruCache<String, Article>(maxCacheSize) {
        override fun sizeOf(key: String, article: Article): Int {
            val serializedData = Gson().toJson(article)
            val objectSize = serializedData.toByteArray().size

            return objectSize / 1024
        }
    }


    fun addArticlesToMemoryCache(articles: List<Article>) {
        articles.forEach { article ->
            addArticleToMemoryCache(article.author, article)
        }
    }

    private fun addArticleToMemoryCache(key: String, article: Article) {
        synchronized(cache) {
            if(existInCache(key)) return
            cache.put(key, article)
        }
    }

    fun existInCache(key: String): Boolean {
        return cache.get(key) != null
    }

    fun getArticleFromCache(key: String): Article? {
        return cache.get(key)
    }
}