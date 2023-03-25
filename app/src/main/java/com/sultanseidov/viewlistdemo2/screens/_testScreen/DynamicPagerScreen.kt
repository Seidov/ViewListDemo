package com.sultanseidov.viewlistdemo2.screens._testScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.sultanseidov.viewlistdemo2.data.entity.genre.GenreModel
import com.sultanseidov.viewlistdemo2.data.entity.movie.MovieModel
import com.sultanseidov.viewlistdemo2.data.entity.tvshow.TvShowModel
import com.sultanseidov.viewlistdemo2.screens.common.movielist.MovieList
import com.sultanseidov.viewlistdemo2.screens.common.movielist.TvShowsList
import com.sultanseidov.viewlistdemo2.viewmodel.TestViewModel
import kotlinx.coroutines.launch

object TestTagsDynamicPagerScreen {

    private const val pager = "dynamic-pager"
    const val tabRow = "dynamic-pager-tab-row"

    fun getPageTag(index: Int) = "$pager-$index"
}

@OptIn(ExperimentalPagerApi::class, ExperimentalPagingApi::class, ExperimentalCoilApi::class)
@Composable
fun DynamicPagerScreen(
    tabGenresList: List<GenreModel>,
    testViewModel: TestViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    val genresList = tabGenresList
    var pageCount by remember { mutableStateOf(genresList.size) }
    val pages = remember(pageCount) { List(pageCount) { genresList[it].name } }

    //val getPopularMovies = homeViewModel.getPopularMovies.collectAsLazyPagingItems()
    //val getAllGenres = homeViewModel.getAllGenres

    var list = mutableListOf<LazyPagingItems<MovieModel>>()
    tabGenresList.forEach {
        list.add(testViewModel.getPopularMoviesByGenre2(it.id.toString()).collectAsLazyPagingItems())
    }


    Column {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
            modifier = Modifier.testTag(TestTagsDynamicPagerScreen.tabRow),
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
        ) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                var genreId = genresList[index].id.toString()
                DynamicPageContent(
                    page = index,
                    lazyMovieItems = list[index],
                )

            }
        }
    }
}

@OptIn(ExperimentalPagingApi::class)
@Composable
private fun DynamicPageContent(
    page: Int,
    lazyMovieItems: LazyPagingItems<MovieModel>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        MovieList(lazyMovieItems = lazyMovieItems)
    }
}


/*
when (page) {
                    StaticPagerScreenPage.Summary -> SummaryContent(
                        homeViewModel.getPopularMoviesByGenre2("14").collectAsLazyPagingItems()
                    )
                    StaticPagerScreenPage.Info -> InformationContent(
                        homeViewModel.getPopularMoviesByGenre2("14").collectAsLazyPagingItems()

                    )
                    StaticPagerScreenPage.Details -> DetailsContent(
                        homeViewModel.getPopularMoviesByGenre2("10402").collectAsLazyPagingItems()

                    )
                }
 */
