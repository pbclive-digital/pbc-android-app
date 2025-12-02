package com.kavi.pbc.droid.data.dto.appointment

import com.kavi.pbc.droid.data.dto.user.User
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.Date

@Serializable
data class Appointment(
    val id: String? = null,
    var title: String = "",
    val userId: String = "",
    val user: User,
    var selectedMonkId: String = "",
    var selectedMonk: User? = null,
    var date: Long = 0,
    var time: String = "",
    var reason: String = "",
    var appointmentStatus: AppointmentStatus = AppointmentStatus.PENDING
) {
    fun getFormatDate(): String {
        val dateFormat = DateFormat.getDateInstance()
        return dateFormat.format(getDateFromTimestamp())
    }

    fun getDateFromTimestamp(): Date {
        return Date(date)
    }
}
