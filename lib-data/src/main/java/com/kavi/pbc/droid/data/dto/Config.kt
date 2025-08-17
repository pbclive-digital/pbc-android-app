package com.kavi.pbc.droid.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val dashboardEventCount: Int = 2
)
