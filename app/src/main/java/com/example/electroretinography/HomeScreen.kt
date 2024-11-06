package com.example.electroretinography

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // <-- Add this import for 'sp' units
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.ExperimentalPagerApi
import androidx.compose.ui.graphics.Brush

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
                0 -> Info1()
                1 -> Info2(onNavigateToSignalTest = onNavigateToSignalTest) // Pass the navigation function here
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onNavigateToSignalTest = {})
}
