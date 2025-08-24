package com.kavi.pbc.droid.dashboard.ui.screen.event.pager

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.lib.parent.contract.ContractName.EVENT_CONTRACT
import com.kavi.pbc.droid.lib.parent.contract.ContractRegistry
import com.kavi.pbc.droid.lib.parent.contract.module.EventContract
import javax.inject.Inject

class EventPager @Inject constructor() {

    @Inject
    lateinit var contractRegistry: ContractRegistry

    @Composable
    fun UpcomingEventPager() {
        contractRegistry.getContract<EventContract>(EVENT_CONTRACT).GetUpcomingEventList()
    }

    @Composable
    fun PastEventPager() {
        contractRegistry.getContract<EventContract>(EVENT_CONTRACT).GetPastEventList()
    }
}