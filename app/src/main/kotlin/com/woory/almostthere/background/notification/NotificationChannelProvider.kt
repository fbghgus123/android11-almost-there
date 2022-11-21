package com.woory.almostthere.background.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.woory.almostthere.R

object NotificationChannelProvider {
    const val PROMISE_CHANNEL_ID = "PromiseNotificationChannel"

    private fun createNotificationChannel(
        context: Context, importance: Int, showBadge: Boolean,
        name: String, description: String, channelId: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun providePromiseChannel(context: Context) {
        val name = context.getString(R.string.notification_promise_name)
        val description = context.getString(R.string.notification_promise_description)

        createNotificationChannel(
            context,
            NotificationManager.IMPORTANCE_HIGH,
            true,
            name,
            description,
            PROMISE_CHANNEL_ID
        )
    }

}