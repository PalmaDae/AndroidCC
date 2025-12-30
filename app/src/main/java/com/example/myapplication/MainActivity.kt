package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.myapplication.data.UserDataRepository
import com.example.myapplication.data.UserRepository
import com.example.myapplication.db.InceptionDatabase
import com.example.myapplication.di.ServiceLocator
import com.example.myapplication.navigation.GameList
import com.example.myapplication.navigation.Login
import com.example.myapplication.navigation.NavigationHolder
import com.example.myapplication.utils.SessionManager
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ServiceLocator.initDatabase(applicationContext)

        val sharedPrefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        UserDataRepository.provideSharedPrefs(sharedPrefs)

        val startScreen = if (UserDataRepository.isLoggedIn()) {
            GameList
        } else {
            Login
        }

        setContent {
            NavigationHolder(startDestination = startScreen)
        }
    }
}