package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.AddNoteScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.NoteScreen
import com.example.myapplication.viewmodel.SharedViewModel

object Routes {
    const val LOGIN = "login"
    const val NOTES = "notes"
    const val ADD_NOTE = "add_note"
}


@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: SharedViewModel
) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable(Routes.NOTES) {
            NoteScreen(navController = navController, viewModel = viewModel)
        }
        composable(Routes.ADD_NOTE) {
            AddNoteScreen(navController = navController, viewModel = viewModel)
        }
    }
}