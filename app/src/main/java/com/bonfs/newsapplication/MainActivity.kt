package com.bonfs.newsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.bonfs.newsapplication.news.app.ui.commons.ARTICLE_ID_KEY
import com.bonfs.newsapplication.news.app.ui.screens.ArticleDetail
import com.bonfs.newsapplication.news.app.ui.screens.NewsFeedScreen
import com.bonfs.newsapplication.news.app.ui.screens.NewsScreen
import com.bonfs.newsapplication.news.app.ui.screens.sign_in.SignInScreen
import com.bonfs.newsapplication.news.app.ui.theme.NewsApplicationTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApplicationTheme {
                navController = rememberNavController()
                val navGraph = navController.createGraph(startDestination = NewsScreen.SignIn.name) {
                    composable(NewsScreen.SignIn.name) { SignInScreen(onNavigateToNewsFeed = ::navigateToNewsFeed) }
                    composable(NewsScreen.NewsFeed.name) { NewsFeedScreen(onNavigateToArticle = ::navigateToArticleDetail) }
                    composable(
                        "${NewsScreen.ArticleDetail.name}/{articleId}",
                        arguments = listOf(navArgument(ARTICLE_ID_KEY) { type = NavType.StringType })
                    ) { ArticleDetail() }
                }

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavHost(
                        navController = navController,
                        graph = navGraph,
                    )
                }
            }
        }
    }

    private fun navigateToNewsFeed() {
        navController.navigate(NewsScreen.NewsFeed.name)
    }

    private fun navigateToArticleDetail(id: String) {
        navController.navigate("${NewsScreen.ArticleDetail.name}/$id")
    }
}
