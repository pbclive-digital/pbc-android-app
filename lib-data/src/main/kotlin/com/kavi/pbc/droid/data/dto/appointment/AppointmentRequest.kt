package com.kavi.pbc.droid.data.dto.appointment

import com.kavi.pbc.droid.data.dto.user.User
import kotlinx.serialization.Serializable

@Serializable
data class AppointmentRequest(
    val id: String? = null,
    var title: String = "",
    val userId: String = "",
    val user: User,
    var selectedMonkId: String = "",
    var selectedMonk: User? = null,
    var reason: String = "",
    var appointmentReqType: AppointmentRequestType = AppointmentRequestType.REMOTE
)
