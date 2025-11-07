package com.example.myapplication.navScreens.messagescreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Message
import com.example.myapplication.model.MessagesRepository
import com.example.myapplication.navigation.BottomNavItem

@Composable
fun MessageJetpackScreen(
    onNavigateToMessage: () -> Unit,
    onNavigateToEditor: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    var newMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = newMessage,
                    onValueChange = { newMessage = it },
                    label = { Text("Enter message") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                Button(onClick = {
                    if (newMessage.isNotBlank()) {
                        MessagesRepository.add(Message(newMessage))
                        newMessage = ""
                    }
                }) {
                    Text("Add Message")
                }
            }
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(MessagesRepository.getAll()) { msg ->
                Text(msg.text, modifier = Modifier.padding(4.dp))
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