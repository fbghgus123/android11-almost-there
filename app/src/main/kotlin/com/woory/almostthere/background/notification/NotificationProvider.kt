package com.woory.almostthere.background.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.woory.almostthere.R
import com.woory.almostthere.ui.gaming.GamingActivity

object NotificationProvider {
    val PROMISE_NOTIFICATION_ID = 0

    private fun createNotificationBuilder(
        context: Context,
        channelId: String,
        title: String,
        content: String,
        priority: Int,
        intent: Intent,
        requestCode: Int = 0,
    ): NotificationCompat.Builder {

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            );
        }

        return NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(title)
            setContentText(content)
            setAutoCancel(true)
            this.priority = priority

            if (priority == NotificationCompat.PRIORITY_HIGH) {
                setFullScreenIntent(pendingIntent, true)
            } else {
                setContentIntent(pendingIntent)
            }
        }
    }

    fun notifyPromiseNotification(context: Context, title: String, content: String, promiseCode: String) {
        val notificationManager = NotificationManagerCompat.from(context)
        val intent = Intent(context, GamingActivity::class.java)
        intent.putExtra("PROMISE_CODE", promiseCode)

        val notification = createNotificationBuilder(
            context,
            NotificationChannelProvider.PROMISE_CHANNEL_ID,
            title,
            content,
            NotificationCompat.PRIORITY_HIGH,
            intent,
        )

        notificationManager.notify(PROMISE_NOTIFICATION_ID, notification.build())
    }

}