package com.sultanseidov.viewlistdemo2.presentation.screens.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sultanseidov.viewlistdemo2.presentation.ui.common.GlitchText
import com.sultanseidov.viewlistdemo2.presentation.ui.common.ShimmerLoadingGrid
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LibraryScreen(
    onPinClick: (Long) -> Unit,
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        if (uiState.pins.isEmpty() && !uiState.isLoading) {
            EmptyLibraryView()
        } else if (uiState.isLoading) {
            ShimmerLoadingGrid()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    LibraryHeader(activeNodes = uiState.pins.size)
                }

                items(
                    items = uiState.pins,
                    key = { it.pinId }
                ) { pin ->
                    PinCard(
                        pin = pin,
                        onClick = { onPinClick(pin.pinId) }
                    )
                }
                
                // Bottom spacing for Navigation Dock
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun LibraryHeader(activeNodes: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp, top = 16.dp)
    ) {
        Text(
            text = "NEURAL ARCHIVE",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                color = Color.White
            )
        )
        Text(
            text = "SYSTEM STATUS: $activeNodes ACTIVE NODES",
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.secondary,
                letterSpacing = 1.sp
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun EmptyLibraryView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GlitchText(
            text = "NO ACTIVE NEURAL CLUSTERS FOUND",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Begin your journey by selecting 'LIST' on cinematic footprints to initialize your neural engine.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}
