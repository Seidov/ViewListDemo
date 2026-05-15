package com.sultanseidov.viewlistdemo2.presentation.viewmodel

import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import kotlinx.coroutines.flow.Flow

class NewState(
    val isLoading: Boolean = false,
    val movies: Flow<PagingData<TvShowModel>>? = null,
    val error: String = "",
    val search: String = " "
)