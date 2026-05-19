package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.MyApplication
import com.example.myapplication.ui.screen.DetailScreen
import com.example.myapplication.ui.screen.SearchApp

@Composable
fun AppNavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val appComponent = (context.applicationContext as MyApplication).appComponent
    val analyticsLogger = appComponent.getAnalyticsLogger()

    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            LaunchedEffect(Unit) {
                analyticsLogger.logScreenView("search_screen")
            }
            SearchApp(navController = navController)
        }

        composable(
            "detail/{pointId}",
            arguments = listOf(navArgument("pointId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pointId = backStackEntry.arguments?.getInt("pointId") ?: 0
            LaunchedEffect(pointId) {
                analyticsLogger.logScreenView("detail_screen_$pointId")
                analyticsLogger.logNavigation("search", "detail", "pointId=$pointId")
            }
            DetailScreen(pointId = pointId)
        }
    }
}