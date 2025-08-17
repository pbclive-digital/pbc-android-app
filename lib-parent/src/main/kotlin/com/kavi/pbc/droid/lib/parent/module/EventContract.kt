package com.kavi.pbc.droid.lib.parent.module

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.data.dto.BaseResponse
import com.kavi.pbc.droid.data.dto.event.Event
import com.kavi.pbc.droid.lib.parent.CommonContract

interface EventContract: CommonContract {
    @Composable
    fun GetUpcomingEventList()

    @Composable
    fun GetPastEventList()
}