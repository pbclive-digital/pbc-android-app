package com.kavi.pbc.droid.data.dto.appointment

import com.kavi.pbc.droid.data.dto.user.User
import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    val id: String,
    val userId: String,
    val user: User,
    val dateAndTime: Long,
    val reason: String
)
