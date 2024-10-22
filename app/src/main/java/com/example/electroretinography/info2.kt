package com.example.electroretinography

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color

@Composable
fun Info2() {
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
    }
}

@Preview(showBackground = true)
@Composable
fun Info2Preview() {
    Info2()
}
