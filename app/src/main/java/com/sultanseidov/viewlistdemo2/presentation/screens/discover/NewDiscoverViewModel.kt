package com.sultanseidov.viewlistdemo2.presentation.screens.discover

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.repository.RepositoryImpl
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import com.sultanseidov.viewlistdemo2.domain.usecase.discover.DiscoverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class NewDiscoverViewModel @Inject constructor(
    private val discoverUseCase: DiscoverUseCase
) : ViewModel(){

    private val _moviesState: MutableStateFlow<PagingData<MovieModel>> = MutableStateFlow(value = PagingData.empty())
    val moviesState: MutableStateFlow<PagingData<MovieModel>> get() = _moviesState

    init {
        onEvent(DiscoverEvent.GetHome)
    }

    fun onEvent(event: DiscoverEvent) {
        viewModelScope.launch {
            when (event) {
                is DiscoverEvent.GetHome -> {
                    getMovies()
                }
            }
        }
    }

    private suspend fun getMovies() {
        discoverUseCase.getDiscoverMoviesUseCase.execute(Unit)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _moviesState.value = it
            }
    }

}