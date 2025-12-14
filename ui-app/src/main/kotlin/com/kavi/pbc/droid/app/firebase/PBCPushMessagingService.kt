package com.kavi.pbc.droid.app.firebase

import android.app.Notification
import android.app.NotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kavi.pbc.droid.app.firebase.NotificationUtil.getChannelId
import com.kavi.pbc.droid.app.firebase.NotificationUtil.getNotificationChannel
import com.kavi.pbc.droid.app.firebase.NotificationUtil.getNotificationId
import com.kavi.pbc.droid.app.firebase.NotificationUtil.getPendingIntent
import com.kavi.pbc.droid.lib.common.ui.R
import com.kavi.pbc.droid.lib.datastore.AppDatastore
import com.kavi.pbc.droid.lib.datastore.DataKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PBCPushMessagingService: FirebaseMessagingService() {

    @Inject
    lateinit var appDatastore: AppDatastore

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // Update the new push token status
        CoroutineScope(Dispatchers.IO).launch {
            appDatastore.storeValue(DataKey.APP_IS_NEED_TO_UPDATE_PUSH_TOKEN, true)
            appDatastore.storeValue(DataKey.APP_PUSH_TOKEN, token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(message = message)
    }

    private fun showNotification(message: RemoteMessage) {

        val title = message.notification?.title
        val messageBody = message.notification?.body

        val channelId = getChannelId(message)
        val channel = getNotificationChannel(message)
        val pendingIntent = getPendingIntent(this, channelId, message)

        val notification = Notification.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setSmallIcon(R.drawable.icon_pbc)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)


        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(getNotificationId(), notification.build())
    }
}