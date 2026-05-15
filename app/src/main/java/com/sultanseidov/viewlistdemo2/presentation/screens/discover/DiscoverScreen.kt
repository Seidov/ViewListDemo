package com.sultanseidov.viewlistdemo2.presentation.screens.discover

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.sultanseidov.viewlistdemo2.presentation.ui.common.movielist.MovieList
import com.sultanseidov.viewlistdemo2.presentation.ui.common.movielist.TvShowsList
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun DiscoverScreen(
    navController: NavHostController,
    discoverViewModel: NewDiscoverViewModel = hiltViewModel()
) {
    val movies = discoverViewModel.moviesState.collectAsLazyPagingItems()
    val tvShows = discoverViewModel.tvShowsState.collectAsLazyPagingItems()

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val pages = remember {
        listOf("Movies", "TV Shows")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
        ) {
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.scrollToPage(index) } },
                )
            }
        }
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            Box(modifier = Modifier.fillMaxSize()) {
                when (index) {
                    0 -> MovieList(lazyMovieItems = movies)
                    1 -> TvShowsList(lazyMovieItems = tvShows)
                }
            }
        }
    }
}
