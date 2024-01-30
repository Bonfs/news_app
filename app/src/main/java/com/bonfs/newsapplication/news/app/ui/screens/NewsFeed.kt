package com.bonfs.newsapplication.news.app.ui.screens

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bonfs.newsapplication.news.app.ui.commons.components.NewsTopAbbBar
import com.bonfs.newsapplication.news.app.ui.theme.NewsApplicationTheme
import com.bonfs.newsapplication.news.app.viewmodel.NewsFeedViewModel
import com.bonfs.newsapplication.news.domain.model.Article
import com.bonfs.newsapplication.news.domain.model.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.IOException
import java.io.InputStream

const val FAVORITE_ANIMATION = "favorite"

@Composable
fun NewsFeedScreen(onNavigateToArticle: (String) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val newsFeedViewModel: NewsFeedViewModel = viewModel()
    val articles by newsFeedViewModel.articles.observeAsState()
    val feedListState = rememberLazyListState()

    LaunchedEffect(Dispatchers.IO) {
        newsFeedViewModel.fetchArticles(getLocalArticle(context)!!)
    }

    Scaffold(
        topBar = { NewsTopAbbBar() }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .padding(horizontal = 8.dp)
        ) {
            NewsList(articles, feedListState) { onNavigateToArticle(it) }
        }
    }
}

@Composable
private fun NewsList(
    articles: List<Article>?,
    state: LazyListState,
    onNavigateToArticle: (String) -> Unit
) {
    if (articles.isNullOrEmpty()) {
        return Text(text = "EMPTY LIST")
    }

    LazyColumn(
        state = state,
        contentPadding = PaddingValues(
            horizontal = 8.dp,
            vertical = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(articles) { article ->
            FeedCardItem(article) { onNavigateToArticle(it) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedCardItem(article: Article, onClick: (String) -> Unit) {
    var isChecked by remember { mutableStateOf(false) }
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        onClick = { onClick(article.author) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ArticleImage(
                article.imageURL,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                ArticleTitleAndDescription(
                    title = article.title,
                    description = article.description,
                    modifier = Modifier.weight(1F)
                )
                FavoriteButton(isChecked) { isChecked = it }
            }
        }
    }
}

@Composable
@NonRestartableComposable
fun ArticleImage(urlImage: String, modifier: Modifier = Modifier) {
    val newsFeedViewModel: NewsFeedViewModel = viewModel()
    var isLoading by remember {
        mutableStateOf(true)
    }
    var articleImage: Bitmap? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(Dispatchers.IO) {
        val downloadArticleImgJob = async {
            newsFeedViewModel.retrieveArticleImage(urlImage)
        }

        articleImage = downloadArticleImgJob.await()
        isLoading = false
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(150.dp)
            .fillMaxWidth(),

        ) {
        if (isLoading) {
            return CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }

        if (articleImage == null) {
            return Text(text = "NO IMAGE")
        }

        Image(
            bitmap = articleImage!!.asImageBitmap(),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ArticleTitleAndDescription(title: String, description: String, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        )
        Text(
            text = description,
            maxLines = 2,
            style = TextStyle(
                fontSize = 8.sp,
                fontStyle = FontStyle.Italic
            )
        )
    }
}

@Composable
fun FavoriteButton(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    IconToggleButton(
        checked = isChecked,
        onCheckedChange = { onCheckedChange(it) }) {
        val iconColor = if (isChecked) Color(0xFFEC407A) else Color(0xFFB0BEC5)
        val tint by animateColorAsState(iconColor, label = FAVORITE_ANIMATION)
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Localized description",
            tint = tint
        )
    }
}

fun getLocalArticle(context: Context): String? {
    var json: String? = null
    json = try {
        val stream: InputStream = context.assets.open("articles.json")
        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()
        String(buffer, charset("UTF-8"))
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }

    return json
}

@Preview
@Composable
private fun CardPreview() {
    FeedCardItem(
        Article(
            Source(null, "journal"),
            "",
            "Lorem ipsum dolor sit amet",
            "Donec vulputate augue vel venenatis suscipit. Praesent in maximus mauris.",
            "",
            "",
            "",
            ""
        ),
        {}
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NewsApplicationTheme {
        NewsFeedScreen({})
    }
}
