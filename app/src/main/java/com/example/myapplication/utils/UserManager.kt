package com.example.myapplication.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManager @Inject constructor(
    private val application: Application
) {
    private val prefs: SharedPreferences = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun getUserId(): String {
        var userId = prefs.getString("user_id", null)
        if (userId == null) {
            userId = UUID.randomUUID().toString()
            prefs.edit().putString("user_id", userId).apply()
        }
        return userId
    }
}