package com.kavi.pbc.droid.splash.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.lib.parent.module.AuthContract
import com.kavi.pbc.droid.splash.ui.Splash
import javax.inject.Inject

class SplashNavigation @Inject constructor() {

    @Inject
    lateinit var authContract: AuthContract

    @Inject
    lateinit var splash: Splash

    @Composable
    fun SplashNavGraph() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "splash/splash-anim") {
            composable(route = "splash/splash-anim") {
                splash.SplashUI(navController)
            }
            composable(route = "splash/auth") {
                authContract.RetrieveNavGraph()
            }
        }
    }
}