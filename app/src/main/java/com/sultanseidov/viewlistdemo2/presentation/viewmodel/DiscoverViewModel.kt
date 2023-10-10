package com.sultanseidov.viewlistdemo2.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.ResponseMovieGenresListModel
import com.sultanseidov.viewlistdemo2.data.repository.RepositoryImpl
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel(){

    init {
        getPins()
        fetchDiscoverTvShows("")
    }

    private val _discoverMoviesState = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val discoverMoviesState = _discoverMoviesState

    private val _discoverTvShowsState = MutableStateFlow<PagingData<TvShowModel>>(PagingData.empty())
    val discoverTvShowsState = _discoverTvShowsState

    private val _genresState = mutableStateOf(ResponseMovieGenresListModel(emptyList()))
    val genresState: State<ResponseMovieGenresListModel> = _genresState

    private fun fetchDiscoverTvShows(genre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.getAllDiscoverTvShows(genre).cachedIn(viewModelScope).collect {
                _discoverTvShowsState.value = it
            }
        }
    }

    private fun getPins(){
        viewModelScope.launch {

            val allPins= repositoryImpl.getAllPinsViewList().collect(){ response->
                when(response){
                    is ResourceState.Success -> {
                        Log.e("DiscoverViewModel","getPins allPins Success")

                    }
                    is ResourceState.Error -> {
                        Log.e("DiscoverViewModel","getPins allPins Error")

                    }

                    else ->{

                    }
                }
            }
        }
    }

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

}