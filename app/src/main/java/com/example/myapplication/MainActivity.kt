package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.myapplication.db.InceptionDatabase
import com.example.myapplication.navigation.GameList
import com.example.myapplication.navigation.Login
import com.example.myapplication.navigation.NavigationHolder
import com.example.myapplication.utils.SessionManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            InceptionDatabase::class.java,
            "inception_db"
        ).build()

        val sessionManager = SessionManager(this)

        val startScreen = if (sessionManager.isLoggedIn()) {
            GameList
        } else {
            Login
        }

        setContent {
            NavigationHolder(startDestination = startScreen)
        }
    }
}