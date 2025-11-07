package com.example.myapplication.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.model.NotificationModel
import com.example.myapplication.model.ReplyReceiver

class NotificationsHandler(private val context: Context) {

    private val channelId = "my_app_channel"
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "App Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for app notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(
        notification: NotificationModel,
        openMainActivity: Boolean = false,
        expandable: Boolean = false,
        replyActionEnabled: Boolean = false
    ) {

        val intent = if (openMainActivity) {
            Intent(context, MainActivity::class.java).apply {
                putExtra("title", notification.title)
                putExtra("content", notification.content)
            }
        } else null

        val pendingIntent = intent?.let {
            PendingIntent.getActivity(
                context,
                notification.id,
                it,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(notification.icon ?: R.drawable.ic_launcher_foreground)
            .setContentTitle(notification.title)
            .setContentText(notification.content ?: "")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .apply {
                if (pendingIntent != null) setContentIntent(pendingIntent)
                if (expandable && notification.content != null) {
                    setStyle(NotificationCompat.BigTextStyle().bigText(notification.content))
                }


                if (replyActionEnabled) {
                    val replyLabel = "Reply"
                    val remoteInput = RemoteInput.Builder("reply_text")
                        .setLabel(replyLabel)
                        .build()

                    val replyIntent = Intent(context, ReplyReceiver::class.java).apply {
                        putExtra("notification_id", notification.id)
                    }

                    val replyPendingIntent = PendingIntent.getBroadcast(
                        context,
                        notification.id,
                        replyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    val action = NotificationCompat.Action.Builder(
                        R.drawable.ic_launcher_foreground,
                        "Reply",
                        replyPendingIntent
                    ).addRemoteInput(remoteInput)
                        .build()

                    addAction(action)
                }
            }

        NotificationManagerCompat.from(context).notify(notification.id, builder.build())
    }
}
