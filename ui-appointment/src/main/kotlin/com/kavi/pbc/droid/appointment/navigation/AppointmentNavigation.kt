package com.kavi.pbc.droid.appointment.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.appointment.ui.create.appointment.AppointmentCreateOrModify
import com.kavi.pbc.droid.appointment.ui.create.request.AppointmentRequestCreateOrModify
import com.kavi.pbc.droid.appointment.ui.manage.AppointmentManage
import javax.inject.Inject

class AppointmentNavigation @Inject constructor() {

    @Inject
    lateinit var appointmentManage: AppointmentManage

    @Inject
    lateinit var appointmentCreateOrModify: AppointmentCreateOrModify

    @Inject
    lateinit var appointmentRequestCreateOrModify: AppointmentRequestCreateOrModify

    @Composable
    fun AppointmentNavGraph(startDestination: String = "appointment/appointment-manage") {
        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = startDestination,
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            composable (route = "appointment/appointment-manage") {
                appointmentManage.AppointmentManageUI(navController = navController)
            }
            composable (route = "appointment/appointment-create") {
                appointmentCreateOrModify.AppointmentCreateOrModifyUI(navController = navController)
            }
            composable (route = "appointment/appointment-edit/{appointmentKey}") { backStackEntry ->
                val appointmentKey = backStackEntry.arguments?.getString("appointmentKey")
                appointmentCreateOrModify.AppointmentCreateOrModifyUI(navController = navController,
                    modifyingAppointmentKey = appointmentKey)
            }
            composable (route = "appointment/appointment-request-create") {
                appointmentRequestCreateOrModify.AppointmentRequestCreateOrModifyUI(navController = navController)
            }
            composable (route = "appointment/appointment-request-edit/{appointmentReqKey}") { backStackEntry ->
                val appointmentReqKey = backStackEntry.arguments?.getString("appointmentReqKey")
                appointmentRequestCreateOrModify.AppointmentRequestCreateOrModifyUI(navController = navController,
                    modifyingAppointmentReqKey = appointmentReqKey)
            }
        }
    }
}