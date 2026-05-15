package com.sultanseidov.viewlistdemo2.presentation.ui.common.movielist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import com.sultanseidov.viewlistdemo2.R
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.presentation.ui.theme.StarRed
import com.sultanseidov.viewlistdemo2.screens.common.ErrorItem
import com.sultanseidov.viewlistdemo2.screens.common.LoadingItem
import com.sultanseidov.viewlistdemo2.screens.common.LoadingView
import com.sultanseidov.viewlistdemo2.util.Constants.IMAGE_BASE_URL

@Composable
fun MovieList(
    lazyMovieItems: LazyPagingItems<MovieModel>
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(
            count = lazyMovieItems.itemCount,
            key = lazyMovieItems.itemKey { it.id }
        ) { index ->

            val movieItem = lazyMovieItems[index]

            movieItem?.let {
                MovieItem(movieItem = it)
            }
        }

        lazyMovieItems.apply {

            when {

                loadState.refresh is LoadState.Loading -> {

                    item {
                        LoadingView(
                            modifier = Modifier.fillParentMaxSize()
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {

                    item {
                        LoadingItem()
                    }
                }

                loadState.refresh is LoadState.Error -> {

                    val error =
                        loadState.refresh as LoadState.Error

                    item {

                        ErrorItem(
                            message = error.error.localizedMessage
                                ?: "Unknown Error",
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }

                loadState.append is LoadState.Error -> {

                    val error =
                        loadState.append as LoadState.Error

                    item {

                        ErrorItem(
                            message = error.error.localizedMessage
                                ?: "Unknown Error",
                            onClickRetry = { retry() }
                        )

                        Log.e(
                            "MoviesContent",
                            error.error.localizedMessage ?: "Unknown Error"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movieItem: MovieModel
) {

    val painter = rememberAsyncImagePainter(
        model = IMAGE_BASE_URL + movieItem.poster_path
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clickable {

            },
        contentAlignment = Alignment.BottomCenter
    ) {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .alpha(ContentAlpha.medium),
            color = Color.Black
        ) {}

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = buildAnnotatedString {

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Black
                        )
                    ) {

                        append(movieItem.title ?: "")
                    }
                },
                color = Color.White,
                fontSize = MaterialTheme.typography.body2.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            LikeCounter(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.ic_star),
                likes = "${movieItem.popularity}"
            )
        }
    }
}

@Composable
fun LikeCounter(
    modifier: Modifier,
    painter: Painter,
    likes: String
) {

    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        Icon(
            painter = painter,
            contentDescription = "Star Icon",
            tint = StarRed
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = likes,
            color = Color.White,
            fontSize = MaterialTheme.typography.body2.fontSize,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}