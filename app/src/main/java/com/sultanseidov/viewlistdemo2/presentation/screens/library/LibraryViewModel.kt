package com.sultanseidov.viewlistdemo2.presentation.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultanseidov.viewlistdemo2.data.local.dao.PinDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val pinDao: PinDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryState())
    val uiState: StateFlow<LibraryState> = _uiState.asStateFlow()

    init {
        onEvent(LibraryEvent.LoadLibrary)
    }

    fun onEvent(event: LibraryEvent) {
        when (event) {
            LibraryEvent.LoadLibrary -> {
                observePins()
            }
            is LibraryEvent.DeletePin -> {
                // Implementation for delete if needed in Dao
            }
            LibraryEvent.RefreshLibrary -> {
                observePins()
            }
        }
    }

    private fun observePins() {
        pinDao.getActivePinsFlow()
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .onEach { pins ->
                _uiState.update { it.copy(pins = pins, isLoading = false) }
            }
            .catch { e ->
                _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }
}
