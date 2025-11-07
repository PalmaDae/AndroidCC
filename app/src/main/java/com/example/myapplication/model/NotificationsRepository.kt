package com.example.myapplication.model

import android.content.Context
import com.example.myapplication.utils.NotificationsHandler

object NotificationsRepository {

    private val notifications = mutableMapOf<Int, NotificationModel>()

    fun add(notification: NotificationModel) {
        notifications[notification.id] = notification
    }

    fun get(id: Int): NotificationModel? = notifications[id]

    fun update(
        context: Context,
        id: Int,
        newTitle: String?,
        newContent: String?
    ): Boolean {
        val oldNotif = notifications[id] ?: return false

        val updated = oldNotif.copy(
            title = newTitle?.ifBlank { oldNotif.title } ?: oldNotif.title,
            content = newContent?.ifBlank { oldNotif.content } ?: oldNotif.content
        )

        notifications[id] = updated

        NotificationsHandler(context).showNotification(
            updated,
            openMainActivity = false,
            expandable = updated.content != null,
            replyActionEnabled = false
        )

        return true
    }


    fun clear(): Boolean {
        val had = notifications.isNotEmpty()
        notifications.clear()
        return had
    }
}
