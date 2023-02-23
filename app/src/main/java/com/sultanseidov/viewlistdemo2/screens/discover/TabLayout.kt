package com.sultanseidov.viewlistdemo2.screens.discover

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.sultanseidov.viewlistdemo2.screens.common.DynamicPagerScreen
import com.sultanseidov.viewlistdemo2.navigation.Tabs

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun TabLayout(
    navController: NavHostController
) {

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Tabs.SEARCH.route)
                }
            )
        },
        content = {
            DynamicPagerScreen()
        }
    )
}