package com.bonfs.newsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.bonfs.newsapplication.news.app.ui.screens.NewsFeedScreen
import com.bonfs.newsapplication.news.app.ui.screens.NewsScreen
import com.bonfs.newsapplication.news.app.ui.screens.SignInScreen
import com.bonfs.newsapplication.news.app.ui.theme.NewsApplicationTheme
import com.bonfs.newsapplication.news.app.viewmodel.SignInViewModel

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApplicationTheme {
                navController = rememberNavController()
                val navGraph = navController.createGraph(startDestination = NewsScreen.SignIn.name) {
                    composable(NewsScreen.SignIn.name) { SignInScreen(onNavigateToNewsFeed = ::navigateToNewsFeed) }
                    composable(NewsScreen.NewsFeed.name) { NewsFeedScreen() }
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
}