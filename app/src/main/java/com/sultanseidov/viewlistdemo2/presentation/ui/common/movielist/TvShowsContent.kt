package com.sultanseidov.viewlistdemo2.presentation.ui.common.movielist

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.sultanseidov.viewlistdemo2.R
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import com.sultanseidov.viewlistdemo2.presentation.ui.common.glassmorphism
import com.sultanseidov.viewlistdemo2.presentation.ui.theme.Shapes
import com.sultanseidov.viewlistdemo2.screens.common.ErrorItem
import com.sultanseidov.viewlistdemo2.screens.common.LoadingItem
import com.sultanseidov.viewlistdemo2.screens.common.LoadingView
import com.sultanseidov.viewlistdemo2.util.Constants.IMAGE_BASE_URL

@Composable
fun TvShowsList(
    lazyMovieItems: LazyPagingItems<TvShowModel>,
    onListClick: (Long) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 12.dp,
            end = 12.dp,
            bottom = 120.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = lazyMovieItems.itemCount,
            key = lazyMovieItems.itemKey { it.id }
        ) { index ->
            lazyMovieItems[index]?.let { tvShowItem ->
                TvShowsItem(
                    tvShowItem = tvShowItem,
                    onListClick = onListClick
                )
            }
        }

        lazyMovieItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        LoadingView(modifier = Modifier.fillMaxSize())
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingItem()
                        }
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        ErrorItem(
                            message = error.error.localizedMessage ?: "Unknown Error",
                            modifier = Modifier.fillMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        ErrorItem(
                            message = error.error.localizedMessage ?: "Unknown Error",
                            onClickRetry = { retry() }
                        )
                        Log.e("TvShowsContent", error.error.localizedMessage ?: "Unknown Error")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TvShowsItem(
    tvShowItem: TvShowModel,
    onListClick: (Long) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onListClick(tvShowItem.id.toLong())
                },
                onLongClick = {
                    // Placeholder for future quick cinematic capsule preview
                }
            ),
        shape = Shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(modifier = Modifier.aspectRatio(0.67f)) {
            AsyncImage(
                model = IMAGE_BASE_URL + tvShowItem.poster_path,
                contentDescription = "TV Show Poster",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.8f)
                            ),
                            startY = 300f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
                    .glassmorphism(shape = Shapes.medium)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = tvShowItem.name ?: "Unknown",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            onListClick(tvShowItem.id.toLong())
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_viewlist),
                            contentDescription = "LIST",
                            tint = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }
    }
}
