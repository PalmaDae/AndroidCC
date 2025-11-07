package com.example.myapplication.model

import androidx.compose.runtime.mutableStateListOf

object NotificationsRepository {
    private val _notifications = mutableStateListOf<NotificationModel>()
    val notifications: List<NotificationModel> get() = _notifications

    fun add(notification: NotificationModel) {
        _notifications.add(notification)
    }

    fun update(id: Int, newTitle: String, newContent: String?): Boolean {
        val notif = _notifications.find { it.id == id } ?: return false
        val index = _notifications.indexOf(notif)
        _notifications[index] = notif.copy(title = newTitle, content = newContent)
        return true
    }

    fun clear(): Boolean {
        if (_notifications.isEmpty()) return false
        _notifications.clear()
        return true
    }
}
