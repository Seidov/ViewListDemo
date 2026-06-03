package com.sultanseidov.viewlistdemo2.presentation.screens.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sultanseidov.viewlistdemo2.presentation.ui.common.glassmorphic

@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState { 3 }

    LaunchedEffect(uiState.isOnboardingComplete) {
        if (uiState.isOnboardingComplete) {
            onOnboardingComplete()
        }
    }

    LaunchedEffect(uiState.currentStep) {
        if (pagerState.currentPage != uiState.currentStep) {
            pagerState.animateScrollToPage(uiState.currentStep)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false // Forced navigation via buttons
        ) { page ->
            when (page) {
                0 -> WelcomePage()
                1 -> PhilosophyPage()
                2 -> TasteSelectionPage(
                    selectedGenreIds = uiState.selectedGenreIds,
                    onGenreToggle = { viewModel.onEvent(OnboardingEvent.ToggleGenre(it)) }
                )
            }
        }

        // Navigation Controls
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Step Indicator (Neon Dots)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                repeat(3) { index ->
                    val isActive = uiState.currentStep == index
                    val dotColor by animateColorAsState(
                        if (isActive) MaterialTheme.colorScheme.primary else Color.White.copy(alpha = 0.2f),
                        label = "dotColor"
                    )
                    Box(
                        modifier = Modifier
                            .size(if (isActive) 10.dp else 8.dp)
                            .clip(CircleShape)
                            .background(dotColor)
                            .then(
                                if (isActive) Modifier.border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                                else Modifier
                            )
                    )
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                if (uiState.currentStep > 0) {
                    TextButton(
                        onClick = { viewModel.onEvent(OnboardingEvent.PrevStep) },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Text("BACK", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                    }
                }

                if (uiState.currentStep < 2) {
                    Button(
                        onClick = { viewModel.onEvent(OnboardingEvent.NextStep) },
                        modifier = Modifier.align(Alignment.CenterEnd),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("NEXT")
                    }
                } else {
                    Button(
                        onClick = { viewModel.onEvent(OnboardingEvent.CompleteOnboarding) },
                        modifier = Modifier.align(Alignment.CenterEnd),
                        enabled = !uiState.isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.Black,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("INITIALIZE SYSTEM", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "VIEWLIST",
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Black,
                letterSpacing = 8.sp,
                color = MaterialTheme.colorScheme.primary
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "BEHAVIORAL CINEMATIC IDENTITY",
            style = MaterialTheme.typography.labelLarge.copy(
                letterSpacing = 2.sp,
                color = MaterialTheme.colorScheme.secondary
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Step into the future of film discovery. Your taste is no longer a static list—it's a living neural footprint.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun PhilosophyPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "THE PIN ENGINE",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .glassmorphic()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Our neural engine monitors and clusters your viewing footprints dynamically. No more manual sorting. Pure cinematic flow.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TasteSelectionPage(
    selectedGenreIds: Set<Int>,
    onGenreToggle: (Int) -> Unit
) {
    val genres = remember {
        listOf(
            GenreItem(28, "Action"),
            GenreItem(878, "Sci-Fi"),
            GenreItem(53, "Thriller"),
            GenreItem(18, "Drama"),
            GenreItem(27, "Horror"),
            GenreItem(16, "Anime"),
            GenreItem(9648, "Mystery"),
            GenreItem(80, "Crime")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "NEURAL INITIALIZATION",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Select your core cinematic pillars",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(32.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(genres) { genre ->
                GenreToken(
                    genre = genre,
                    isSelected = selectedGenreIds.contains(genre.id),
                    onToggle = { onGenreToggle(genre.id) }
                )
            }
        }
    }
}

@Composable
fun GenreToken(
    genre: GenreItem,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.secondary else Color.White.copy(alpha = 0.1f),
        label = "borderColor"
    )
    val borderOpacity by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.5f,
        label = "borderOpacity"
    )

    Box(
        modifier = Modifier
            .height(100.dp)
            .clip(MaterialTheme.shapes.medium)
            .glassmorphic()
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        borderColor.copy(alpha = borderOpacity),
                        borderColor.copy(alpha = borderOpacity * 0.2f)
                    )
                ),
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onToggle() }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = genre.name.uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
        )
    }
}

data class GenreItem(val id: Int, val name: String)
