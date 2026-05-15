package com.sultanseidov.viewlistdemo2.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sultanseidov.viewlistdemo2.domain.usecase.discover.DiscoverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewVM @Inject constructor(
    private val discoverUseCase: DiscoverUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<NewState>(NewState())
    val state: State<NewState> = _state
    private var job : Job? = null


    init {
        getBlogs()
    }

    fun getBlogs() {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                val flow = discoverUseCase.getDiscoverTVShowsUseCase.execute(Unit)
                    .cachedIn(viewModelScope)
                _state.value = NewState(movies = flow)
            } catch (e: Exception) {
                _state.value = NewState(error = e.message ?: "Error!")
            }
        }
    }

}
