package com.example.myapplication.utils

import android.util.Patterns

object Validation {
    fun isEmailValid(email: String): Boolean {
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank() && password.length >= 8
    }
}