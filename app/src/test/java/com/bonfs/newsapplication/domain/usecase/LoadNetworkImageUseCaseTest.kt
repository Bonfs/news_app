package com.bonfs.newsapplication.domain.usecase

import android.graphics.Bitmap
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.ArticleRepository
import com.bonfs.newsapplication.news.domain.usecase.LoadNetworkImageUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoadNetworkImageUseCaseTest {
    private lateinit var subject: LoadNetworkImageUseCase
    private val repository: ArticleRepository = mockk()
    private val bmp: Bitmap = mockk()

    @Before
    fun setup() {
        subject = LoadNetworkImageUseCase(repository)
    }

    @Test
    fun `when execute correct should return success response`() = runTest {
        coEvery { repository.loadImage(any()) } returns ResponseResultStatus.Success(bmp)

        val response = subject.execute("some_url")

        assert(response is ResponseResultStatus.Success)
    }
}