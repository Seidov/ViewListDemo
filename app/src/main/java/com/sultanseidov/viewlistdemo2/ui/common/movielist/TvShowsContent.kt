package com.sultanseidov.viewlistdemo2.ui.common.movielist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.sultanseidov.viewlistdemo2.R
import com.sultanseidov.viewlistdemo2.data.model.tvshow.TvShowModel
import com.sultanseidov.viewlistdemo2.screens.common.ErrorItem
import com.sultanseidov.viewlistdemo2.screens.common.LoadingItem
import com.sultanseidov.viewlistdemo2.screens.common.LoadingView
import com.sultanseidov.viewlistdemo2.util.Constants.IMAGE_BASE_URL


@ExperimentalCoilApi
@Composable
fun TvShowsList(lazyMovieItems: LazyPagingItems<TvShowModel>) {

    LazyColumn(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = lazyMovieItems,
            key = { movieItem ->
                movieItem.pk
            }
        ) { movieItem ->
            movieItem?.let {
                TvShowsItem(tvShowItem = it)
            }
        }

        lazyMovieItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyMovieItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyMovieItems.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                        Log.e("TvShowsContent", e.error.localizedMessage!!)

                    }
                }
            }
        }

    }
}

@ExperimentalCoilApi
@Composable
fun TvShowsItem(tvShowItem: TvShowModel) {
    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        model = IMAGE_BASE_URL + tvShowItem.poster_path
    )

    Box(
        modifier = Modifier
            .clickable {
                /*
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://")
                )
                ContextCompat.startActivity(context, browserIntent, null)
                 */
            }
            .height(400.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "Image",
            contentScale = ContentScale.Crop
        )
        Surface(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .alpha(ContentAlpha.medium),
            color = Color.Black
        ) {}
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    //append("Photo by ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                        tvShowItem.name?.let { append(it) }
                    }
                },
                color = Color.White,
                fontSize = MaterialTheme.typography.caption.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            LikeCounter(
                modifier = Modifier.weight(3f),
                painter = painterResource(id = R.drawable.ic_star),
                likes = "${tvShowItem.popularity.toString()}"
            )
        }
    }
}
