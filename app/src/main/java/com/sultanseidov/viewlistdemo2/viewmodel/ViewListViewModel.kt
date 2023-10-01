package com.sultanseidov.viewlistdemo2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sultanseidov.viewlistdemo2.data.model.movie.MovieModel
import com.sultanseidov.viewlistdemo2.data.model.tvshow.TvShowModel
import com.sultanseidov.viewlistdemo2.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ViewListViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {

    init {
        fetchDiscoverMovies("")
        fetchDiscoverTvShows("")
    }

    private val _discoverMoviesState = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val discoverMoviesState = _discoverMoviesState
    private val _discoverTvShowsState = MutableStateFlow<PagingData<TvShowModel>>(PagingData.empty())
    val discoverTvShowsState = _discoverTvShowsState

    private fun fetchDiscoverMovies(genre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.getAllDiscoverMovies(genre).cachedIn(viewModelScope).collect {
                _discoverMoviesState.value = it
            }
        }
    }

    private fun fetchDiscoverTvShows(genre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.getAllDiscoverTvShows(genre).cachedIn(viewModelScope).collect {
                _discoverTvShowsState.value = it
            }
        }
    }


}