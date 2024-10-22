package com.example.electroretinography

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.electroretinography.ui.theme.ElectroretinographyTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElectroretinographyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize().statusBarsPadding(),
                    containerColor = Color.Transparent
                ) { innerPadding: PaddingValues ->
                    // Pass the innerPadding to HomeScreen as a Modifier
                    HomeScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}
