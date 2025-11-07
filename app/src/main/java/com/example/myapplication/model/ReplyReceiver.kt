package com.example.myapplication.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput

class ReplyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val notificationId = intent.getIntExtra("notification_id", 0)


        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        val replyText = remoteInput?.getCharSequence("reply_text")?.toString() ?: return


        MessagesRepository.add(Message(replyText))


        NotificationManagerCompat.from(context).cancel(notificationId)
    }
}
