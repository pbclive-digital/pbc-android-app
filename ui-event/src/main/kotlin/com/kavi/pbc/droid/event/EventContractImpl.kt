package com.kavi.pbc.droid.event

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.event.data.model.EventListViewMode
import com.kavi.pbc.droid.event.navigation.EventNavigation
import com.kavi.pbc.droid.event.ui.events.EventListUI
import com.kavi.pbc.droid.lib.parent.module.EventContract
import javax.inject.Inject

class EventContractImpl @Inject constructor(): EventContract {

    @Inject
    lateinit var eventNavigation: EventNavigation

    @Composable
    override fun RetrieveNavGraph() {
        eventNavigation.EventNavGraph()
    }

    @Composable
    override fun GetUpcomingEventList() {
        EventListUI(viewMode = EventListViewMode.UPCOMING)
    }

    @Composable
    override fun GetPastEventList() {
        EventListUI(viewMode = EventListViewMode.PAST)
    }
}