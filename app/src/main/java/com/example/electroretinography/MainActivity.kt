package com.example.electroretinography

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.electroretinography.ui.theme.ElectroRetinographyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElectroRetinographyTheme {
                // Create a NavController to manage navigation
                val navController = rememberNavController()

                // Set up NavHost
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            onNavigateToSignalTest = {
                                // Navigate to SignalTest screen when button is clicked
                                navController.navigate("signalTest")
                            }
                        )
                    }
                    composable("signalTest") {
                        SignalTestScreen() // Screen that appears after navigation
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ElectroRetinographyTheme {
        HomeScreen(onNavigateToSignalTest = {})
    }
}
