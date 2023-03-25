package com.sultanseidov.viewlistdemo2.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sultanseidov.viewlistdemo2.data.entity.movie.MovieModel
import com.sultanseidov.viewlistdemo2.data.entity.genre.ResponseGenresListModel
import com.sultanseidov.viewlistdemo2.data.entity.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.entity.tvshow.TvShowModel
import com.sultanseidov.viewlistdemo2.data.entity.viewlistpin.PinsViewListModel
import com.sultanseidov.viewlistdemo2.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _discoverMoviesState = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val discoverMoviesState = _discoverMoviesState

    private val _discoverTvShowsState = MutableStateFlow<PagingData<TvShowModel>>(PagingData.empty())
    val discoverTvShowsState = _discoverTvShowsState


    private val _genresState = mutableStateOf(ResponseGenresListModel(emptyList()))
    val genresState: State<ResponseGenresListModel> = _genresState

    private val _pinsState = mutableStateOf(listOf<PinsViewListModel>())
    val pinsState: State<List<PinsViewListModel>> = _pinsState

    fun fetchGenres() {
        viewModelScope.launch {
            repository.getMovieGenres().collect { response ->

                when(response) {
                    is ResourceState.Success -> {

                        _genresState.value = response.data!!
                        repository.addGenresList(response.data.genres)

                    }
                    is ResourceState.Error -> {

                    }
                    else -> {}
                }
            }
        }
    }

    fun fetchDiscoverMovies(genre:String) {
        viewModelScope.launch {
            repository.getDiscoverMovies(genre).cachedIn(viewModelScope).collect {
                _discoverMoviesState.value = it
            }
        }
    }

    fun fetchDiscoverTvShows(genre:String) {
        viewModelScope.launch {
            repository.getDiscoverTvShows(genre).cachedIn(viewModelScope).collect {
                _discoverTvShowsState.value = it
            }
        }
    }




}