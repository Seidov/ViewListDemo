package com.sultanseidov.viewlistdemo2.presentation.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.sultanseidov.viewlistdemo2.presentation.ui.common.glassmorphism
import com.sultanseidov.viewlistdemo2.presentation.ui.common.movielist.MovieItem
import com.sultanseidov.viewlistdemo2.presentation.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onMovieClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    val isClassifying by viewModel.isClassifying.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // Premium Glassmorphic Search Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .glassmorphism()
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search for Cinematic Discovery...", color = Color.White.copy(alpha = 0.5f)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                singleLine = true
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 12.dp,
                    end = 12.dp,
                    bottom = 100.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalItemSpacing = 12.dp
            ) {
                items(
                    count = searchResults.itemCount,
                    key = { index ->
                        val movie = searchResults.peek(index)
                        movie?.id ?: "placeholder_$index"
                    }
                ) { index ->
                    val movie = searchResults[index]
                    movie?.let {
                        MovieItem(
                            movieItem = it,
                            onMovieClick = onMovieClick,
                            onListClick = { movieId ->
                                viewModel.onListClicked(movieId)
                            }
                        )
                    }
                }
            }

            if (isClassifying) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
