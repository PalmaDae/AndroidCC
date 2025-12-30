package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplication.screen.AddGameScreen
import com.example.myapplication.screen.GameListScreen
import com.example.myapplication.screen.LoginScreen
import com.example.myapplication.screen.ProfileScreen
import com.example.myapplication.screen.RegistrationScreen
import com.example.myapplication.screen.RestoreAccountScreen

@Composable
fun NavigationHolder(startDestination: Any) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Login> {
            LoginScreen(navController = navController)
        }
        composable<Registration> {
            RegistrationScreen(navController = navController)
        }
        composable<GameList> {
            GameListScreen(navController = navController)
        }
        composable<AddGame> {
            AddGameScreen(navController = navController)
        }
        composable<Profile> {
            ProfileScreen(navController = navController)
        }
        composable<RestoreAccount> { backStackEntry ->
            val args = backStackEntry.toRoute<RestoreAccount>()
            RestoreAccountScreen(login = args.login, navController = navController)
        }
    }
}