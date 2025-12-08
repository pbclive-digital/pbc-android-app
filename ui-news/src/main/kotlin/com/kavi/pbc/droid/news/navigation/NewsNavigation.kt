package com.kavi.pbc.droid.news.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.news.ui.create.NewsCreateOrModify
import com.kavi.pbc.droid.news.ui.list.ActiveNewsList
import com.kavi.pbc.droid.news.ui.manage.NewsManage
import javax.inject.Inject

class NewsNavigation @Inject constructor() {

    @Inject
    lateinit var newsManage: NewsManage

    @Inject
    lateinit var newsCreateOrModify: NewsCreateOrModify

    @Inject
    lateinit var activeNewsList: ActiveNewsList

    @Composable
    fun NewsNavGraph(startDestination: String = "news/news-manage") {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = startDestination,
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            composable (route = "news/news-manage") {
                newsManage.NewsManageUI(navController)
            }
            composable (route = "news/news-create") {
                newsCreateOrModify.NewsCreateOrModifyUI(navController)
            }
            composable (route = "news/news-edit/{newsKey}") { backStackEntry ->
                val newsKey = backStackEntry.arguments?.getString("newsKey")
                newsCreateOrModify.NewsCreateOrModifyUI(navController = navController, modifyingNewsKey = newsKey)
            }
            composable (route = "news/active-list") {
                activeNewsList.ActiveNewsListUI(navController)
            }
        }
    }
}