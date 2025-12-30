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
import com.example.myapplication.navigation.GameList
import com.example.myapplication.navigation.Registration
import com.example.myapplication.utils.HashUtil
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Welcome Back", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                label = { Text("Login") },
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
                    scope.launch {
                        val user = ServiceLocator.getUserRepository().getUserByLogin(login)

                        if (user != null && HashUtil.checkPassword(password, user.password)) {
                            UserDataRepository.saveSession(login)
                            navController.navigate(GameList) {
                                popUpTo(com.example.myapplication.navigation.Login) { inclusive = true }
                            }
                        } else {
                            snackbarHostState.showSnackbar("Invalid login or password")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign In")
            }

            TextButton(onClick = { navController.navigate(Registration) }) {
                Text("Don't have an account? Sign Up")
            }
        }
    }
}