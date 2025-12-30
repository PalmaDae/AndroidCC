package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.UserDataRepository
import com.example.myapplication.di.ServiceLocator
import com.example.myapplication.model.UserDataModel
import com.example.myapplication.navigation.GameList
import com.example.myapplication.navigation.Login
import com.example.myapplication.navigation.Registration
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(navController: NavController) {
    var login by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                label = { Text("Login") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (login.isBlank() || name.isBlank() || password.isBlank()) {
                        scope.launch { snackbarHostState.showSnackbar("Please fill all fields") }
                        return@Button
                    }

                    scope.launch {
                        try {
                            val newUser = UserDataModel(login, name, password)
                            val newId = ServiceLocator.getUserRepository().createNewUser(newUser)

                            ServiceLocator.getUserRepository().createNewUser(newUser)

                            UserDataRepository.saveSession(login)

                            navController.navigate(GameList) {
                                popUpTo(Registration) { inclusive = true }
                            }
                        } catch (e: Exception) {
                            snackbarHostState.showSnackbar("Error: ${e.message}")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

            TextButton(
                onClick = { navController.navigate(Login) }
            ) {
                Text("Already have an account? Sign In")
            }
        }
    }
}