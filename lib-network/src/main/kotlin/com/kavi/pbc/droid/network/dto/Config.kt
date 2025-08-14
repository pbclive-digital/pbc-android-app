package com.kavi.pbc.droid.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val dashboardEventCount: Int = 2
)
