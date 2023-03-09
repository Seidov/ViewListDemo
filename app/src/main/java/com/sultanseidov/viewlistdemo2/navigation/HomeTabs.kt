package com.sultanseidov.viewlistdemo2.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
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
import com.sultanseidov.viewlistdemo2.screens.discover.DiscoverScreen
import com.sultanseidov.viewlistdemo2.screens.search.SearchScreen
import com.sultanseidov.viewlistdemo2.screens.viewlist.ViewListScreen

@OptIn(ExperimentalPagingApi::class, ExperimentalCoilApi::class)
fun NavGraphBuilder.home(
    onCourseSelected: (Long, NavBackStackEntry) -> Unit,
    onBoardingComplete: State<Boolean>,
    navController: NavHostController,
    modifier: Modifier = Modifier
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
    composable(Tabs.VIEWLIST.route) { from ->
        ViewListScreen(
            selectCourse = { id -> onCourseSelected(id, from) },
            modifier = modifier
        )
    }
    composable(Tabs.SEARCH.route) { from ->
        SearchScreen(
            selectCourse = { id -> onCourseSelected(id, from) },
            modifier = modifier)
    }
}

@Composable
fun DiscoverAppBar() {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.height(80.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )
        IconButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { /* todo */ }
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = stringResource(R.string.discover)
            )
        }
    }
}

enum class Tabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    DISCOVER(R.string.discover, R.drawable.ic_discover, Destinations.DISCOVER_ROUTE),
    SEARCH(R.string.search, R.drawable.ic_search, Destinations.SEARCH_ROUTE),
    VIEWLIST(R.string.viewlist, R.drawable.ic_viewlist, Destinations.VIEWLIST_ROUTE)
}

object Destinations {
    const val DISCOVER_ROUTE = "home/discover"
    const val SEARCH_ROUTE = "home/search"
    const val VIEWLIST_ROUTE = "home/viewlist"
}