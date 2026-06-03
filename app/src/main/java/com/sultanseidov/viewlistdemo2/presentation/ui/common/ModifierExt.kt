package com.sultanseidov.viewlistdemo2.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Shape
import com.sultanseidov.viewlistdemo2.presentation.ui.theme.Shapes

/**
 * Reusable modifier for "Frosted Glass Surface" using M3 design tokens.
 * Apply a background using surface color and a neon border/edge lighting.
 */
fun Modifier.glassmorphism(shape: Shape = Shapes.medium) = composed {
    this
        .background(
            color = MaterialTheme.colorScheme.surface,
            shape = shape
        )
        .border(
            width = 1.dp,
            brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    Color.White.copy(alpha = 0.1f)
                )
            ),
            shape = shape
        )
        // Placeholder for custom RenderEffect/Blur shaders for Android 12+ compatibility
        // .then(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) Modifier.blur(10.dp) else Modifier)
}

fun Modifier.glassmorphic(shape: Shape = Shapes.medium) = glassmorphism(shape)
