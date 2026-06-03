package com.sultanseidov.viewlistdemo2.presentation.screens.pindetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultanseidov.viewlistdemo2.data.local.dao.PinDao
import com.sultanseidov.viewlistdemo2.data.local.dao.WatchedMovieDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinDetailViewModel @Inject constructor(
    private val pinDao: PinDao,
    private val watchedMovieDao: WatchedMovieDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PinDetailState())
    val uiState: StateFlow<PinDetailState> = _uiState.asStateFlow()

    fun onEvent(event: PinDetailEvent) {
        when (event) {
            is PinDetailEvent.LoadPinDetails -> loadPinDetails(event.pinId)
        }
    }

    private fun loadPinDetails(pinId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            val pin = pinDao.getPinById(pinId)
            _uiState.update { it.copy(pinTitle = pin?.title ?: "Unknown Cluster") }

            watchedMovieDao.getMoviesByPinIdFlow(pinId).collect { movies ->
                _uiState.update { 
                    it.copy(
                        movies = movies,
                        isLoading = false
                    )
                }
            }
        }
    }
}
