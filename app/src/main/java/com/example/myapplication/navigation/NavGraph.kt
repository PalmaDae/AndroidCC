package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screen.DetailScreen
import com.example.myapplication.ui.screen.SearchApp

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            SearchApp(vm = viewModel(), navController = navController)
        }
        composable("detail/{pointId}") { backStackEntry ->
            val pointId = backStackEntry.arguments?.getString("pointId")?.toIntOrNull()
            pointId?.let { DetailScreen(it) }
        }
    }
}