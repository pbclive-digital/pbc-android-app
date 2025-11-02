package com.kavi.pbc.droid.data.dto.event

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class PotluckItem(
    val itemId: String = UUID.randomUUID().toString(),
    val itemName: String,
    val itemCount: Int
)
