package com.kavi.pbc.droid.event.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.event.ui.create.EventCreateOrModify
import com.kavi.pbc.droid.event.ui.manage.EventManage
import com.kavi.pbc.droid.event.ui.selected.EventSelected
import com.kavi.pbc.droid.lib.parent.contract.ContractServiceLocator
import com.kavi.pbc.droid.lib.parent.contract.module.AuthContract
import javax.inject.Inject

class EventNavigation @Inject constructor() {

    @Inject
    lateinit var eventSelected: EventSelected

    @Inject
    lateinit var eventCreateOrModify: EventCreateOrModify

    @Inject
    lateinit var eventManage: EventManage

    @Composable
    fun EventNavGraph(startDestination: String = "event/event-ui", eventData: Event? = null) {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = startDestination,
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            composable (route = "event/event-manage") {
                eventManage.EventManageUI(navController = navController)
            }
            composable (route = "event/event-ui") {
                eventSelected.EventUI(navController = navController, eventData = eventData)
            }
            composable (route = "event/event-create") {
                eventCreateOrModify.EventCreateOrModifyUI(navController = navController)
            }
            composable (route = "event/event-edit/{eventKey}") { backStackEntry ->
                val eventKey = backStackEntry.arguments?.getString("eventKey")
                eventCreateOrModify.EventCreateOrModifyUI(navController = navController, modifyingEventKey = eventKey)
            }
            composable (route = "event/to/auth") {
                ContractServiceLocator.locate(AuthContract::class).RetrieveNavGraph()
            }
        }
    }
}