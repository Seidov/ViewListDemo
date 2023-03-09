package com.sultanseidov.viewlistdemo2.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import com.sultanseidov.viewlistdemo2.viewmodel.DiscoverViewModel
import com.sultanseidov.viewlistdemo2.viewmodel.SearchViewModel

@OptIn(ExperimentalPagingApi::class)
@Composable
fun SearchScreen(
    selectCourse: (Long) -> Unit,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()

) {
    Column {

    }
}