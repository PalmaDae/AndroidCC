package com.example.myapplication.data

import android.content.SharedPreferences

object UserDataRepository {

    private var sharedPref: SharedPreferences? = null

    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_USER_LOGIN = "user_login"

    fun provideSharedPrefs(sp: SharedPreferences) {
        if (sharedPref == null) sharedPref = sp
    }

    fun isLoggedIn(): Boolean {
        return sharedPref?.getBoolean(KEY_IS_LOGGED_IN, false) ?: false
    }

    fun getCurrentLogin(): String? {
        return sharedPref?.getString(KEY_USER_LOGIN, null)
    }

    fun saveSession(login: String) {
        sharedPref?.edit()?.apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_USER_LOGIN, login)
            apply()
        }
    }

    fun clearSession() {
        sharedPref?.edit()?.clear()?.apply()
    }
}