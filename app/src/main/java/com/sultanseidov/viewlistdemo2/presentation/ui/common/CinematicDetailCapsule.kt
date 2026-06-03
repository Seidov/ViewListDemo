package com.sultanseidov.viewlistdemo2.presentation.ui.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.util.Constants.IMAGE_BASE_URL

@Composable
fun CinematicDetailCapsule(
    movie: MovieModel,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Box(
        modifier = modifier
            .width(300.dp)
            .clip(RoundedCornerShape(28.dp))
            .glassmorphic(shape = RoundedCornerShape(28.dp))
    ) {
        Column {
            // Top Section: Backdrop masked into capsule
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            ) {
                AsyncImage(
                    model = IMAGE_BASE_URL + (movie.backdrop_path ?: movie.poster_path),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Gradient overlay for text readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                            )
                        )
                )
            }

            // Body Content
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = movie.title ?: "Unknown",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Tech Badges Row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TechBadge(text = "★ ${movie.vote_average ?: 0.0}")
                    TechBadge(text = movie.release_date?.take(4) ?: "N/A")
                    TechBadge(text = "HD")
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Neural Match Indicator
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = pulseAlpha), androidx.compose.foundation.shape.CircleShape)
                    )
                    Text(
                        text = "✦ ANALYZING SYNC STRENGTH...",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = pulseAlpha),
                            letterSpacing = 1.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun TechBadge(text: String) {
    Box(
        modifier = Modifier
            .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
            .border(0.5.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.8f),
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
            )
        )
    }
}
