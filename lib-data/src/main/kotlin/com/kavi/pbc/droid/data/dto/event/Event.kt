package com.kavi.pbc.droid.data.dto.event

import com.kavi.pbc.droid.data.dto.event.potluck.PotluckItem
import com.kavi.pbc.droid.data.dto.event.signup.SignUpSheet
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.Date

@Serializable
data class Event(
    val id: String? = null,
    var name: String = "",
    var description: String = "",
    var eventStatus: EventStatus = EventStatus.DRAFT,
    var eventDate: Long = 0,
    var startTime: String = "",
    var endTime: String = "",
    val createdTime: Long = System.currentTimeMillis(),
    var venueType: VenueType = VenueType.DEFAULT,
    var venue: String? = null,
    var venueAddress: String? = null,
    var meetingUrl: String? = null,
    val creator: String,
    var eventImage: String? = null,
    var eventType: EventType = EventType.DEFAULT,
    var registrationRequired: Boolean = false,
    var openSeatCount: Int? = null,
    var potluckAvailable: Boolean = false,
    var potluckItemList: MutableList<PotluckItem>? = mutableListOf(),
    var signUpSheetAvailable: Boolean = false,
    var signUpSheetList: MutableList<SignUpSheet>? = mutableListOf()
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
            VenueType.DEFAULT -> { "" }
            VenueType.VIRTUAL -> "Online"
            VenueType.PHYSICAL -> {
               "$venue"
            }
        }
    }
}
