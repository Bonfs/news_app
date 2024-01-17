package com.bonfs.newsapplication.news.app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonfs.newsapplication.news.data.model.UserDTO
import com.bonfs.newsapplication.news.data.repository.RemoteSessionRepository
import com.bonfs.newsapplication.news.domain.model.ResponseResultStatus
import com.bonfs.newsapplication.news.domain.usecase.SignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {
    private val signInUseCase = SignInUseCase(RemoteSessionRepository())

    private var _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    fun onEmailUpdate(email: String) {
        _email.value = email
    }

    fun onPasswordUpdate(password: String) {
        _password.value = password
    }

    fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = signInUseCase.execute(
                email.value ?: "",
                password.value ?: ""
            )) {
                is ResponseResultStatus.Error -> TODO()
                is ResponseResultStatus.Success ->
                    Log.d("signIn", response.data.firstName)

                else -> TODO()
            }
        }
    }
}