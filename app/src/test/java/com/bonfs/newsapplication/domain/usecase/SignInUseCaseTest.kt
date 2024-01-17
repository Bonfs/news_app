package com.bonfs.newsapplication.domain.usecase

import com.bonfs.newsapplication.news.data.model.UserDTO
import com.bonfs.newsapplication.news.domain.model.NewsModel
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.SessionRepository
import com.bonfs.newsapplication.news.domain.usecase.SignInUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class SignInUseCaseTest {
    private lateinit var subject: SignInUseCase
    private val sessionRepository: SessionRepository = mockk()
    private val user = UserDTO("n", "m")

    @Before
    fun setup() {
        subject = SignInUseCase(sessionRepository)
    }

    @Test
    fun `when execute correct should return success response`() = runTest {
        coEvery { subject.execute(any(), any()) } returns ResponseResultStatus.Success(NewsModel(user))

        val response = subject.execute("", "")

        assert(response is ResponseResultStatus.Success)
    }
}