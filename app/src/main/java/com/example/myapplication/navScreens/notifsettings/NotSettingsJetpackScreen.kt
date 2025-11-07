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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.model.NotificationModel
import com.example.myapplication.model.NotificationType
import com.example.myapplication.model.NotificationsRepository
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

    // Все строки выносим заранее
    val titleLabelText = stringResource(R.string.title_label)
    val contentOptionalText = stringResource(R.string.content_optional)
    val expandableText = stringResource(R.string.expandable)
    val openMainText = stringResource(R.string.open_main_activity)
    val replyActionText = stringResource(R.string.reply_action)
    val priorityLabelText = stringResource(R.string.priority_label, priority.name)
    val titleCannotBeEmptyText = stringResource(R.string.title_cannot_be_empty)
    val notificationPermissionNotGrantedText = stringResource(R.string.notification_permission_not_granted)
    val sendNotificationText = stringResource(R.string.send_notification)
    val notificationSentWithIdText = stringResource(R.string.notification_sent_with_id, 0) // временно 0, заменим в onClick
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
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(titleLabelText) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text(contentOptionalText) },
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Switch(
                    checked = expandable,
                    onCheckedChange = { expandable = it },
                    enabled = content.isNotBlank()
                )
                Text(expandableText)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Switch(
                    checked = openMain,
                    onCheckedChange = { openMain = it }
                )
                Text(openMainText)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Switch(
                    checked = replyAction,
                    onCheckedChange = { replyAction = it }
                )
                Text(replyActionText)
            }

            var expanded by remember { mutableStateOf(false) }
            Box {
                Button(onClick = { expanded = true }) {
                    Text(stringResource(R.string.priority_label, priority.name))
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
                    Toast.makeText(context, titleCannotBeEmptyText, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val permissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                } else true

                if (!permissionGranted) {
                    Toast.makeText(context, notificationPermissionNotGrantedText, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val notification = NotificationModel(
                    id = (System.currentTimeMillis() % 10000).toInt(),
                    title = title,
                    content = content.ifBlank { null },
                    type = priority
                )

                notificationsHandler.showNotification(notification, openMain, expandable, replyAction)
                NotificationsRepository.add(notification)

                Toast.makeText(
                    context,
                    context.getString(R.string.notification_sent_with_id, notification.id),
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Text(sendNotificationText)
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
