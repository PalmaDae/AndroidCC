package com.example.myapplication.model

import androidx.compose.runtime.mutableStateListOf

object MessagesRepository {
    val messages = mutableStateListOf<Message>()

    fun add(message: Message) {
        messages.add(message)
    }
}