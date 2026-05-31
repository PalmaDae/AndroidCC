package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.AppNavGraph
import com.example.myapplication.ui.component.BottomAppBar
import com.example.myapplication.ui.component.TopAppBar
import com.example.myapplication.ui.screen.SearchApp
import com.example.myapplication.ui.theme.DonorCity
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("FCM", "Token: ${task.result}")
            }
        }

        setContent {
            DonorCity {
                Scaffold(
                    topBar = { TopAppBar() },
                    bottomBar = { BottomAppBar() }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        val navController = rememberNavController()
                        AppNavGraph(navController)
                    }
                }
            }
        }
    }
}