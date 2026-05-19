package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.ui.screen.DetailScreen
import com.example.myapplication.ui.screen.SearchApp

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            SearchApp(navController = navController)
        }

        composable(
            "detail/{pointId}",
            arguments = listOf(navArgument("pointId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pointId = backStackEntry.arguments?.getInt("pointId") ?: 0
            DetailScreen(pointId = pointId)
        }
    }
}