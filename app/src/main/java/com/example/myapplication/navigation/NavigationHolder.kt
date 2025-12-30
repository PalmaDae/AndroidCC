package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screen.AddGameScreen
import com.example.myapplication.screen.GameListScreen
import com.example.myapplication.screen.LoginScreen
import com.example.myapplication.screen.ProfileScreen
import com.example.myapplication.screen.RegistrationScreen

@Composable
fun NavigationHolder(startDestination: Any) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Login> {
            LoginScreen()
        }
        composable<Registration> {
            RegistrationScreen()
        }
        composable<GameList> {
            GameListScreen()
        }
        composable<AddGame> {
            AddGameScreen()
        }
        composable<Profile> {
            ProfileScreen()
        }
    }
}