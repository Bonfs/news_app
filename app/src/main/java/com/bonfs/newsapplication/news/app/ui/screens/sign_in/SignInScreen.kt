package com.bonfs.newsapplication.news.app.ui.screens.sign_in

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bonfs.newsapplication.news.app.ui.theme.NewsApplicationTheme

@Composable
fun SignInScreen(modifier: Modifier = Modifier, onNavigateToNewsFeed: () -> Unit) {
    val signInViewModel: SignInViewModel = viewModel()
    val event by signInViewModel.event.collectAsState(initial = SignInEvent.Idle)

    LaunchedEffect(event) {
        when(event) {
            SignInEvent.Idle -> Unit
            SignInEvent.Loading -> Log.v("SignInScreen", "isLoading")
            SignInEvent.NavigateToArticleScreen -> onNavigateToNewsFeed()
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = "NewsApp Sign In!",
            modifier = modifier
        )
        Column {
            SignInForm(modifier)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                signInViewModel.signIn()
            }) {
                Text("Sign in")
            }
        }
        Spacer(modifier)
    }
}

@Composable
fun SignInForm(modifier: Modifier = Modifier) {
    val signInViewModel: SignInViewModel = viewModel()
    val viewState by signInViewModel.signInViewState.collectAsState()

    OutlinedTextField(
        modifier = modifier,
        value = viewState.email,
        label = { Text("Email") },
        onValueChange = signInViewModel::onEmailUpdate
    )
    OutlinedTextField(
        modifier = modifier,
        value = viewState.password,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        onValueChange = signInViewModel::onPasswordUpdate
    )
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    NewsApplicationTheme {
        SignInScreen(onNavigateToNewsFeed = {})
    }
}