package com.example.myapplication.navScreens.notifsettings

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.myapplication.model.NotificationModel
import com.example.myapplication.model.NotificationType
import com.example.myapplication.utils.NotificationsHandler

@Composable
fun NotSettingsJetpackScreen(
    onNavigateToMessage: () -> Unit,
    onNavigateToEditor: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val context = LocalContext.current
    val notificationsHandler = NotificationsHandler(context)

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var expandable by remember { mutableStateOf(false) }
    var openMain by remember { mutableStateOf(false) }
    var replyAction by remember { mutableStateOf(false) }
    var priority by remember { mutableStateOf(NotificationType.DEFAULT) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content (optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Switch(
                    checked = expandable,
                    onCheckedChange = { expandable = it },
                    enabled = content.isNotBlank()
                )
                Text("Expandable")
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Switch(
                    checked = openMain,
                    onCheckedChange = { openMain = it }
                )
                Text("Open MainActivity on click")
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Switch(
                    checked = replyAction,
                    onCheckedChange = { replyAction = it }
                )
                Text("Reply Action")
            }

            var expanded by remember { mutableStateOf(false) }
            Box {
                Button(onClick = { expanded = true }) {
                    Text("Priority: $priority")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    NotificationType.values().forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type.name) },
                            onClick = {
                                priority = type
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (title.isBlank()) {
                    Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val permissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                } else true

                if (!permissionGranted) {
                    Toast.makeText(context, "Notification permission not granted", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val notification = NotificationModel(
                    id = (System.currentTimeMillis() % 10000).toInt(),
                    title = title,
                    content = content.ifBlank { null },
                    type = priority
                )
                notificationsHandler.showNotification(notification, openMain, expandable, replyAction)
                Toast.makeText(context, "Notification sent", Toast.LENGTH_SHORT).show()
            }) {
                Text("Send Notification")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onNavigateToMessage) { Text("Messages") }
            Button(onClick = onNavigateToEditor) { Text("Editor") }
            Button(onClick = onNavigateToSettings) { Text("Settings") }
        }
    }
}
