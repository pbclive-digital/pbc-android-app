package com.kavi.pbc.droid.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AppVersionStatus(
    val support: Boolean,
    val supportingVersion: String
)
