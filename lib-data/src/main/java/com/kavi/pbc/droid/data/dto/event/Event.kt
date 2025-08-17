package com.kavi.pbc.droid.data.dto.event

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
    val eventType: EventType = EventType.SPECIAL,
    val isRegistrationRequired: Boolean = false,
    val openSeatCount: Int? = null,
    val isPotluckAvailable: Boolean = false,
    val potluckItemList: MutableList<PotluckItem>? = mutableListOf()
)
