package com.example.electroretinography.ui.theme

import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color // Import Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable

// Define the theme
@Composable
fun ElectroRetinographyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6200EE), // Primary color
            secondary = Color(0xFF03DAC5), // Secondary color
            background = Color(0xFFF5F5F5), // Background color
            surface = Color(0xFFFFFFFF), // Surface color
            onPrimary = Color.White, // On primary text color
            onSecondary = Color.Black, // On secondary text color
            onBackground = Color.Black, // On background text color
            onSurface = Color.Black // On surface text color
        ),
        typography = Typography( // Use Typography from Material3
            displayLarge = TextStyle(fontSize = 30.sp), // Example text style
            headlineMedium = TextStyle(fontSize = 24.sp), // Example text style
            bodyLarge = TextStyle(fontSize = 16.sp), // Example text style
            bodyMedium = TextStyle(fontSize = 14.sp) // Example text style
        ),
        content = content // Pass the content composables here
    )
}
