package com.bonfs.newsapplication.ui

import com.bonfs.newsapplication.MainDispatcherRule
import com.bonfs.newsapplication.news.app.ui.screens.sign_in.SignInEvent
import com.bonfs.newsapplication.news.app.ui.screens.sign_in.SignInViewModel
import com.bonfs.newsapplication.news.data.repository.RemoteSessionRepository
import com.bonfs.newsapplication.news.domain.model.ErrorStatus
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.model.User
import com.bonfs.newsapplication.news.domain.repository.SessionRepository
import com.bonfs.newsapplication.news.domain.usecase.SignInUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SignInViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var subject: SignInViewModel
    private val mockUseCase = mockk<SignInUseCase>()
    private val successResult = ResponseResultStatus.Success(User("", ""))
    private val errorResult = ResponseResultStatus.Error(ErrorStatus.NetworkError(Exception()))

    @Before
    fun setup() {
        subject = SignInViewModel(mockUseCase)
        coEvery { mockUseCase.execute(any(), any()) } returns successResult
    }

    @Test
    fun `when email is updated should set correctly the email value`() = runTest {
        val email = "mmm@mmm.com"

        subject.onEmailUpdate(email)

        assert(subject.signInViewState.value.email == email)
    }

    @Test
    fun `when password is updated should set correctly the password value`() = runTest {
        val pwd = "1234"

        subject.onPasswordUpdate(pwd)

        assert(subject.signInViewState.value.password == pwd)
    }

    @Test
    fun `signIn emits Loading and Idle events on signInUseCase error`() = runTest {
        coEvery { mockUseCase.execute(any(), any()) } returns errorResult
        val testResults = mutableListOf<SignInEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            subject.event.toList(testResults)
        }

        subject.signIn()

        assertEquals(3, testResults.size)
        assertEquals(listOf(SignInEvent.Idle, SignInEvent.Loading, SignInEvent.Idle), testResults)
    }

    @Test
    fun `signIn emits Loading and NavigateToArticleScreen events on signInUseCase success`() =
        runTest {
            val testResults = mutableListOf<SignInEvent>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                subject.event.toList(testResults)
            }

            subject.signIn()

            assertEquals(3, testResults.size)
            assertEquals(
                listOf(
                    SignInEvent.Idle,
                    SignInEvent.Loading,
                    SignInEvent.NavigateToArticleScreen
                ), testResults
            )
        }
}
