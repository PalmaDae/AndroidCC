package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Note

class SharedViewModel : ViewModel() {
    val userEmail = mutableStateOf("")
    val notes = mutableStateOf(listOf<Note>())

    fun addNote(note: Note) {
        notes.value = notes.value + note
    }
}