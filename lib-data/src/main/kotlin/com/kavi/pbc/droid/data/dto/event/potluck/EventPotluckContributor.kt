package com.kavi.pbc.droid.data.dto.event.potluck

import kotlinx.serialization.Serializable

@Serializable
data class EventPotluckContributor(
    val contributorId: String,
    val contributorName: String,
    val contributorContactNumber: String
)
