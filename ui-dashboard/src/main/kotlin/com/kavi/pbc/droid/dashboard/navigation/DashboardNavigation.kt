package com.kavi.pbc.droid.dashboard.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.dashboard.ui.Dashboard
import com.kavi.pbc.droid.lib.parent.contract.NavDestinationPath.EVENT_MANAGE_DESTINATION
import com.kavi.pbc.droid.lib.parent.contract.NavDestinationPath.EVENT_SELECTED_DESTINATION
import com.kavi.pbc.droid.lib.parent.contract.NavDestinationPath.TEMPLE_CONTACT_US_DESTINATION
import com.kavi.pbc.droid.lib.parent.contract.ContractServiceLocator
import com.kavi.pbc.droid.lib.parent.contract.module.AppointmentContract
import com.kavi.pbc.droid.lib.parent.contract.module.AuthContract
import com.kavi.pbc.droid.lib.parent.contract.module.EventContract
import com.kavi.pbc.droid.lib.parent.contract.module.NewsContract
import com.kavi.pbc.droid.lib.parent.contract.module.ProfileContract
import com.kavi.pbc.droid.lib.parent.contract.module.TempleContract
import com.kavi.pbc.droid.lib.parent.contract.module.UserContract
import javax.inject.Inject

class DashboardNavigation @Inject constructor() {

    @Inject
    lateinit var dashboard: Dashboard

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
                ContractServiceLocator.locate(AuthContract::class).RetrieveNavGraph()
            }
            composable (route = "dashboard/to/profile") {
                ContractServiceLocator.locate(ProfileContract::class).RetrieveNavGraph()
            }
            composable (route = "dashboard/to/event/{eventKey}") { backStackEntry ->
                val eventKey = backStackEntry.arguments?.getString("eventKey")
                eventKey?.let {
                    ContractServiceLocator.locate(EventContract::class).RetrieveNavGraphWithData(startDestination = EVENT_SELECTED_DESTINATION, eventKey = it)
                }?: run {
                    ContractServiceLocator.locate(EventContract::class).RetrieveNavGraph()
                }
            }
            composable (route = "dashboard/admin/to/event/manage-event") {
                ContractServiceLocator.locate(EventContract::class).RetrieveNavGraphWithDynamicDestination(startDestination = EVENT_MANAGE_DESTINATION)
            }
            composable (route = "dashboard/admin/to/user") {
                ContractServiceLocator.locate(UserContract::class).RetrieveNavGraph()
            }
            composable (route = "dashboard/admin/to/news") {
                ContractServiceLocator.locate(NewsContract::class).RetrieveNavGraph()
            }
            composable (route = "dashboard/to/temple/about-us") {
                ContractServiceLocator.locate(TempleContract::class).RetrieveNavGraph()
            }
            composable (route = "dashboard/to/temple/contact-us") {
                ContractServiceLocator.locate(TempleContract::class).RetrieveNavGraphWithDynamicDestination(startDestination = TEMPLE_CONTACT_US_DESTINATION)
            }
            composable (route = "dashboard/to/appointment") {
                ContractServiceLocator.locate(AppointmentContract::class).RetrieveNavGraph()
            }
        }
    }
}