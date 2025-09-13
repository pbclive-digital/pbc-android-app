package com.kavi.pbc.droid.event.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.event.ui.selected.EventSelected
import javax.inject.Inject

class EventNavigation @Inject constructor() {

    @Inject
    lateinit var eventSelected: EventSelected

    @Composable
    fun EventNavGraph(eventData: Event? = null) {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = "event/event-ui",
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            composable (route = "event/event-ui") {
                eventSelected.EventUI(navController = navController, eventData = eventData)
            }
        }
    }
}