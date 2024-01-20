package com.bonfs.newsapplication.news.app.ui.commons.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.bonfs.newsapplication.news.app.ui.commons.APP_NAME

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAbbBar() {
    TopAppBar(
        title = { Text(text = APP_NAME) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    )
}