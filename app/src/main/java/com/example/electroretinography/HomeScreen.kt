package com.example.electroretinography

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.ExperimentalPagerApi
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.clickable

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToSignalTest: () -> Unit // Add this parameter for navigation
) {
    val pagerState = rememberPagerState()
    val pages = listOf("Info 1", "Info 2")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFBBDEFB), Color(0xFFE1F5FE))
                )
            )
    ) {
        // HorizontalPager for content pages
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> Info1()  // Info1 page
                1 -> Info2(onNavigateToSignalTest = onNavigateToSignalTest) // Info2 with navigation
            }
        }

        // Banner to trigger navigation to Signal Test screen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp) // Set the height of the banner
                .background(Color(0xFF81D4FA)) // Light blue background for visibility
                .align(Alignment.BottomCenter) // Position it at the bottom
                .clickable { onNavigateToSignalTest() } // When clicked, navigate
        ) {
            Text(
                text = "Test Your Signals",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onNavigateToSignalTest = {})
}
