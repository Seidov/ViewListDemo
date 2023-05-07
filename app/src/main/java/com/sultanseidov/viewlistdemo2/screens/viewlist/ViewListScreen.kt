package com.sultanseidov.viewlistdemo2.screens.viewlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.ExperimentalPagingApi
import com.sultanseidov.viewlistdemo2.ui.common.tab.StaticPagerScreen

@OptIn(ExperimentalPagingApi::class)
@Composable
fun ViewListScreen(
    selectCourse: (Long) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .statusBarsPadding()
    ) {
        StaticPagerScreen()
    }
}

