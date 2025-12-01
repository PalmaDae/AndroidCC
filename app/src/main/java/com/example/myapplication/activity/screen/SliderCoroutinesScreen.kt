package com.example.myapplication.activity.screen

import CoroutineViewModel
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SliderCoroutinesScreen(viewModel: CoroutineViewModel = viewModel()) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when(event) {
                is CoroutineViewModel.UiEvent.ShowToast ->
                    Toast.makeText(context, context.getString(event.messageRes), Toast.LENGTH_SHORT).show()
                is CoroutineViewModel.UiEvent.ShowSnackbar ->
                    Toast.makeText(context, context.getString(event.messageRes), Toast.LENGTH_SHORT).show()
                CoroutineViewModel.UiEvent.ResetSettings -> {
                    viewModel.sliderValue = 10f
                    viewModel.sequential = true
                    viewModel.parallel = false
                    viewModel.delayedStart = false
                    viewModel.selectedDispatcher = R.string.default_dispatcher
                }
            }
        }
    }




    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(stringResource(R.string.coroutines_count) + ": ${viewModel.sliderValue.toInt()}")
            Slider(
                value = viewModel.sliderValue,
                onValueChange = { viewModel.sliderValue = it },
                valueRange = 10f..100f,
                steps = 17,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.secondary,
                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                )
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = viewModel.sequential,
                    onCheckedChange = {
                        viewModel.sequential = it
                        viewModel.parallel = !it
                    }
                )
                Text(stringResource(R.string.sequential))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = viewModel.parallel,
                    onCheckedChange = {
                        viewModel.parallel = it
                        viewModel.sequential = !it
                    }
                )
                Text(stringResource(R.string.parallel))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = viewModel.delayedStart,
                    onCheckedChange = { viewModel.delayedStart = it }
                )
                Text(stringResource(R.string.delayed_start))
            }

            var expanded by remember { mutableStateOf(false) }
            Box {
                Text(
                    text = stringResource(viewModel.selectedDispatcher),
                    modifier = Modifier
                        .clickable { expanded = true }
                        .padding(8.dp)
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    listOf(
                        R.string.default_dispatcher,
                        R.string.dispatcher_io
                    ).forEach { dispatcherRes ->
                        DropdownMenuItem(
                            text = { Text(stringResource(dispatcherRes)) },
                            onClick = {
                                viewModel.selectedDispatcher = dispatcherRes
                                expanded = false
                            }
                        )
                    }
                }
            }



            if (viewModel.isRunning) {
                CircularProgressIndicator()
            } else {
                Button(onClick = { viewModel.startCoroutines() }) {
                    Text(stringResource(R.string.start_coroutines))
                }
            }
        }
    }
}
