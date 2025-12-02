package com.kavi.pbc.droid.data.dto.appointment

import kotlinx.serialization.Serializable

@Serializable
data class AppointmentRequestEligibility(
    val acceptedCount: Int = 0,
    val requestCount: Int = 0,
    val allowToCreateRequest: Boolean = false
)
