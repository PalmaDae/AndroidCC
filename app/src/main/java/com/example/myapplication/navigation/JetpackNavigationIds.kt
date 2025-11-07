package com.example.myapplication.navigation

import androidx.navigation.compose.ComposeNavigator

enum class JetpackNavigationIds(val destination: String) {
    MESSAGE_SCREEN("message_screen"),
    EDITOR_SCREEN("editor_screen"),
    SETTINGS_SCREEN("settings_screen")
}