package com.kavi.pbc.droid.dashboard.ui.screen.event.pager

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.lib.parent.ContractRegistry
import com.kavi.pbc.droid.lib.parent.module.EventContract
import javax.inject.Inject

class EventPager @Inject constructor() {

    @Inject
    lateinit var contractRegistry: ContractRegistry

    @Composable
    fun UpcomingEventPager() {
        contractRegistry.getContract<EventContract>("event").GetUpcomingEventList()
    }

    @Composable
    fun PastEventPager() {
        contractRegistry.getContract<EventContract>("event").GetPastEventList()
    }
}