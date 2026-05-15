package com.sultanseidov.viewlistdemo2.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.sultanseidov.viewlistdemo2.navigation.Screen
import com.sultanseidov.viewlistdemo2.screens.common.MovieList
import com.sultanseidov.viewlistdemo2.viewmodel.HomeViewModel

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
){
    val getPopularMovies = homeViewModel.getPopularMovies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }
        ,
        content = {
            MovieList(lazyMovieItems = getPopularMovies)
        }
    )
}