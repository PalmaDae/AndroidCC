package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
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

    val errorEmpty = stringResource(R.string.reg_error_empty)
    val errorDefault = stringResource(R.string.reg_error_default)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.reg_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                label = { Text(stringResource(R.string.reg_label_login)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.reg_label_name)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.reg_label_pass)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        if (login.isBlank() || password.isBlank() || name.isBlank()) {
                            snackbarHostState.showSnackbar(errorEmpty)
                            return@launch
                        }

                        try {
                            val userModel = UserDataModel(login, name, password)
                            ServiceLocator.getUserRepository().createNewUser(userModel)

                            UserDataRepository.saveSession(login)
                            navController.navigate(GameList) {
                                popUpTo(Registration) { inclusive = true }
                            }
                        } catch (e: Exception) {
                            snackbarHostState.showSnackbar(e.message ?: errorDefault)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.reg_btn_register))
            }

            TextButton(
                onClick = { navController.navigate(Login) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.reg_btn_signin))
            }
        }
    }
}