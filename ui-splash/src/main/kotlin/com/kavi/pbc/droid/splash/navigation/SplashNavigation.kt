package com.kavi.pbc.droid.splash.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.lib.parent.module.AuthContract
import com.kavi.pbc.droid.splash.ui.error.NoAPISupport
import com.kavi.pbc.droid.splash.ui.error.NoConnection
import com.kavi.pbc.droid.splash.ui.splash.SplashUI
import javax.inject.Inject

class SplashNavigation @Inject constructor() {

    @Inject
    lateinit var authContract: AuthContract

    @Composable
    fun SplashNavGraph() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "splash/splash-anim") {
            composable(route = "splash/splash-anim") {
                SplashUI(navController)
            }
            composable(route = "splash/auth") {
                authContract.RetrieveNavGraph()
            }
            composable (route = "splash/no-support") {
                NoAPISupport()
            }
            composable (route = "splash/no-connection") {
                NoConnection()
            }
        }
    }
}