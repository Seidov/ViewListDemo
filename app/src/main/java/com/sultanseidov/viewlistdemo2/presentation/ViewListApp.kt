@file:JvmName("ViewListAppKt")

package com.sultanseidov.viewlistdemo2.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.presentation.navigation.NavGraph
import com.sultanseidov.viewlistdemo2.presentation.navigation.Tabs
import com.sultanseidov.viewlistdemo2.presentation.ui.common.CinematicDetailCapsule
import com.sultanseidov.viewlistdemo2.presentation.ui.common.glassmorphism
import com.sultanseidov.viewlistdemo2.presentation.ui.theme.ViewListTheme
import java.util.*

// Global composition local to manage movie preview state
val LocalMoviePreview = compositionLocalOf { MoviePreviewState() }

class MoviePreviewState {
    var activeMovie by mutableStateOf<MovieModel?>(null)
    
    fun show(movie: MovieModel) {
        activeMovie = movie
    }
    
    fun dismiss() {
        activeMovie = null
    }
}

@Composable
fun ViewListApp(
    showOnboardingInitially: Boolean,
    finishActivity: () -> Unit
) {
    val previewState = remember { MoviePreviewState() }
    
    CompositionLocalProvider(LocalMoviePreview provides previewState) {
        ViewListTheme {
            val tabs = remember { Tabs.entries.toTypedArray() }
            val navController = rememberNavController()
            
            val isPreviewActive = previewState.activeMovie != null
            val blurRadius by animateDpAsState(
                targetValue = if (isPreviewActive) 16.dp else 0.dp,
                label = "blurAnimation"
            )

            Box(modifier = Modifier.fillMaxSize()) {
                Scaffold(
                    modifier = Modifier.blur(blurRadius),
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = { ViewListBottomBar(navController = navController, tabs) }
                ) { innerPaddingModifier ->
                    NavGraph(
                        finishActivity = finishActivity,
                        navController = navController,
                        modifier = Modifier.padding(innerPaddingModifier),
                        showOnboardingInitially = showOnboardingInitially
                    )
                }

                // Global Preview Overlay
                if (isPreviewActive) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { previewState.dismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        previewState.activeMovie?.let { movie ->
                            CinematicDetailCapsule(movie = movie)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ViewListBottomBar(navController: NavController, tabs: Array<Tabs>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: Tabs.DISCOVER.route

    val routes = remember { Tabs.entries.map { it.route } }
    if (currentRoute in routes) {
        val dockShape = RoundedCornerShape(28.dp)
        
        // Floating Translucent Glass Dock
        Surface(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .navigationBarsPadding()
                .glassmorphism(shape = dockShape),
            color = Color.Transparent,
            shape = dockShape
        ) {
            NavigationBar(
                modifier = Modifier.height(64.dp),
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
                tonalElevation = 0.dp
            ) {
                tabs.forEach { tab ->
                    val isSelected = currentRoute == tab.route
                    NavigationBarItem(
                        icon = { Icon(painterResource(tab.icon), contentDescription = null) },
                        label = { 
                            Text(
                                text = stringResource(tab.title).uppercase(Locale.getDefault()), 
                                style = MaterialTheme.typography.labelSmall
                            ) 
                        },
                        selected = isSelected,
                        onClick = {
                            if (tab.route != currentRoute) {
                                navController.navigate(tab.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                        )
                    )
                }
            }
        }
    }
}
