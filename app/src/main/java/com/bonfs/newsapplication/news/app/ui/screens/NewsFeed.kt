package com.bonfs.newsapplication.news.app.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bonfs.newsapplication.news.app.ui.theme.NewsApplicationTheme
import com.bonfs.newsapplication.news.app.ui.theme.Purple80
import com.bonfs.newsapplication.news.app.ui.theme.PurpleGrey80
import com.bonfs.newsapplication.news.app.viewmodel.NewsFeedViewModel
import java.io.IOException
import java.io.InputStream
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.bonfs.newsapplication.news.data.model.ArticleDTO
import com.bonfs.newsapplication.news.data.model.SourceDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


const val FAVORITE_ANIMATION = "favorite"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedScreen() {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val newsFeedViewModel: NewsFeedViewModel = viewModel()
    newsFeedViewModel.fetchArticles(getLocalArticle(context)!!)
    Log.d("NewsFeedScreen", newsFeedViewModel.articles.value.toString())
    val articles by newsFeedViewModel.articles.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "News") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier
            .padding(
                top = contentPadding.calculateTopPadding(),
            )
            .padding(
                horizontal = 8.dp
            )
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = 8.dp,
                    vertical = 8.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (articles != null) {
                    items(articles!!) { article ->
                        FeedCardItem(article)
                    }
                }

            }
        }
    }
}

suspend fun aaa() {}

@Composable
private fun FeedCardItem(article: ArticleDTO) {
    var isChecked by remember { mutableStateOf(false) }
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        Column {
            Box(modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .background(Color.Blue)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    Text(
                        text = article.title,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                    Text(
                        text = article.description,
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
                IconToggleButton(checked = isChecked, onCheckedChange = { isChecked = !isChecked }) {
                    val iconColor = if (isChecked) Color(0xFFEC407A) else Color(0xFFB0BEC5)
                    val tint by animateColorAsState(iconColor, label = FAVORITE_ANIMATION)
                    Icon(Icons.Filled.Favorite, contentDescription = "Localized description", tint = tint)
                }
            }
        }
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
        ArticleDTO(
            SourceDTO(null, "journal"),
            "",
            "Lorem ipsum dolor sit amet",
            "Donec vulputate augue vel venenatis suscipit. Praesent in maximus mauris.",
            "",
            "",
            "",
            ""
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NewsApplicationTheme {
        NewsFeedScreen()
    }
}
