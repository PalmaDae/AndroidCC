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
import com.example.myapplication.navigation.GameList
import com.example.myapplication.navigation.Login
import com.example.myapplication.navigation.Registration
import com.example.myapplication.navigation.RestoreAccount
import com.example.myapplication.utils.HashUtil
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val errorFields = stringResource(R.string.login_error_fields)
    val errorExpired = stringResource(R.string.login_error_expired)
    val errorPassword = stringResource(R.string.login_error_password)
    val errorNotFound = stringResource(R.string.login_error_not_found)

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
                text = stringResource(R.string.login_welcome),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                label = { Text(stringResource(R.string.login_label_user)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.login_label_pass)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        if (login.isBlank() || password.isBlank()) {
                            snackbarHostState.showSnackbar(errorFields)
                            return@launch
                        }

                        val user = ServiceLocator.getUserRepository().getUserByLogin(login)

                        if (user != null) {
                            if (HashUtil.checkPassword(password, user.password)) {
                                val userEntity = ServiceLocator.getDatabase().userDao().getUserByLogin(login)

                                if (userEntity?.deletionDate != null) {
                                    val diff = System.currentTimeMillis() - userEntity.deletionDate
                                    val days = diff / (1000 * 60 * 60 * 24)

                                    if (days >= 7) {
                                        ServiceLocator.getUserRepository().permanentDelete(login)
                                        snackbarHostState.showSnackbar(errorExpired)
                                    } else {
                                        navController.navigate(RestoreAccount(login))
                                    }
                                } else {
                                    UserDataRepository.saveSession(login)
                                    navController.navigate(GameList) {
                                        popUpTo(Login) { inclusive = true }
                                    }
                                }
                            } else {
                                snackbarHostState.showSnackbar(errorPassword)
                            }
                        } else {
                            snackbarHostState.showSnackbar(errorNotFound)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.login_btn_signin))
            }

            TextButton(onClick = { navController.navigate(Registration) }) {
                Text(stringResource(R.string.login_btn_signup))
            }
        }
    }
}