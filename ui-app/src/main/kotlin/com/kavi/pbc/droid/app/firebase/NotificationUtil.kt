package com.kavi.pbc.droid.app.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.firebase.messaging.RemoteMessage
import com.kavi.pbc.droid.app.ui.PBCActivity
import com.kavi.pbc.droid.data.model.NotificationData
import com.kavi.pbc.droid.lib.parent.extension.simpleSerializeData

object NotificationUtil {

    private const val GENERAL_CHANNEL_ID = "GENERAL_CHANNEL_ID"
    private const val EVENT_CHANNEL_ID = "EVENT_CHANNEL_ID"
    private const val NEWS_CHANNEL_ID = "NEWS_CHANNEL_ID"
    private const val BROADCAST_CHANNEL_ID = "BROADCAST_CHANNEL_ID"
    private const val APPOINTMENT_CHANNEL_ID = "APPOINTMENT_CHANNEL_ID"

    fun getNotificationId(): Int {
        return System.currentTimeMillis().toInt()
    }

    fun getChannelId(message: RemoteMessage): String {
        var channelId: String = GENERAL_CHANNEL_ID

        if (message.data.isNotEmpty()) {
            channelId = message.data["CHANNEL"] ?: run { GENERAL_CHANNEL_ID }
        }

        return channelId
    }

    fun getNotificationChannel(message: RemoteMessage): NotificationChannel {
        val channelId: String = getChannelId(message)
        val channel =  NotificationChannel(channelId, getChannelName(channelId),
            NotificationManager.IMPORTANCE_HIGH)
        channel.importance = NotificationManager.IMPORTANCE_HIGH
        return channel
    }

    private fun getChannelName(channelId: String?): String {
        return when(channelId) {
            EVENT_CHANNEL_ID -> "Event publishing Notification"
            NEWS_CHANNEL_ID -> "News Notification"
            BROADCAST_CHANNEL_ID -> "Broadcast Notification"
            APPOINTMENT_CHANNEL_ID -> "Appointment Notification"
            else -> "General Notification"
        }
    }

    fun getNotificationData(channelId: String, message: RemoteMessage): NotificationData? {
        return if (message.data.isNotEmpty()) {
            when (channelId) {
                EVENT_CHANNEL_ID -> {
                    val eventId = message.data["EVENT_ID"]
                    NotificationData(channelId = channelId, eventId = eventId)
                }
                NEWS_CHANNEL_ID -> {
                    val newsId = message.data["NEWS_ID"]
                    NotificationData(channelId = channelId, newsId = newsId)
                }
                APPOINTMENT_CHANNEL_ID -> {
                    val appointmentId = message.data["APPOINTMENT_ID"]
                    NotificationData(channelId = channelId, newsId = appointmentId)
                }
                BROADCAST_CHANNEL_ID -> { null }
                else -> { null }
            }
        } else
            null
    }

    fun getPendingIntent(context: Context, channelId: String, message: RemoteMessage): PendingIntent {
        val intent: Intent
        when(channelId) {
            EVENT_CHANNEL_ID -> {
                intent = Intent()
                intent.action = Intent.ACTION_VIEW
                getNotificationData(channelId, message)?.let {
                    // TODO - This is to handle and navigate via deep-links to specific event
                    //val surveyIdEncoded = Base64.encodeToString(it.surveyId?.toByteArray(StandardCharsets.UTF_8), Base64.DEFAULT)
                    //intent.data = Uri.parse("app://${NetworkConfig.networkConfig.domain}/survey/details/$surveyIdEncoded");
                }
            }
            else -> {
                intent = Intent(context, PBCActivity::class.java)
                getNotificationData(channelId, message)?.let {
                    intent.putExtra("APP_NOTIFICATION_DATA", simpleSerializeData(it))
                }
            }
        }

        return PendingIntent.getActivity(context, System.currentTimeMillis().toInt(),
            intent, PendingIntent.FLAG_IMMUTABLE)
    }
}