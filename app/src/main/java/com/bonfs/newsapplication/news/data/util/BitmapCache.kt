package com.bonfs.newsapplication.news.data.util

import android.graphics.Bitmap
import android.util.LruCache

object BitmapCache {
    private val maxCacheSize: Int = (Runtime.getRuntime().maxMemory() / 1024).toInt() / 4
    private val cache: LruCache<String, Bitmap> = object: LruCache<String, Bitmap>(maxCacheSize) {
        override fun sizeOf(key: String, bitmap: Bitmap): Int {
            return bitmap.byteCount / 1024
        }
    }

    fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        synchronized(cache) {
            if(existInCache(key)) return
            cache.put(key, bitmap)
        }
    }

    fun existInCache(key: String): Boolean {
        return cache.get(key) != null
    }

    fun getBitmapFromCache(key: String): Bitmap? {
        return cache.get(key)
    }
}