package com.sultanseidov.viewlistdemo2.presentation.ui.common.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import com.sultanseidov.viewlistdemo2.presentation.ui.common.movielist.MovieList
import com.sultanseidov.viewlistdemo2.presentation.ui.common.movielist.TvShowsList
import com.sultanseidov.viewlistdemo2.presentation.viewmodel.ViewListViewModel
import kotlinx.coroutines.launch

enum class StaticPagerScreenPage {
    ForYouMovies,
    ForYouTvShows,
}

@OptIn(ExperimentalPagerApi::class, ExperimentalPagingApi::class)
@Composable
fun StaticPagerScreen(
    viewListViewModel: ViewListViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val pages = remember {
        listOf(
            StaticPagerScreenPage.ForYouMovies,
            StaticPagerScreenPage.ForYouTvShows,
        )
    }

    //val movies = viewListViewModel.discoverMoviesState.collectAsLazyPagingItems()
    //val tvShows = viewListViewModel.discoverTvShowsState.collectAsLazyPagingItems()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                if (pagerState.currentPage < tabPositions.size) {
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
        ) {
            pages.forEachIndexed { index, page ->
                Tab(
                    text = { Text(page.name, style = MaterialTheme.typography.labelLarge) },
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.scrollToPage(index) } },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
        HorizontalPager(
            count = pages.size,
            state = pagerState,
        ) { index ->
            val page = pages[index]
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (page) {
                    StaticPagerScreenPage.ForYouMovies -> {
                        //ForYouMoviesContent(movies)
                    }
                    StaticPagerScreenPage.ForYouTvShows -> {
                        //ForYouTvShowsContent(tvShows)
                    }
                }
            }
        }
    }
}

@Composable
private fun ForYouMoviesContent(
    lazyMovieItems: LazyPagingItems<MovieModel>
) {
    MovieList(lazyMovieItems = lazyMovieItems)
}

@Composable
private fun ForYouTvShowsContent(
    lazyTvShowItems: LazyPagingItems<TvShowModel>
) {
    TvShowsList(lazyMovieItems = lazyTvShowItems)
}
