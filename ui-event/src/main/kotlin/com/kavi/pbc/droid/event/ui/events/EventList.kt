package com.kavi.pbc.droid.event.ui.events

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kavi.pbc.droid.event.data.model.EventListViewMode
import com.kavi.pbc.droid.lib.common.ui.component.event.EventListItem
import com.kavi.pbc.droid.lib.common.ui.theme.BottomNavBarHeight

@Composable
fun EventListUI(viewMode: EventListViewMode, viewModel: EventListViewModel = hiltViewModel()) {

    val upcomingEventList by viewModel.upcomingEventList.collectAsState()
    val pastEventList by viewModel.pastEventList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUpcomingEvents()
        viewModel.fetchPastEvents()
    }

    BoxWithConstraints {
        val minHeight = this.minHeight
        val maxHeight = this.maxHeight

        println("Min Height: $minHeight")
        println("Max Height: $maxHeight")

        when(viewMode) {
            EventListViewMode.UPCOMING -> {
                LazyColumn (
                    modifier = Modifier.height(maxHeight - BottomNavBarHeight)
                ) {
                    items(upcomingEventList) { eventItem ->
                        EventListItem(
                            event = eventItem,
                            modifier = Modifier.clickable {

                            }
                        )
                    }
                }
            }
            EventListViewMode.PAST -> {
                LazyColumn (
                    modifier = Modifier.height(maxHeight - BottomNavBarHeight)
                ) {
                    items(pastEventList) { eventItem ->
                        EventListItem(
                            event = eventItem,
                            modifier = Modifier.clickable {

                            }
                        )
                    }
                }
            }
        }
    }
}