package com.sultanseidov.viewlistdemo2.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.ResponseMovieGenresListModel
import com.sultanseidov.viewlistdemo2.data.model.pinviewlist.PinViewListModel
import com.sultanseidov.viewlistdemo2.data.repository.RepositoryImpl
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel@Inject constructor(
    private val repositoryImpl: RepositoryImpl
):ViewModel(){

    private val _discoverMoviesState = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val discoverMoviesState = _discoverMoviesState

    private val _discoverTvShowsState = MutableStateFlow<PagingData<TvShowModel>>(PagingData.empty())
    val discoverTvShowsState = _discoverTvShowsState


    private val _genresState = mutableStateOf(ResponseMovieGenresListModel(emptyList()))
    val genresState: State<ResponseMovieGenresListModel> = _genresState

    private val _pinsState = mutableStateOf(listOf<PinViewListModel>())
    val pinsState: State<List<PinViewListModel>> = _pinsState

    fun fetchGenres() {
        viewModelScope.launch {
            repositoryImpl.getAllMovieGenres().collect { response ->

                when(response) {
                    is ResourceState.Success -> {

                        _genresState.value = response.data!!
                        repositoryImpl.insertMovieGenres(response.data.genres)

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
            repositoryImpl.getAllDiscoverMovies(genre).cachedIn(viewModelScope).collect {
                _discoverMoviesState.value = it
            }
        }
    }

    private fun fetchDiscoverMovies2(genre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.getAllDiscoverMovies(genre).cachedIn(viewModelScope).collect {
                _discoverMoviesState.value = it
            }
        }
    }


    fun fetchDiscoverTvShows(genre:String) {
        viewModelScope.launch {
            repositoryImpl.getAllDiscoverTvShows(genre).cachedIn(viewModelScope).collect {
                _discoverTvShowsState.value = it
            }
        }
    }


}