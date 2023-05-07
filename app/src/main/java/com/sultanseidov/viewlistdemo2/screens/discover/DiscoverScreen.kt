package com.sultanseidov.viewlistdemo2.screens.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.sultanseidov.viewlistdemo2.ui.common.tab.TabScreen
import com.sultanseidov.viewlistdemo2.viewmodel.DiscoverViewModel

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun DiscoverScreen(
    navController: NavHostController,
    discoverViewModel: DiscoverViewModel = hiltViewModel()

) {

    fun launch() {

        discoverViewModel.fetchGenres()
    }

    launch()

    Column {
        TabScreen(discoverViewModel.genresState.value.genres)
    }
}