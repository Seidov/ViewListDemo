package com.sultanseidov.viewlistdemo2.presentation.screens.library

import com.sultanseidov.viewlistdemo2.data.local.entity.PinEntity

data class LibraryState(
    val pins: List<PinEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class LibraryEvent {
    object LoadLibrary : LibraryEvent()
    data class DeletePin(val pinId: Long) : LibraryEvent()
    object RefreshLibrary : LibraryEvent()
}
