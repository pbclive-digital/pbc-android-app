package com.kavi.pbc.droid.broadcast

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.broadcast.navigation.BroadcastNavigation
import com.kavi.pbc.droid.lib.parent.contract.module.BroadcastContract
import javax.inject.Inject

class BroadcastContractImpl @Inject constructor(): BroadcastContract {

    @Inject
    lateinit var broadcastNavigation: BroadcastNavigation

    @Composable
    override fun RetrieveNavGraph() {
        broadcastNavigation.BroadcastNavGraph()
    }
}