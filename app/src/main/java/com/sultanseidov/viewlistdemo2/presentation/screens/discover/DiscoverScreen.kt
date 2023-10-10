package com.sultanseidov.viewlistdemo2.presentation.screens.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.sultanseidov.viewlistdemo2.presentation.ui.common.tab.TabScreen
import com.sultanseidov.viewlistdemo2.presentation.viewmodel.DiscoverViewModel

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

    }
}