package com.sultanseidov.viewlistdemo2.presentation.ui._testScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.presentation.viewmodel.TestViewModel
import kotlinx.coroutines.launch

object TestTagsDynamicPagerScreen {

    private const val pager = "dynamic-pager"
    const val tabRow = "dynamic-pager-tab-row"

    fun getPageTag(index: Int) = "$pager-$index"
}

@OptIn(ExperimentalPagerApi::class, ExperimentalPagingApi::class, ExperimentalCoilApi::class)
@Composable
fun DynamicPagerScreen(
    tabGenresList: List<GenresMovieModel>,
    testViewModel: TestViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    val genresList = tabGenresList
    val pageCount by remember { mutableIntStateOf(genresList.size) }
    val pages = remember(pageCount) { List(pageCount) { genresList[it].name } }

    val list = mutableListOf<LazyPagingItems<MovieModel>>()
    tabGenresList.forEach {
        //list.add(testViewModel.getPopularMoviesByGenre2(it.id.toString()).collectAsLazyPagingItems())
    }


    Column {
        ScrollableTabRow(
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
            modifier = Modifier.testTag(TestTagsDynamicPagerScreen.tabRow),
            edgePadding = 16.dp
        ) {
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title, style = MaterialTheme.typography.labelLarge) },
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                val genreId = genresList[index].id.toString()
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
        //MovieList(lazyMovieItems = lazyMovieItems)
    }
}
