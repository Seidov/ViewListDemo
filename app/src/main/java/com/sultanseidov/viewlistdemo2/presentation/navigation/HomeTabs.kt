package com.sultanseidov.viewlistdemo2.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.sultanseidov.viewlistdemo2.R
import com.sultanseidov.viewlistdemo2.presentation.screens.discover.DiscoverScreen
import com.sultanseidov.viewlistdemo2.presentation.screens.library.LibraryScreen
import com.sultanseidov.viewlistdemo2.presentation.screens.search.SearchScreen
import androidx.compose.ui.tooling.preview.Preview
import com.sultanseidov.viewlistdemo2.presentation.ui.theme.ViewListTheme


@OptIn(ExperimentalPagingApi::class, ExperimentalCoilApi::class)
fun NavGraphBuilder.home(
    onCourseSelected: (Long, NavBackStackEntry) -> Unit,
    onPinSelected: (Long, NavBackStackEntry) -> Unit,
    onBoardingComplete: State<Boolean>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    composable(Tabs.DISCOVER.route) { from ->

        // Show onboarding instead if not shown yet.
        LaunchedEffect(onBoardingComplete) {
            if (!onBoardingComplete.value) {
                navController.navigate(MainDestinations.ONBOARDING_ROUTE)
            }
        }
        if (onBoardingComplete.value) { // Avoid glitch when showing onboarding
            DiscoverScreen(navController = navController)
        }
    }
    composable(Tabs.LIBRARY.route) { from ->
        LibraryScreen(onPinClick = { pinId -> onPinSelected(pinId, from) })
    }
    composable(Tabs.SEARCH.route) { from ->
        SearchScreen(
            onMovieClick = { id -> onCourseSelected(id, from) },
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverAppBar() {
    CenterAlignedTopAppBar(
        modifier = Modifier.height(80.dp),
        title = {
            Image(
                modifier = Modifier
                    .padding(16.dp),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null
            )
        },
        actions = {
            IconButton(
                onClick = { /* todo */ }
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.discover)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

enum class Tabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    DISCOVER(R.string.discover, R.drawable.ic_discover, Destinations.DISCOVER_ROUTE),
    SEARCH(R.string.search, R.drawable.ic_search, Destinations.SEARCH_ROUTE),
    LIBRARY(R.string.library, R.drawable.ic_viewlist, Destinations.LIBRARY_ROUTE)
}

object Destinations {
    const val DISCOVER_ROUTE = "home/discover"
    const val SEARCH_ROUTE = "home/search"
    const val LIBRARY_ROUTE = "home/library"
}

@Preview(showBackground = true)
@Composable
fun DiscoverAppBarPreview() {
    ViewListTheme {
        DiscoverAppBar()
    }
}

