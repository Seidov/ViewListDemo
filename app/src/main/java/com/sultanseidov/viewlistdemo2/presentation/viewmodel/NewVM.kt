package com.sultanseidov.viewlistdemo2.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.domain.usecase.discover.DiscoverUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NewV @Inject constructor(
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
        job = discoverUseCase.getDiscoverTVShowsUseCase.executeGetTVShows("").onEach {
            when (it) {
                is ResourceState.Success -> {
                    _state.value = NewState(movies = it.data)
                }

                is ResourceState.Error -> {
                    _state.value = NewState(error = it.message ?: "Error!")
                }

                is ResourceState.Loading -> {
                    _state.value = NewState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

}