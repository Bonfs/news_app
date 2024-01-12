package com.bonfs.newsapplication.domain.usecase

import com.bonfs.newsapplication.news.domain.repository.SessionRepository
import com.bonfs.newsapplication.news.domain.usecase.SignInUseCase
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

internal class SignInUseCaseTest {
    private lateinit var subject: SignInUseCase
    private val sessionRepository: SessionRepository = mockk()

    @Before
    fun setup() {
        subject = SignInUseCase(sessionRepository)
    }

    @Test
    fun `when execute correct should return success response`() {

    }
}