package com.kavi.pbc.droid.dashboard.ui.event.pager

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.kavi.pbc.droid.lib.parent.contract.ContractServiceLocator
import com.kavi.pbc.droid.lib.parent.contract.module.EventContract
import javax.inject.Inject

class EventPager @Inject constructor() {
    @Composable
    fun UpcomingEventPager(navController: NavController) {
        ContractServiceLocator.locate(EventContract::class)
            .GetUpcomingEventList(navController = navController)
    }

    @Composable
    fun PastEventPager(navController: NavController) {
        ContractServiceLocator.locate(EventContract::class)
            .GetPastEventList(navController = navController)
    }
}