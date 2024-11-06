package com.example.electroretinography

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color

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
fun SignalTestScreen() {
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
        // Placeholder for file upload input
        Button(onClick = { /* Implement file upload logic */ }) {
            Text("Upload CSV File")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Info2Preview() {
    Info2(onNavigateToSignalTest = {})
}

@Preview(showBackground = true)
@Composable
fun SignalTestScreenPreview() {
    SignalTestScreen()
}
