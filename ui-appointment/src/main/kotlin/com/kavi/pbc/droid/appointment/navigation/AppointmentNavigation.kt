package com.kavi.pbc.droid.appointment.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.appointment.ui.create.AppointmentCreate
import com.kavi.pbc.droid.appointment.ui.manage.AppointmentManage
import javax.inject.Inject

class AppointmentNavigation @Inject constructor() {

    @Inject
    lateinit var appointmentManage: AppointmentManage

    @Inject
    lateinit var appointmentCreate: AppointmentCreate

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
                appointmentCreate.AppointmentCreateUI(navController = navController)
            }
        }
    }
}