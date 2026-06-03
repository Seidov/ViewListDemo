package com.sultanseidov.viewlistdemo2.presentation.screens.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.sultanseidov.viewlistdemo2.data.local.entity.DiscoveryMediaEntity
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.presentation.ui.common.GlitchText
import com.sultanseidov.viewlistdemo2.presentation.ui.common.ShimmerLoadingCard
import com.sultanseidov.viewlistdemo2.presentation.ui.common.movielist.MovieItem
import com.sultanseidov.viewlistdemo2.screens.common.LoadingItem
import com.sultanseidov.viewlistdemo2.util.CyberLogger
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun DiscoverScreen(
    navController: NavHostController,
    viewModel: NewDiscoverViewModel = hiltViewModel(),
) {
    val tabs by viewModel.tabs.collectAsState()
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    val items = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val isClassifying by viewModel.isClassifying.collectAsState()

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    // Sync Pager with ViewModel
    LaunchedEffect(selectedTabIndex) {
        if (pagerState.currentPage != selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (selectedTabIndex != pagerState.currentPage) {
            viewModel.onTabChanged(pagerState.currentPage)
        }
    }

    // Telemetry
    var lastLoadingStartTime by remember { mutableLongStateOf(0L) }
    LaunchedEffect(items.loadState) {
        val isAnyLoading = (items.loadState.refresh is LoadState.Loading) || 
                          (items.loadState.append is LoadState.Loading)
        
        if (isAnyLoading && lastLoadingStartTime == 0L) {
            lastLoadingStartTime = System.currentTimeMillis()
            val currentTag = tabs.getOrNull(selectedTabIndex)?.tag ?: "UNKNOWN"
            CyberLogger.logDiscoverLoadState("SYNCING $currentTag FEED")
        } else if (!isAnyLoading && lastLoadingStartTime > 0L) {
            val delta = System.currentTimeMillis() - lastLoadingStartTime
            CyberLogger.logDiscoverLoadState("FEED UPDATED IN $delta ms")
            lastLoadingStartTime = 0L
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(32.dp))

            if (tabs.isNotEmpty()) {
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary,
                    edgePadding = 16.dp,
                    divider = {},
                    indicator = { tabPositions: List<TabPosition> ->
                        if (selectedTabIndex < tabPositions.size) {
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                ) {
                    tabs.forEachIndexed { index, tabItem ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { viewModel.onTabChanged(index) },
                            text = {
                                Text(
                                    text = tabItem.title.uppercase(),
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        letterSpacing = 1.sp,
                                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                                    )
                                )
                            }
                        )
                    }
                }

                HorizontalPager(
                    count = tabs.size,
                    state = pagerState,
                    modifier = Modifier.weight(1f),
                    userScrollEnabled = !isClassifying
                ) { index: Int ->
                    DiscoveryGrid(
                        items = items,
                        onListClick = { id: Long -> viewModel.onEvent(DiscoverEvent.OnListClicked(id)) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
        }

        if (isClassifying) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(16.dp))
                    GlitchText(
                        text = "CLASSIFYING NEURAL FOOTPRINT...",
                        style = MaterialTheme.typography.labelLarge.copy(color = Color.White)
                    )
                }
            }
        }
    }
}

@Composable
fun DiscoveryGrid(
    items: LazyPagingItems<DiscoveryMediaEntity>,
    onListClick: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = items.itemCount,
            key = items.itemKey { "${it.categoryTag}_${it.id}" }
        ) { index: Int ->
            items[index]?.let { media ->
                MovieItem(
                    movieItem = media.toMovieModel(),
                    onMovieClick = { /* TODO */ },
                    onListClick = onListClick
                )
            }
        }

        items.loadState.apply {
            when {
                refresh is LoadState.Loading -> {
                    items(6) { ShimmerLoadingCard() }
                }
                append is LoadState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingItem()
                        }
                    }
                }
            }
        }
    }
}

// Extension to map unified entity back to movie model for UI
private fun DiscoveryMediaEntity.toMovieModel(): MovieModel {
    return MovieModel(
        id = id,
        title = title,
        poster_path = posterPath,
        overview = "",
        release_date = null,
        vote_average = voteAverage ?: 0.0,
        genre_ids = emptyList(),
        adult = false,
        backdrop_path = null,
        original_language = null,
        original_title = title,
        popularity = 0.0,
        video = false,
        vote_count = 0,
        page = page
    )
}
