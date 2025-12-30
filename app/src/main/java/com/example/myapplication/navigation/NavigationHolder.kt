package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationHolder(startDestination: Any) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Login> {

        }
        composable<Registration> {

        }
        composable<GameList> {

        }
        composable<AddGame> {

        }
        composable<Profile> {

        }
    }
}