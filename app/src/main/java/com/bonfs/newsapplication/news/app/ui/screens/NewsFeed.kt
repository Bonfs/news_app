package com.bonfs.newsapplication.news.app.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bonfs.newsapplication.news.app.ui.theme.NewsApplicationTheme
import com.bonfs.newsapplication.news.app.ui.theme.Purple80
import com.bonfs.newsapplication.news.app.ui.theme.PurpleGrey80


const val FAVORITE_ANIMATION = "favorite"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedScreen() {
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
            FeedCardItem()
        }
    }
}

@Composable
private fun FeedCardItem() {
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
                Column {
                    Text(
                        text = "Lorem ipsum dolor sit amet",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Donec vulputate augue vel venenatis suscipit. Praesent in maximus mauris.",
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

@Preview
@Composable
private fun CardPreview() {
    FeedCardItem()
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NewsApplicationTheme {
        NewsFeedScreen()
    }
}
