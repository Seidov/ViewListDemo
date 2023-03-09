package com.sultanseidov.viewlistdemo2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sultanseidov.viewlistdemo2.data.entity.MovieModel
import com.sultanseidov.viewlistdemo2.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ViewListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    init {
        fetchPopularMovies1("14")
        fetchPopularMovies2("36")
        fetchPopularMovies3("10402")
    }

    private val _popularMoviesState = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val popularMoviesState = _popularMoviesState
    private val _popularMoviesState2 = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val popularMoviesState2 = _popularMoviesState2
    private val _popularMoviesState3 = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val popularMoviesState3 = _popularMoviesState3

    private fun fetchPopularMovies1(genre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPopularMovies(genre).cachedIn(viewModelScope).collect {
                _popularMoviesState.value = it
            }
        }
    }

    private fun fetchPopularMovies2(genre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPopularMovies2(genre).cachedIn(viewModelScope).collect {
                _popularMoviesState2.value = it
            }
        }
    }

    private fun fetchPopularMovies3(genre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPopularMovies3(genre).cachedIn(viewModelScope).collect {
                _popularMoviesState3.value = it
            }
        }
    }
}