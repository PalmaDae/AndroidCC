package com.example.myapplication.navScreens.messagescreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.model.Message
import com.example.myapplication.model.MessagesRepository
import com.example.myapplication.model.NotificationModel
import com.example.myapplication.utils.NotificationsHandler

@Composable
fun MessageJetpackScreen(
    onNavigateToMessage: () -> Unit,
    onNavigateToEditor: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val context = LocalContext.current
    var newMessage by remember { mutableStateOf("") }
    val newMessageTitle = stringResource(R.string.new_message)
    val addMessageText = stringResource(R.string.add_message)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedTextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                label = { Text(stringResource(R.string.enter_message)) },
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Button(onClick = {
                if (newMessage.isNotBlank()) {
                    val msg = Message(newMessage)
                    MessagesRepository.add(msg)

                    val notificationsHandler = NotificationsHandler(context)
                    val notification = NotificationModel(
                        id = (System.currentTimeMillis() % 10000).toInt(),
                        title = newMessageTitle,
                        content = newMessage
                    )
                    notificationsHandler.showNotification(
                        notification,
                        openMainActivity = true,
                        expandable = true,
                        replyActionEnabled = true
                    )

                    newMessage = ""
                }
            }) {
                Text(addMessageText)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(MessagesRepository.messages) { msg ->
                Text(msg.text, modifier = Modifier.padding(4.dp))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onNavigateToMessage) { Text(stringResource(R.string.messages)) }
            Button(onClick = onNavigateToEditor) { Text(stringResource(R.string.editor)) }
            Button(onClick = onNavigateToSettings) { Text(stringResource(R.string.settings)) }
        }
    }
}
