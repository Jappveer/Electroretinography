package com.example.electroretinography

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Info1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFE1F5FE)), // Light blue background
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.electroretinography_image),
            contentDescription = "Electroretinography",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Electroretinography (ERG) is a diagnostic test that measures the electrical activity of the retina in response to light. It helps in diagnosing various retinal conditions.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Info1Preview() {
    Info1()
}
