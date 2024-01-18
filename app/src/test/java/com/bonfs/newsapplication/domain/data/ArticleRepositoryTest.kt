package com.bonfs.newsapplication.domain.data

import com.bonfs.newsapplication.news.data.model.ArticleDTO
import com.bonfs.newsapplication.news.data.repository.LocalArticleRepository
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

import java.io.InputStream


@ExperimentalCoroutinesApi
class ArticleRepositoryTest {
    private lateinit var subject: LocalArticleRepository

    @Before
    fun setup() {
        subject = LocalArticleRepository()
    }

    @Test
    fun `when fetch articles should return a successful response`() = runTest {
        val json = loadJSONFromAsset()
        val response = subject.fetchArticles(json!!)
        when (response) {
            is ResponseResultStatus.Error -> {}
            is ResponseResultStatus.Success -> {
                val articles = response.data
                println(articles[0].author)
            }
        }

        assert(response is ResponseResultStatus.Success)
    }

    @Test
    fun `when fetch article image should return a successful response`() = runTest {

    }

    private fun loadJSONFromAsset(): String? {
        var json: String? = null
        json = try {
            val stream: InputStream = javaClass.classLoader?.getResourceAsStream("articles.json")!!
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }
}

