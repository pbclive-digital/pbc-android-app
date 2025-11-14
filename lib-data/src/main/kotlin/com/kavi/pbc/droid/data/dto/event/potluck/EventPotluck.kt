package com.kavi.pbc.droid.data.dto.event.potluck

import kotlinx.serialization.Serializable

@Serializable
data class EventPotluck(
    val id: String,
    val potluckItemList: MutableList<EventPotluckItem> = mutableListOf()
)
