package com.kavi.pbc.droid.temple

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.lib.parent.contract.module.TempleContract
import com.kavi.pbc.droid.temple.navigation.TempleNavigation
import jakarta.inject.Inject

class TempleContractImpl @Inject constructor(): TempleContract {

    @Inject
    lateinit var templeNavigation: TempleNavigation

    @Composable
    override fun RetrieveNavGraph() {
        templeNavigation.TempleNavGraph()
    }

    @Composable
    override fun RetrieveNavGraphWithDynamicDestination(startDestination: String) {
        templeNavigation.TempleNavGraph(startDestination = startDestination)
    }
}