package com.sultanseidov.viewlistdemo2.screens.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.sultanseidov.viewlistdemo2.ui.theme.ViewListDemo2Theme
import com.sultanseidov.viewlistdemo2.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import kotlin.math.max

object TestTagsDynamicPagerScreen {

    private const val pager = "dynamic-pager"
    const val tabRow = "dynamic-pager-tab-row"

    fun getPageTag(index: Int) = "$pager-$index"
}

@OptIn(ExperimentalPagerApi::class, ExperimentalPagingApi::class, ExperimentalCoilApi::class)
@Composable
fun DynamicPagerScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    var pageCount by remember { mutableStateOf(10) }
    val pages = remember(pageCount) { List(pageCount) { "Page $it" } }
    val getPopularMovies = homeViewModel.getPopularMovies.collectAsLazyPagingItems()

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
                    .testTag(TestTagsDynamicPagerScreen.getPageTag(index))
            ) {

                MovieList(lazyMovieItems = getPopularMovies)

            }
        }
    }
}




