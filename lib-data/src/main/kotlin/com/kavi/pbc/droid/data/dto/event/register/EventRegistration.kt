package com.kavi.pbc.droid.data.dto.event.register

import kotlinx.serialization.Serializable

@Serializable
data class EventRegistration(
    val id: String,
    val availableSeatCount: Int,
    val registrationList: List<EventRegistrationItem> = emptyList()
)
