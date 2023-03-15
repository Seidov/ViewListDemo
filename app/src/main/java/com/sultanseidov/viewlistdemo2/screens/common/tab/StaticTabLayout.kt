package com.sultanseidov.viewlistdemo2.screens.common.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
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
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.sultanseidov.viewlistdemo2.data.entity.movie.PopularMoviesModel
import com.sultanseidov.viewlistdemo2.screens.common.movielist.MovieList
import com.sultanseidov.viewlistdemo2.viewmodel.ViewListViewModel
import kotlinx.coroutines.launch

enum class StaticPagerScreenPage {
    Summary,
    Info,
    Details,
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
            StaticPagerScreenPage.Summary,
            StaticPagerScreenPage.Info,
            StaticPagerScreenPage.Details,
        )
    }



    var movies1 = viewListViewModel.popularMoviesState.collectAsLazyPagingItems()
    var movies2 = viewListViewModel.popularMoviesState2.collectAsLazyPagingItems()
    var movies3 = viewListViewModel.popularMoviesState3.collectAsLazyPagingItems()



    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
        ) {
            pages.forEachIndexed { index, page ->
                Tab(
                    text = { Text(page.name) },
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.scrollToPage(index) } },
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

                    StaticPagerScreenPage.Summary -> {

                        SummaryContent(movies1)
                    }
                    StaticPagerScreenPage.Info -> {

                        InformationContent(movies2)
                    }
                    StaticPagerScreenPage.Details -> {

                        DetailsContent(movies3)
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryContent(
    lazyMovieItems: LazyPagingItems<PopularMoviesModel>
) {
    MovieList(lazyMovieItems = lazyMovieItems)
}

@Composable
private fun InformationContent(
    lazyMovieItems: LazyPagingItems<PopularMoviesModel>
) {
    MovieList(lazyMovieItems = lazyMovieItems)
}

@Composable
private fun DetailsContent(
    lazyMovieItems: LazyPagingItems<PopularMoviesModel>
) {
    MovieList(lazyMovieItems = lazyMovieItems)
}