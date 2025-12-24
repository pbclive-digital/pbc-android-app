package com.kavi.pbc.droid.data.model

data class NotificationData(
    val channelId: String,
    val eventId: String? = null,
    val newsId: String? = null,
    val appointmentId: String? = null,
    val broadcastMessage: String? = null
)
