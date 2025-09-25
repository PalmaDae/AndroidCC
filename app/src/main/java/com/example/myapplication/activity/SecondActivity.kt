package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textFromMain = intent.getStringExtra("text_key") ?: "0_0"

        setContent {
            mainContent(textFromMain)
        }
    }


    @Composable
    private fun textView(text: String) {
        Text(text = text)
    }

    @Composable
    private fun goToMain() {
        val context = LocalContext.current
        OutlinedButton(
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text("Go to MainScreen")
        }
    }

    @Composable
    private fun mainContent(text: String) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            textView(text)
            Spacer(modifier = Modifier.height(16.dp))
            goToThird(text)
            Spacer(modifier = Modifier.height(16.dp))
            goToMain()
        }
    }

    @Composable
    private fun goToThird(text: String) {
        val context = LocalContext.current
        OutlinedButton(
            onClick = {
                val intent = Intent(context, ThirdActivity::class.java)
                if (text != "0_0")
                    intent.putExtra("text_key",text)
                context.startActivity(intent)
            }
        ) {
            Text("SUPER MEGA SECRET BUTTON")
        }
    }
}