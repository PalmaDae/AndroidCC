package com.example.myapplication.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("inception_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LOGIN = "current_user_login"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    fun saveSession(login: String) {
        prefs.edit().apply {
            putString(KEY_LOGIN, login)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    fun fetchLogin(): String? = prefs.getString(KEY_LOGIN, null)

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}