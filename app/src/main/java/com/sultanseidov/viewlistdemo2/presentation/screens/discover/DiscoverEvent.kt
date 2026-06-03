package com.sultanseidov.viewlistdemo2.presentation.screens.discover

sealed class DiscoverEvent {
    object GetHome : DiscoverEvent()
    data class OnListClicked(val movieId: Long) : DiscoverEvent()
}