package com.example.electroretinography

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Info2(onNavigateToSignalTest: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFE0F7FA)), // Light teal background
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Common Uses of Electroretinography:",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "- Assessing retinal diseases like retinitis pigmentosa.\n" +
                    "- Monitoring disease progression.\n" +
                    "- Evaluating the function of the retina after treatment.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToSignalTest) {
            Text("Test Your Signals")
        }
    }
}
@Composable
fun SignalTestScreen(onFileSelected: (Uri) -> Unit) {
    val context = LocalContext.current

    // Launcher for file picker
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { onFileSelected(it) }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF0F4C3)), // Light yellow background for distinction
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Test Your Signals",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Here you can test your signals as a CSV file format generated from the electroretinogram.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Button to open file picker with the correct MIME type for CSV
        Button(onClick = { filePickerLauncher.launch("text/*") }) {
            Text("Upload CSV File")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignalTestScreenPreview() {
    SignalTestScreen(onFileSelected = {})
}

@Preview(showBackground = true)
@Composable
fun Info2Preview() {
    Info2(onNavigateToSignalTest = {})
}
