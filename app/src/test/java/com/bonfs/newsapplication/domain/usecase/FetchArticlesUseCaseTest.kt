package com.bonfs.newsapplication.domain.usecase

import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository
import com.bonfs.newsapplication.news.domain.usecase.FetchArticlesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchArticlesUseCaseTest {
    private lateinit var subject: FetchArticlesUseCase
    private val repository: ArticleRepository = mockk()

    @Before
    fun setup() {
        subject = FetchArticlesUseCase(repository)
    }

    @Test
    fun `when execute correct should return success response`() = runTest {
        coEvery { repository.fetchArticles(any()) } returns ResponseResultStatus.Success(emptyList())

        val response = subject.execute("")

        assert(response is ResponseResultStatus.Success)
    }
}