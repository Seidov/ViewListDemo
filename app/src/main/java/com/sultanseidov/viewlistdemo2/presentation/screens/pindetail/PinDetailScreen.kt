package com.sultanseidov.viewlistdemo2.presentation.screens.pindetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sultanseidov.viewlistdemo2.presentation.ui.common.ShimmerLoadingStaggeredGrid
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sultanseidov.viewlistdemo2.data.local.entity.WatchedMovieEntity
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.presentation.LocalMoviePreview
import com.sultanseidov.viewlistdemo2.presentation.ui.common.glassmorphic
import com.sultanseidov.viewlistdemo2.util.Constants.IMAGE_BASE_URL

private val CyberBackground = Color(0xFF070B14)
private val NeonViolet = Color(0xFFBB86FC)
private val NeonCyan = Color(0xFF03DAC6)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PinDetailScreen(
    pinId: Long,
    onBackClick: () -> Unit,
    viewModel: PinDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(pinId) {
        viewModel.onEvent(PinDetailEvent.LoadPinDetails(pinId))
    }

    Scaffold(
        topBar = {
            PinDetailHeader(
                title = uiState.pinTitle,
                movieCount = uiState.movies.size,
                onBackClick = onBackClick
            )
        },
        containerColor = CyberBackground
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.isLoading) {
                ShimmerLoadingStaggeredGrid()
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalItemSpacing = 12.dp
                ) {
                    items(
                        items = uiState.movies,
                        key = { it.movieId }
                    ) { movie ->
                        MovieFootprintCard(movie = movie)
                    }
                    
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun PinDetailHeader(
    title: String,
    movieCount: Int,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CyberBackground)
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        TextButton(
            onClick = onBackClick,
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "← BACK",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = NeonCyan,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "✦ ${title.uppercase()}",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Black,
                color = NeonViolet,
                letterSpacing = 1.sp
            )
        )
        
        Text(
            text = "[ SYNCED FOOTPRINTS: $movieCount ]",
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = FontFamily.Monospace,
                color = NeonCyan.copy(alpha = 0.7f),
                letterSpacing = 1.sp
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
private fun MovieFootprintCard(movie: WatchedMovieEntity) {
    val previewState = LocalMoviePreview.current
    val haptic = LocalHapticFeedback.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .glassmorphic(shape = RoundedCornerShape(12.dp))
            .combinedClickable(
                onClick = { /* Detail maybe? */ },
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    previewState.show(movie.toMovieModel())
                }
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column {
            Box(modifier = Modifier.aspectRatio(0.7f)) {
                AsyncImage(
                    model = IMAGE_BASE_URL + movie.posterPath,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                                startY = 100f
                            )
                        )
                )
            }
            
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    movie.keywords.take(3).forEach { keyword ->
                        CyberBadge(text = "#$keyword")
                    }
                }
            }
        }
    }
}

@Composable
private fun CyberBadge(text: String) {
    Box(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
            .border(0.5.dp, NeonCyan.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Text(
            text = text.lowercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 8.sp,
                color = NeonCyan,
                fontFamily = FontFamily.Monospace
            )
        )
    }
}

// Extension to map WatchedMovieEntity back to MovieModel for preview
private fun WatchedMovieEntity.toMovieModel(): MovieModel {
    return MovieModel(
        id = movieId.toInt(),
        title = title,
        poster_path = posterPath,
        overview = "", 
        release_date = null,
        vote_average = 0.0,
        genre_ids = emptyList(),
        adult = false,
        backdrop_path = null,
        original_language = null,
        original_title = title,
        popularity = 0.0,
        video = false,
        vote_count = 0
    )
}
