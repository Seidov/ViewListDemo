package com.sultanseidov.viewlistdemo2.presentation.ui.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.random.Random

private val NeonViolet = Color(0xFFBB86FC)
private val NeonCyan = Color(0xFF03DAC6)

/**
 * A tactical terminal glitch effect for text.
 * Periodically triggers chromatic aberration and horizontal offsets.
 */
@Composable
fun GlitchText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.White
) {
    val infiniteTransition = rememberInfiniteTransition(label = "GlitchInfiniteTransition")
    
    // Periodically trigger glitch cycles
    val glitchTrigger by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "GlitchTrigger"
    )

    // Derived state to determine if we are currently "glitching" (small window of the cycle)
    val isGlitching = glitchTrigger > 0.8f && glitchTrigger < 0.95f
    
    val offsetX = remember(isGlitching, glitchTrigger) {
        if (isGlitching) Random.nextInt(-4, 4).dp else 0.dp
    }
    
    val chromaticAlpha = remember(isGlitching, glitchTrigger) {
        if (isGlitching) Random.nextFloat() * 0.5f else 0f
    }

    Box(modifier = modifier) {
        // Cyan Layer (Left shift)
        if (isGlitching) {
            Text(
                text = text,
                style = style,
                color = NeonCyan,
                modifier = Modifier
                    .offset { IntOffset(x = -offsetX.roundToPx(), y = 0) }
                    .alpha(chromaticAlpha)
            )
        }

        // Violet Layer (Right shift)
        if (isGlitching) {
            Text(
                text = text,
                style = style,
                color = NeonViolet,
                modifier = Modifier
                    .offset { IntOffset(x = offsetX.roundToPx(), y = 0) }
                    .alpha(chromaticAlpha)
            )
        }

        // Main Layer
        Text(
            text = text,
            style = style,
            color = color,
            modifier = Modifier.offset {
                if (isGlitching && Random.nextFloat() > 0.7f) {
                    IntOffset(x = (offsetX / 2).roundToPx(), y = 0)
                } else IntOffset.Zero
            }
        )
    }
}
