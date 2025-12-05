package com.kavi.pbc.droid.news.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.news.ui.manage.NewsManage
import javax.inject.Inject

class NewsNavigation @Inject constructor() {

    @Inject
    lateinit var newsManage: NewsManage

    @Composable
    fun NewsNavGraph() {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "news/manage",
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            composable (route = "news/manage") {
                newsManage.NewsManageUI(navController)
            }
        }
    }
}