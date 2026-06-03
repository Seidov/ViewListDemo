package com.sultanseidov.viewlistdemo2.presentation.ui.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

/**
 * A reusable Neon Shimmer effect that creates a premium cyberpunk loading feel.
 * Uses the baseline palette: CyberBackground, Neon Violet (Primary), and Neon Cyan (Secondary).
 */
fun Modifier.neonShimmer(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "NeonShimmerTransition")
    
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ShimmerOffsetX"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFF070B14),
                MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
                Color(0xFF070B14)
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}
