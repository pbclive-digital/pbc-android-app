package com.kavi.pbc.droid.splash.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.lib.parent.ContractRegistry
import com.kavi.pbc.droid.lib.parent.module.AuthContract
import com.kavi.pbc.droid.lib.parent.module.DashboardContract
import com.kavi.pbc.droid.splash.ui.error.NoAPISupport
import com.kavi.pbc.droid.splash.ui.error.NoConnection
import com.kavi.pbc.droid.splash.ui.splash.SplashUI
import javax.inject.Inject

class SplashNavigation @Inject constructor() {

    @Inject
    lateinit var contractRegistry: ContractRegistry

    @Composable
    fun SplashNavGraph() {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "splash/splash-anim",
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            composable(route = "splash/splash-anim") {
                SplashUI(navController)
            }
            composable(route = "splash/to/auth") {
                //authContract.RetrieveNavGraph()
                contractRegistry.getContract<AuthContract>("auth").RetrieveNavGraph()
            }
            composable(route = "splash/to/dashboard") {
                //dashboardContract.RetrieveNavGraph()
                contractRegistry.getContract<DashboardContract>("dashboard").RetrieveNavGraph()
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