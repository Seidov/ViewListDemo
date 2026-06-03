package com.sultanseidov.viewlistdemo2.presentation.screens.pindetail

import com.sultanseidov.viewlistdemo2.data.local.entity.WatchedMovieEntity

data class PinDetailState(
    val pinTitle: String = "",
    val movies: List<WatchedMovieEntity> = emptyList(),
    val isLoading: Boolean = false
)

sealed class PinDetailEvent {
    data class LoadPinDetails(val pinId: Long) : PinDetailEvent()
}
