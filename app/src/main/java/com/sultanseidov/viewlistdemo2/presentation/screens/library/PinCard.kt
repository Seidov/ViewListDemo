package com.sultanseidov.viewlistdemo2.presentation.screens.library

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sultanseidov.viewlistdemo2.data.local.entity.PinEntity
import com.sultanseidov.viewlistdemo2.presentation.ui.common.glassmorphic

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PinCard(
    pin: PinEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isSystemCore = pin.semanticType == "system_core"
    val accentColor = if (isSystemCore) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    val haptic = LocalHapticFeedback.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .glassmorphic(shape = RoundedCornerShape(16.dp))
            .then(
                if (pin.clusterStrength >= 1.0) {
                    Modifier.border(
                        width = 1.dp,
                        color = accentColor.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(16.dp)
                    )
                } else Modifier
            )
            .combinedClickable(
                onClick = onClick,
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    // TODO: Preview pin summary or first movie
                }
            )
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Row: Title
            Text(
                text = pin.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    letterSpacing = 0.5.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Center Info: Digital Badge
            Box(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "[ INDEXED: ${pin.movieCount} MOVIES ]",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Footprint: Genres
            Text(
                text = pin.dominantGenres.joinToString(" • ").uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.sp,
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Visual Cue: Stable Cluster indicator
        if (pin.clusterStrength >= 1.0) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .align(Alignment.TopEnd)
                    .background(accentColor, CircleShape)
            )
        }
    }
}

val CircleShape = RoundedCornerShape(50)
