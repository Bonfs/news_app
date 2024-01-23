package com.bonfs.newsapplication.news.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bonfs.newsapplication.news.app.ui.commons.components.NewsTopAbbBar
import com.bonfs.newsapplication.news.app.ui.theme.NewsApplicationTheme
import com.bonfs.newsapplication.news.app.viewmodel.ArticleViewModel

@Composable
fun ArticleDetail() {
    val articleViewModel: ArticleViewModel = viewModel()
    articleViewModel.getArticle()
    val article by articleViewModel.article.observeAsState()

    Scaffold(
        topBar = { NewsTopAbbBar() }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .padding(horizontal = 8.dp)
        ) {
            ArticleBody()
        }
    }
}

@Composable
fun ArticleBody() {
    val articleViewModel: ArticleViewModel = viewModel()
    articleViewModel.getArticle()
    val article by articleViewModel.article.observeAsState()

    if(article == null) {
        return Text("NO ARTICLE")
    }

    Column {
        Text(checkNotNull(article?.title), fontWeight = FontWeight.Bold)
//        Text(checkNotNull(article?.description))
        Text("By " + checkNotNull(article?.author))
        Spacer(Modifier.height(16.dp))
        Text(checkNotNull(article?.content))
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleDetailPreview() {
    NewsApplicationTheme {
        ArticleDetail()
    }
}
