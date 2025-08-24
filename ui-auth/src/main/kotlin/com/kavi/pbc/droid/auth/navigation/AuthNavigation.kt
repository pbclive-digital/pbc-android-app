package com.kavi.pbc.droid.auth.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.auth.ui.auth.AuthUI
import com.kavi.pbc.droid.auth.ui.register.RegisterUI
import com.kavi.pbc.droid.lib.parent.contract.ContractName.DASHBOARD_CONTRACT
import com.kavi.pbc.droid.lib.parent.contract.ContractRegistry
import com.kavi.pbc.droid.lib.parent.contract.module.DashboardContract
import javax.inject.Inject

class AuthNavigation @Inject constructor() {

    @Inject
    lateinit var contractRegistry: ContractRegistry

    @Composable
    fun AuthNavGraph() {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "auth/auth-ui",
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            composable (route = "auth/auth-ui") {
                AuthUI(navController = navController)
            }
            composable (route = "auth/registration-ui/{email}") { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email")
                RegisterUI(navController = navController, email = email!!)
            }
            composable (route = "auth/to/dashboard") {
                contractRegistry.getContract<DashboardContract>(DASHBOARD_CONTRACT).RetrieveNavGraph()
            }
        }
    }
}