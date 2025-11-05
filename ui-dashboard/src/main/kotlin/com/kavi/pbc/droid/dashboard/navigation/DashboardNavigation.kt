package com.kavi.pbc.droid.dashboard.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.dashboard.ui.Dashboard
import com.kavi.pbc.droid.lib.parent.contract.ContractName.AUTH_CONTRACT
import com.kavi.pbc.droid.lib.parent.contract.ContractName.EVENT_CONTRACT
import com.kavi.pbc.droid.lib.parent.contract.ContractName.EVENT_MANAGE_DESTINATION
import com.kavi.pbc.droid.lib.parent.contract.ContractName.EVENT_SELECTED_DESTINATION
import com.kavi.pbc.droid.lib.parent.contract.ContractName.PROFILE_CONTRACT
import com.kavi.pbc.droid.lib.parent.contract.ContractRegistry
import com.kavi.pbc.droid.lib.parent.contract.module.AuthContract
import com.kavi.pbc.droid.lib.parent.contract.module.EventContract
import com.kavi.pbc.droid.lib.parent.contract.module.ProfileContract
import javax.inject.Inject

class DashboardNavigation @Inject constructor() {

    @Inject
    lateinit var dashboard: Dashboard

    @Inject
    lateinit var contractRegistry: ContractRegistry

    @Composable
    fun DashboardNavGraph() {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "dashboard/dashboard-ui",
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            composable (route = "dashboard/dashboard-ui") {
                dashboard.DashboardUI(navController = navController)
            }
            composable (route = "dashboard/to/auth") {
                contractRegistry.getContract<AuthContract>(AUTH_CONTRACT).RetrieveNavGraph()
            }
            composable (route = "dashboard/to/profile") {
                contractRegistry.getContract<ProfileContract>(PROFILE_CONTRACT).RetrieveNavGraph()
            }
            composable (route = "dashboard/to/event/{eventKey}") { backStackEntry ->
                val eventKey = backStackEntry.arguments?.getString("eventKey")
                eventKey?.let {
                    contractRegistry.getContract<EventContract>(EVENT_CONTRACT).RetrieveNavGraphWithData(startDestination = EVENT_SELECTED_DESTINATION, eventKey = it)
                }?: run {
                    contractRegistry.getContract<EventContract>(EVENT_CONTRACT).RetrieveNavGraph()
                }
            }
            composable (route = "dashboard/admin/to/event/manage-event") {
                contractRegistry.getContract<EventContract>(EVENT_CONTRACT).RetrieveNavGraphWithDynamicDestination(startDestination = EVENT_MANAGE_DESTINATION)
            }
        }
    }
}