package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.UserDataRepository
import com.example.myapplication.db.entity.GameEntity
import com.example.myapplication.di.ServiceLocator
import com.example.myapplication.slider.RatingSlider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameScreen(navController: NavController) {
    val statusPlanned = stringResource(R.string.status_planned)
    val statusPlaying = stringResource(R.string.status_playing)
    val statusCompleted = stringResource(R.string.status_completed)

    var title by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(statusPlanned) }
    var rating by remember { mutableFloatStateOf(5f) }
    var impressions by remember { mutableStateOf("") }

    val currentLogin = UserDataRepository.getCurrentLogin()

    var expanded by remember { mutableStateOf(false) }
    val statusOptions = listOf(statusCompleted, statusPlaying, statusPlanned)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val titleRequiredMsg = stringResource(R.string.add_game_snackbar_error)

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
                    text = stringResource(R.string.add_game_screen_title),
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
                    label = { Text(stringResource(R.string.add_game_label_title)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = title.isBlank(),
                    supportingText = {
                        if (title.isBlank()) Text(stringResource(R.string.add_game_error_required))
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
                        label = { Text(stringResource(R.string.add_game_label_status)) },
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

                if (status == statusCompleted || status == statusPlaying) {
                    RatingSlider(
                        rating = rating,
                        onRatingChange = { rating = it }
                    )
                }

                OutlinedTextField(
                    value = impressions,
                    onValueChange = { impressions = it },
                    label = { Text(stringResource(R.string.add_game_label_impressions)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (title.isBlank()) {
                            scope.launch { snackbarHostState.showSnackbar(titleRequiredMsg) }
                            return@Button
                        }

                        scope.launch {
                            val game = GameEntity(
                                userLogin = currentLogin ?: "",
                                title = title,
                                rating = if (status == statusPlanned) 0 else rating.toInt(),
                                status = status,
                                impres = impressions
                            )
                            ServiceLocator.getGameRepository().addGame(game)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.add_game_button_save))
                }

                TextButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.add_game_button_cancel))
                }
            }
        }
    }
}