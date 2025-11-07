package com.example.myapplication.model

object MessagesRepository {
    private val messages = mutableListOf<Message>()

    fun add(message: Message) {
        messages.add(message)
    }

    fun getAll(): List<Message> = messages.toList()
}
