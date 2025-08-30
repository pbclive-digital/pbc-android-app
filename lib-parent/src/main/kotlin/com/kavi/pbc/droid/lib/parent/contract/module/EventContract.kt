package com.kavi.pbc.droid.lib.parent.contract.module

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.kavi.pbc.droid.lib.parent.contract.CommonContract

interface EventContract: CommonContract {

    @Composable
    fun RetrieveNavGraphWithData(eventKey: String)

    @Composable
    fun GetUpcomingEventList(navController: NavController)

    @Composable
    fun GetPastEventList(navController: NavController)
}