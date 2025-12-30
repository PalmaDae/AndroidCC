package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.UserDataRepository
import com.example.myapplication.db.entity.GameEntity
import com.example.myapplication.di.ServiceLocator
import com.example.myapplication.navigation.AddGame
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListScreen(navController: NavController) {
    val gameRepository = ServiceLocator.getGameRepository()
    val scope = rememberCoroutineScope()
    val currentLogin = UserDataRepository.getCurrentLogin() ?: ""
    val games by gameRepository.getGamesForUser(currentLogin ?: "").collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Game Collection") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(AddGame) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Game")
            }
        }
    ) { padding ->
        if (games.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No games added yet", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(games) { game ->
                    GameItem(
                        game = game,
                        onDelete = {
                            scope.launch {
                                gameRepository.deleteGame(game)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GameItem(game: GameEntity, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = game.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "Status: ${game.status}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Rating: ${game.rating}/10", color = Color.Gray)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
        }
    }
}