package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.UserDataRepository
import com.example.myapplication.di.ServiceLocator
import com.example.myapplication.navigation.GameList
import com.example.myapplication.navigation.Login
import kotlinx.coroutines.launch

@Composable
fun RestoreAccountScreen(login: String, navController: NavController) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.restore_title),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.restore_description),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                scope.launch {
                    ServiceLocator.getUserRepository().restoreAccount(login)
                    UserDataRepository.saveSession(login)
                    navController.navigate(GameList) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.restore_btn_restore))
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = {
                scope.launch {
                    ServiceLocator.getUserRepository().permanentDelete(login)
                    navController.navigate(Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
        ) {
            Text(stringResource(R.string.restore_btn_permanent_delete))
        }
    }
}