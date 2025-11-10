package com.kavi.pbc.droid.data.dto.appointment

import kotlinx.serialization.Serializable

@Serializable
data class AppointmentRequest(
    val monkName: String,
    val monkEmail: String,
    val date: Long,
    val time: String,
    val reasonForAppointment: String
)
