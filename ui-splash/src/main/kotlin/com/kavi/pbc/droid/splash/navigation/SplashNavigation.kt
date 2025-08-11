package com.kavi.pbc.droid.splash.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.splash.ui.SplashUI
import javax.inject.Inject

class SplashNavigation @Inject constructor() {
    @Composable
    fun SplashNavGraph() {
        NavHost(navController = rememberNavController(), startDestination = "splash-anim") {
            composable(route = "splash-anim") {
                SplashUI()
            }
        }
    }
}