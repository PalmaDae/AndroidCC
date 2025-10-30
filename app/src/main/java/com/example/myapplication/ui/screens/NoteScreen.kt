package com.example.myapplication.ui.screens

import android.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.model.Note
import com.example.myapplication.navigation.Routes
import com.example.myapplication.viewmodel.SharedViewModel

@Composable
fun NoteScreen(
    navController: NavHostController,
    viewModel: SharedViewModel
) {
    val notes by viewModel.notes
    val email by viewModel.userEmail

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "$email", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            if (notes.isEmpty()) {
                item {
                    Text("Заметок нет", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else  {
                items(notes) { note ->
                    Card(
                        modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp))
                    {
                        Column(modifier = Modifier
                            .padding(12.dp)
                        ) {
                            Text(text = note.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            if (note.content.isNotEmpty()) {
                                Text(text = note.content,
                                    style = MaterialTheme.typography.bodyMedium
                                    )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { navController.navigate(Routes.ADD_NOTE) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Добавить заметку")
            }
        }
    }
}