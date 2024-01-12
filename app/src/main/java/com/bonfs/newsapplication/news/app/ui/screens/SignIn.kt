package com.bonfs.newsapplication.news.app.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bonfs.newsapplication.news.app.ui.theme.NewsApplicationTheme
import com.bonfs.newsapplication.news.app.viewmodel.SignInViewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun SignInScreen(modifier: Modifier = Modifier, onNavigateToNewsFeed: () -> Unit) {
    val signInViewModel: SignInViewModel = viewModel()
    val navController = rememberNavController()

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
                onNavigateToNewsFeed()
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
    val email by signInViewModel.email.observeAsState()
    val password by signInViewModel.password.observeAsState()

    OutlinedTextField(
        modifier = modifier,
        value = email ?: "",
        label = { Text("Email") },
        onValueChange = signInViewModel::onEmailUpdate
    )
    OutlinedTextField(
        modifier = modifier,
        value = password ?: "",
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