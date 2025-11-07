package com.kavi.pbc.droid.appointment

import androidx.compose.runtime.Composable
import com.kavi.pbc.droid.appointment.navigation.AppointmentNavigation
import com.kavi.pbc.droid.lib.parent.contract.module.AppointmentContract
import javax.inject.Inject

class AppointmentContractImpl @Inject constructor(): AppointmentContract {
    @Inject
    lateinit var appointmentNavigation: AppointmentNavigation

    @Composable
    override fun RetrieveNavGraph() {
        appointmentNavigation.AppointmentNavGraph()
    }
}