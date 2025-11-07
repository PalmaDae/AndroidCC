package com.example.myapplication.model

import androidx.compose.runtime.mutableStateListOf

object MessagesRepository {
    private val _messages = mutableStateListOf<Message>()
    val messages: List<Message> get() = _messages

    fun add(message: Message) {
        _messages.add(message)
    }
}