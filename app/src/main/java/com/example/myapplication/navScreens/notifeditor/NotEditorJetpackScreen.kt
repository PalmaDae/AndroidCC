package com.example.myapplication.navScreens.notifeditor

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.NotificationsRepository

@Composable
fun NotEditorJetpackScreen(
    onNavigateToMessage: () -> Unit,
    onNavigateToEditor: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onBack: () -> Boolean
) {
    val context = LocalContext.current
    var notifId by remember { mutableStateOf("") }
    var newTitle by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = notifId,
                onValueChange = { notifId = it },
                label = { Text("Notification ID") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text("New Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = newContent,
                onValueChange = { newContent = it },
                label = { Text("New Content (optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    val idInt = notifId.toIntOrNull()
                    if (idInt == null) {
                        Toast.makeText(context, "Invalid ID", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val success = NotificationsRepository.update(
                        context,
                        idInt,
                        newTitle,
                        newContent.ifBlank { null }
                    )

                    if (!success) {
                        Toast.makeText(context, "Error: Notification with this ID does not exist", Toast.LENGTH_LONG).show()
                        return@Button
                    }

                    Toast.makeText(context, "Notification updated", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Update")
                }



                Button(onClick = {
                    val success = NotificationsRepository.clear()
                    Toast.makeText(
                        context,
                        if (success) "All notifications cleared" else "No notifications to clear",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Text("Clear All")
                }
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
