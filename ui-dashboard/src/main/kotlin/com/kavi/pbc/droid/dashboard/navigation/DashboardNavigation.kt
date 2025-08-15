package com.kavi.pbc.droid.dashboard.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.dashboard.ui.Dashboard
import javax.inject.Inject

class DashboardNavigation @Inject constructor() {

    @Composable
    fun DashboardNavGraph() {
        NavHost(
            navController = rememberNavController(), startDestination = "dashboard/dashboard-ui",
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            composable (route = "dashboard/dashboard-ui") {
                Dashboard()
            }
        }
    }
}