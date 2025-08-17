package com.kavi.pbc.droid.event.ui.events

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.kavi.pbc.droid.event.data.model.EventListViewMode
import com.kavi.pbc.droid.lib.common.ui.component.EventItem

@Composable
fun EventListUI(viewMode: EventListViewMode, viewModel: EventListViewModel = hiltViewModel()) {

    val upcomingEventList by viewModel.upcomingEventList.collectAsState()
    val pastEventList by viewModel.pastEventList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUpcomingEvents()
        viewModel.fetchPastEvents()
    }

    when(viewMode) {
        EventListViewMode.UPCOMING -> {
            LazyColumn {
                items(upcomingEventList) { eventItem ->
                    EventItem(event = eventItem)
                }
            }
        }
        EventListViewMode.PAST -> {
            LazyColumn {
                items(pastEventList) { eventItem ->
                    EventItem(event = eventItem)
                }
            }
        }
    }
}