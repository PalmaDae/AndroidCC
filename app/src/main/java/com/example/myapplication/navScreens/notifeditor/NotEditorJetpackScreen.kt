package com.example.myapplication.navScreens.notifeditor

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
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

    // Все строки выносим заранее
    val notificationIdText = stringResource(R.string.notification_id)
    val newTitleText = stringResource(R.string.new_title)
    val newContentText = stringResource(R.string.new_content_optional)
    val invalidIdText = stringResource(R.string.invalid_id)
    val errorNotExistText = stringResource(R.string.error_notification_not_exist)
    val notificationUpdatedText = stringResource(R.string.notification_updated)
    val updateText = stringResource(R.string.update)
    val clearAllText = stringResource(R.string.clear_all)
    val allClearedText = stringResource(R.string.all_notifications_cleared)
    val noNotificationsText = stringResource(R.string.no_notifications_to_clear)
    val messagesText = stringResource(R.string.messages)
    val editorText = stringResource(R.string.editor)
    val settingsText = stringResource(R.string.settings)

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
                label = { Text(notificationIdText) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text(newTitleText) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = newContent,
                onValueChange = { newContent = it },
                label = { Text(newContentText) },
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
                        Toast.makeText(context, invalidIdText, Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val success = NotificationsRepository.update(
                        context,
                        idInt,
                        newTitle,
                        newContent.ifBlank { null }
                    )

                    if (!success) {
                        Toast.makeText(context, errorNotExistText, Toast.LENGTH_LONG).show()
                        return@Button
                    }

                    Toast.makeText(context, notificationUpdatedText, Toast.LENGTH_SHORT).show()
                }) {
                    Text(updateText)
                }

                Button(onClick = {
                    val success = NotificationsRepository.clear()
                    Toast.makeText(
                        context,
                        if (success) allClearedText else noNotificationsText,
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Text(clearAllText)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onNavigateToMessage) { Text(messagesText) }
            Button(onClick = onNavigateToEditor) { Text(editorText) }
            Button(onClick = onNavigateToSettings) { Text(settingsText) }
        }
    }
}
