package com.example.myapplication.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navScreens.notifeditor.NotEditorJetpackScreen
import com.example.myapplication.navScreens.notifsettings.NotSettingsJetpackScreen

@Composable
fun BottomNavApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val currentScreen = BottomNavItem.values().find { it.screen.destination == currentDestination }
        ?: BottomNavItem.MESSAGES

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomNavItem.values().forEach { item ->
                    NavigationBarItem(
                        icon = {},                         label = { Text(item.label) },
                        selected = currentScreen == item,
                        onClick = {
                            navController.navigate(item.screen.destination) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = JetpackNavigationIds.MESSAGE_SCREEN.destination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(JetpackNavigationIds.MESSAGE_SCREEN.destination) {
                MessageJetpackScreen(
                    onNavigateToEditor = { navController.navigate(JetpackNavigationIds.EDITOR_SCREEN.destination) },
                    onNavigateToSettings = { navController.navigate(JetpackNavigationIds.SETTINGS_SCREEN.destination) }
                )
            }

            composable(JetpackNavigationIds.EDITOR_SCREEN.destination) {
                NotEditorJetpackScreen(
                    onNavigateToMessage = { navController.navigate(JetpackNavigationIds.MESSAGE_SCREEN.destination) },
                    onNavigateToEditor = { navController.navigate(JetpackNavigationIds.EDITOR_SCREEN.destination) },
                    onNavigateToSettings = { navController.navigate(JetpackNavigationIds.SETTINGS_SCREEN.destination) },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(JetpackNavigationIds.SETTINGS_SCREEN.destination) {
                NotSettingsJetpackScreen(
                    onNavigateToMessage = { navController.navigate(JetpackNavigationIds.MESSAGE_SCREEN.destination) },
                    onNavigateToEditor = { navController.navigate(JetpackNavigationIds.EDITOR_SCREEN.destination) },
                    onNavigateToSettings = { navController.navigate(JetpackNavigationIds.SETTINGS_SCREEN.destination) }
                )
            }

        }

    }
}

@Composable
fun MessageJetpackScreen(onNavigateToEditor: () -> Unit, onNavigateToSettings: () -> Unit) {
    TODO("Not yet implemented")
}

enum class BottomNavItem(val screen: JetpackNavigationIds, val label: String) {
    MESSAGES(JetpackNavigationIds.MESSAGE_SCREEN, "Messages"),
    EDITOR(JetpackNavigationIds.EDITOR_SCREEN, "Editor"),
    SETTINGS(JetpackNavigationIds.SETTINGS_SCREEN, "Settings")
}
