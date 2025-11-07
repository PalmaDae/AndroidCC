package com.example.myapplication.model

import androidx.annotation.DrawableRes

data class NotificationModel(
    val id: Int,
    val title: String,
    val content: String? = null,
    @DrawableRes
    val icon: Int? = null,
    val type: NotificationType = NotificationType.DEFAULT
)

enum class NotificationType {
    AUTH,
    PROMO,
    SETTINGS,
    DEFAULT,
}