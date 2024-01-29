package com.bonfs.newsapplication.news.app.ui.screens.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonfs.newsapplication.news.data.repository.RemoteSessionRepository
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.repository.SessionRepository
import com.bonfs.newsapplication.news.domain.usecase.SignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    val signInUseCase: SignInUseCase = SignInUseCase()
): ViewModel() {
//    private val signInUseCase = SignInUseCase(repository)

    private val _signInViewState: MutableStateFlow<SignInViewState> = MutableStateFlow(SignInViewState())
    val signInViewState: StateFlow<SignInViewState> get() = _signInViewState

    private val _event: MutableStateFlow<SignInEvent> = MutableStateFlow(SignInEvent.Idle)
    val event: StateFlow<SignInEvent> get() = _event

    fun onEmailUpdate(email: String) {
        viewModelScope.launch {
            _signInViewState.update { it.copy(email = email) }
        }
    }

    fun onPasswordUpdate(password: String) {
        viewModelScope.launch {
            _signInViewState.update { it.copy(password = password) }
        }
    }

    fun signIn() {
        _event.tryEmit(SignInEvent.Loading)
        viewModelScope.launch {
            when(val response = signInUseCase.execute(
                _signInViewState.value.email,
                _signInViewState.value.password
            )) {
                is ResponseResultStatus.Error -> {
                    _event.tryEmit(SignInEvent.Idle)
                }
                is ResponseResultStatus.Success -> {
                    _event.tryEmit(SignInEvent.NavigateToArticleScreen)
//                    Log.d("signIn", response.data.firstName)
                }
            }
        }
    }
}