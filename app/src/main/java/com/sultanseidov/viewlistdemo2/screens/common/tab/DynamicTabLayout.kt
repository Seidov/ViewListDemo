package com.sultanseidov.viewlistdemo2.screens.common.tab

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.*
import com.google.accompanist.pager.rememberPagerState
import com.sultanseidov.viewlistdemo2.data.entity.GenreModel
import com.sultanseidov.viewlistdemo2.screens.common.movielist.MovieList
import com.sultanseidov.viewlistdemo2.viewmodel.DiscoverViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
@ExperimentalPagerApi
@Composable
fun TabScreen(genreGroups: List<GenreModel>) {
    if (genreGroups.isNotEmpty()) {
        val pagerState = rememberPagerState()

        //val pagerState = rememberPagerState(pageCount = genreGroups.size)
        Column {
            Tabs(pagerState = pagerState, tabTitles = genreGroups.map { it.name })
            TabsContent(pagerState = pagerState, genreGroups = genreGroups)
        }
    } else {
        Text("No Data")
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState, tabTitles: List<String>) {
    val scope = rememberCoroutineScope()

    ScrollableTabRow(selectedTabIndex = pagerState.currentPage) {
        tabTitles.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        tabTitles[index],
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalPagingApi::class)
@ExperimentalPagerApi
@Composable
fun TabsContent(
    pagerState: PagerState,
    genreGroups: List<GenreModel>,
    discoverViewModel: DiscoverViewModel = hiltViewModel()

) {
    fun launch() {
        discoverViewModel.fetchPopularMovies(genreGroups[pagerState.currentPage].id.toString())
    }

    launch()

    val movies = discoverViewModel.popularMoviesState.collectAsLazyPagingItems()

    HorizontalPager(count = genreGroups.size, state = pagerState) { page ->
        Log.d(ContentValues.TAG, "TabsContent: $page")
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            MovieList(lazyMovieItems = movies)

        }

    }
}


