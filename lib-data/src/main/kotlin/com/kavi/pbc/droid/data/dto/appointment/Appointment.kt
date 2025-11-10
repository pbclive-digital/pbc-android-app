package com.kavi.pbc.droid.data.dto.appointment

import com.kavi.pbc.droid.data.dto.user.User
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.Date

@Serializable
data class Appointment(
    val id: String,
    val userId: String,
    val user: User,
    val selectedMonkId: String,
    val selectedMonk: User? = null,
    val date: Long,
    val time: String,
    val reason: String,
    val appointmentStatus: AppointmentStatus = AppointmentStatus.PENDING
) {
    fun getFormatDate(): String {
        val dateFormat = DateFormat.getDateInstance()
        return dateFormat.format(getDateFromTimestamp())
    }

    fun getDateFromTimestamp(): Date {
        return Date(date)
    }
}
