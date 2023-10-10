package com.sultanseidov.viewlistdemo2.presentation.ui.common.tab

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
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.*
import com.google.accompanist.pager.rememberPagerState
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.presentation.ui.common.movielist.MovieList
import com.sultanseidov.viewlistdemo2.presentation.ui.common.movielist.TvShowsList
import com.sultanseidov.viewlistdemo2.presentation.viewmodel.DiscoverViewModel
import com.sultanseidov.viewlistdemo2.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
@ExperimentalPagerApi
@Composable
fun TabScreen(genreGroups: List<GenresMovieModel>) {
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

@OptIn(ExperimentalPagingApi::class, ExperimentalCoilApi::class)
@ExperimentalPagerApi
@Composable
fun TabsContent(
    pagerState: PagerState,
    genreGroups: List<GenresMovieModel>,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    fun launch() {
        searchViewModel.fetchDiscoverMovies(genreGroups[pagerState.currentPage].id.toString())
        //searchViewModel.fetchDiscoverTvShows(genreGroups[pagerState.currentPage].id.toString())

    }

    launch()

    val movies = searchViewModel.discoverMoviesState.collectAsLazyPagingItems()
    //val tvShows = searchViewModel.discoverTvShowsState.collectAsLazyPagingItems()

    HorizontalPager(count = genreGroups.size, state = pagerState) { page ->
        Log.d(ContentValues.TAG, "TabsContent: $page")
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            MovieList(lazyMovieItems = movies)
            //TvShowsList(lazyMovieItems = tvShows)

        }

    }
}


