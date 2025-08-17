package com.kavi.pbc.droid.dashboard.ui.screen.event.pager

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.lib.parent.module.EventContract
import javax.inject.Inject

class EventPager @Inject constructor() {

    @Inject
    lateinit var eventContract: EventContract

    @Composable
    fun UpcomingEventPager() {
        eventContract.GetUpcomingEventList()
    }

    @Composable
    fun PastEventPager() {
        eventContract.GetPastEventList()
    }
}