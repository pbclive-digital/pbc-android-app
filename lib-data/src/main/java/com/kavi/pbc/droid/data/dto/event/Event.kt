package com.kavi.pbc.droid.data.dto.event

import java.text.DateFormat
import java.util.Date

data class Event(
    val id: String,
    val name: String,
    val description: String,
    var eventStatus: EventStatus = EventStatus.DRAFT,
    val eventDate: Long,
    val startTime: String,
    val endTime: String,
    val createdTime: Long,
    val venueType: VenueType = VenueType.VIRTUAL,
    val venue: String? = null,
    val creator: String,
    val eventImage: String? = null,
    val eventType: EventType = EventType.SPECIAL,
    val isRegistrationRequired: Boolean = false,
    val openSeatCount: Int? = null,
    val isPotluckAvailable: Boolean = false,
    val potluckItemList: MutableList<PotluckItem>? = mutableListOf()
) {
    fun getFormatDate(): String {
        val dateFormat = DateFormat.getDateInstance()
        return dateFormat.format(getDateFromTimestamp())
    }

    fun getDateFromTimestamp(): Date {
        return Date(eventDate)
    }

    fun getPlace(): String {
        return when(venueType) {
            VenueType.VIRTUAL -> "Online"
            VenueType.PHYSICAL -> {
               "at $venue"
            }
        }
    }
}
