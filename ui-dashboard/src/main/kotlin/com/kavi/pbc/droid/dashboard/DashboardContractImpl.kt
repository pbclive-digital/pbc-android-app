package com.kavi.pbc.droid.dashboard

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.dashboard.navigation.DashboardNavigation
import com.kavi.pbc.droid.lib.parent.contract.module.DashboardContract
import javax.inject.Inject

class DashboardContractImpl @Inject constructor(): DashboardContract {

    @Inject
    lateinit var dashboardNavigation: DashboardNavigation

    @Composable
    override fun RetrieveNavGraph() {
        dashboardNavigation.DashboardNavGraph()
    }
}