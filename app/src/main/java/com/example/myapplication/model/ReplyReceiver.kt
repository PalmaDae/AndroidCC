package com.example.myapplication.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.myapplication.R
import com.example.myapplication.utils.NotificationsHandler

class ReplyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val notificationId = intent.getIntExtra("notification_id", -1)
        if (notificationId == -1) return

        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        val replyText = remoteInput?.getCharSequence("reply_text")?.toString() ?: return

        MessagesRepository.add(Message(replyText))

        NotificationManagerCompat.from(context).cancel(notificationId)

        val notificationsHandler = NotificationsHandler(context)
        notificationsHandler.showNotification(
            NotificationModel(
                id = notificationId,
                title = context.getString(R.string.new_message),
                content = replyText
            ),
            openMainActivity = false,
            expandable = true,
            replyActionEnabled = true
        )
    }
}
