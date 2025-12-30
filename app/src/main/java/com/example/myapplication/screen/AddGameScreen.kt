package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.UserDataRepository
import com.example.myapplication.db.entity.GameEntity
import com.example.myapplication.di.ServiceLocator
import com.example.myapplication.slider.RatingSlider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Planned") }
    var rating by remember { mutableFloatStateOf(5f) }
    var impressions by remember { mutableStateOf("") }

    val currentLogin = UserDataRepository.getCurrentLogin()

    var expanded by remember { mutableStateOf(false) }
    val statusOptions = listOf("Completed", "Playing", "Planned")

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Add New Game",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { if (it.length <= 50) title = it },
                    label = { Text("Game Title") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = title.isBlank(),
                    supportingText = {
                        if (title.isBlank()) Text("Required field")
                    }
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = status,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Status") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        statusOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    status = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                if (status == "Completed" || status == "Playing") {
                    RatingSlider(
                        rating = rating,
                        onRatingChange = { rating = it }
                    )
                }

                OutlinedTextField(
                    value = impressions,
                    onValueChange = { impressions = it },
                    label = { Text("Your Impressions") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (title.isBlank()) {
                            scope.launch { snackbarHostState.showSnackbar("Title is required") }
                            return@Button
                        }

                        scope.launch {
                            val game = GameEntity(
                                userLogin = currentLogin ?: "",
                                title = title,
                                rating = if (status == "Planned") 0 else rating.toInt(),
                                status = status,
                                impres = impressions
                            )
                            ServiceLocator.getGameRepository().addGame(game)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Game")
                }

                TextButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}