package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.navScreens.messagescreen.MessageJetpackScreen
import com.example.myapplication.navScreens.notifeditor.NotEditorJetpackScreen
import com.example.myapplication.navScreens.notifsettings.NotSettingsJetpackScreen

@Composable
fun JetpackNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = JetpackNavigationIds.MESSAGE_SCREEN.destination
    ) {
        composable(JetpackNavigationIds.MESSAGE_SCREEN.destination) {
            MessageJetpackScreen(
                onNavigateToEditor = { navController.navigate(JetpackNavigationIds.EDITOR_SCREEN.destination) },
                onNavigateToSettings = { navController.navigate(JetpackNavigationIds.SETTINGS_SCREEN.destination) }
            )
        }
        composable(JetpackNavigationIds.EDITOR_SCREEN.destination) {
            NotEditorJetpackScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(JetpackNavigationIds.SETTINGS_SCREEN.destination) {
            NotSettingsJetpackScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}