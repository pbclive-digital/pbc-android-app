package com.kavi.pbc.droid.data.dto.notification

import kotlinx.serialization.Serializable

@Serializable
data class PushTokenRequest(
    val email: String,
    val pushToken: String
)
