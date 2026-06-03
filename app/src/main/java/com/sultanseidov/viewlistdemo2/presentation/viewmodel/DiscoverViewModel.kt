package com.sultanseidov.viewlistdemo2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import com.sultanseidov.viewlistdemo2.domain.repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val repository: IMovieRepository
) : ViewModel(){

    private val _discoverMoviesState = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val discoverMoviesState = _discoverMoviesState

    private val _discoverTvShowsState = MutableStateFlow<PagingData<TvShowModel>>(PagingData.empty())
    val discoverTvShowsState = _discoverTvShowsState

    private fun fetchDiscoverTvShows(genre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllDiscoverTvShows(genre).cachedIn(viewModelScope).collect {
                _discoverTvShowsState.value = it
            }
        }
    }

    private fun getPins(){
        viewModelScope.launch {
            repository.getAllPinsViewList().collect { pins ->
                Log.e("DiscoverViewModel", "getPins allPins Success: ${pins.size}")
            }
        }
    }
}
