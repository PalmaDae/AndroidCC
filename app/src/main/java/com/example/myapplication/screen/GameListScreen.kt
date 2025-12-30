package com.example.myapplication.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.data.UserDataRepository
import com.example.myapplication.db.entity.GameEntity
import com.example.myapplication.di.ServiceLocator
import com.example.myapplication.navigation.AddGame
import com.example.myapplication.navigation.Profile
import com.example.myapplication.viewmodel.GameListViewModel
import com.example.myapplication.viewmodel.SortType
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListScreen(
    navController: NavController,
    viewModel: GameListViewModel = viewModel()
) {
    val games by viewModel.games.collectAsState()
    val currentSort by viewModel.sortType.collectAsState()

    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Game Collection") },
                actions = {
                    IconButton(onClick = { showSheet = true }) {
                        Icon(Icons.Default.List, contentDescription = "Sort")
                    }
                    IconButton(onClick = { navController.navigate(Profile) }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(AddGame) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Game")
            }
        }
    ) { padding ->
        if (games.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No games added yet")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(games) { game ->
                    GameItem(game = game, onDelete = {
                        scope.launch {
                            ServiceLocator.getGameRepository().deleteGame(game)
                        }
                    })
                }
            }
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 40.dp)
                ) {
                    Text(
                        "Sort by",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    SortOption("Name", currentSort == SortType.NAME) {
                        viewModel.setSortType(SortType.NAME)
                        showSheet = false
                    }
                    SortOption("Rating", currentSort == SortType.RATING) {
                        viewModel.setSortType(SortType.RATING)
                        showSheet = false
                    }
                    SortOption("Status", currentSort == SortType.STATUS) {
                        viewModel.setSortType(SortType.STATUS)
                        showSheet = false
                    }
                }
            }
        }
    }
}

@Composable
fun GameItem(game: GameEntity, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
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
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Status: ${game.status}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = "Rating: ${game.rating}/10",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun SortOption(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = onClick)
        Text(text = label, modifier = Modifier.padding(start = 8.dp))
    }
}