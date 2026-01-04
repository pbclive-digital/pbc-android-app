package com.kavi.pbc.droid.data.dto.event.potluck

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class PotluckItem(
    val itemId: String = UUID.randomUUID().toString(), // This ID is not sync with database. This use only for client purpose
    val itemName: String,
    val itemCount: Int
)