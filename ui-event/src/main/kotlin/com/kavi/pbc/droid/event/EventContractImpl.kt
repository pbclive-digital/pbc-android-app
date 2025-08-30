package com.kavi.pbc.droid.event

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.kavi.pbc.droid.event.data.model.EventListViewMode
import com.kavi.pbc.droid.event.data.repository.local.EventLocalRepository
import com.kavi.pbc.droid.event.navigation.EventNavigation
import com.kavi.pbc.droid.event.ui.list.EventList
import com.kavi.pbc.droid.lib.parent.contract.module.EventContract
import javax.inject.Inject

class EventContractImpl @Inject constructor(
    private val eventLocalRepository: EventLocalRepository
): EventContract {

    @Inject
    lateinit var eventNavigation: EventNavigation

    @Inject
    lateinit var eventList: EventList

    @Composable
    override fun RetrieveNavGraph() {
        eventNavigation.EventNavGraph()
    }

    @Composable
    override fun RetrieveNavGraphWithData(eventKey: String) {
        eventLocalRepository.getSelectedEvent(tempEventKey = eventKey).onSuccess { event ->
            eventNavigation.EventNavGraph(eventData = event)
        }.onFailure { error ->
            eventNavigation.EventNavGraph()
        }
    }

    @Composable
    override fun GetUpcomingEventList(navController: NavController) {
        eventList.EventListUI(navController = navController, viewMode = EventListViewMode.UPCOMING)
    }

    @Composable
    override fun GetPastEventList(navController: NavController) {
        eventList.EventListUI(navController = navController, viewMode = EventListViewMode.PAST)
    }
}