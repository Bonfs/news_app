package com.bonfs.newsapplication.domain.data

import com.bonfs.newsapplication.news.data.model.UserDTO
import com.bonfs.newsapplication.news.data.repository.RemoteSessionRepository
import com.bonfs.newsapplication.news.domain.model.ErrorStatus
import com.bonfs.newsapplication.news.domain.model.NewsModel
import com.bonfs.newsapplication.news.domain.model.ResponseResult
import com.bonfs.newsapplication.news.domain.repository.SessionRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class SessionRepositoryTest {
    private lateinit var subject: SessionRepository
    private val user = UserDTO("n", "m")

    @Before
    fun setup() {
        subject = mockk<RemoteSessionRepository>()
    }

    @Test
    fun `when sign in should return a successful response`() = runTest {
        coEvery { subject.signIn(any(), any()) } returns
                ResponseResult.Success(NewsModel(user))

        val response = subject.signIn("", "")

        assert(response is ResponseResult.Success)
    }

    @Test
    fun `when sign in error should return a error response`() = runTest {
        coEvery { subject.signIn(any(), any()) } returns
                ResponseResult.Error(ErrorStatus.NetworkError(Exception()))

        val response = subject.signIn("", "")

        assert(response is ResponseResult.Error)
    }
}