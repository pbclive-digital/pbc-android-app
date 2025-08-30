package com.kavi.pbc.droid.data.dto.event

import kotlinx.serialization.Serializable

@Serializable
data class PotluckItem(
    val itemName: String,
    val itemCount: Int
)
