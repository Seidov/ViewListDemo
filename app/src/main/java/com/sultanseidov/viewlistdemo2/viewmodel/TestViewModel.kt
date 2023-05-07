package com.sultanseidov.viewlistdemo2.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.data.model.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.movie.MovieModel
import com.sultanseidov.viewlistdemo2.data.model.genre.ResponseGenresListModel
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalPagingApi
@HiltViewModel
class TestViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val getPopularMovies = repository.getDiscoverMovies("")

    val getAllGenres = repository.getAllGenres

    private val _genresState =
        mutableStateOf<ResourceState<ResponseGenresListModel>>(ResourceState.Success(null))
    val genresState: State<ResourceState<ResponseGenresListModel>> = _genresState

    private var _popularMoviesByGenre =  repository.getDiscoverMovies("")
    var popularMoviesByGenre: Flow<PagingData<MovieModel>> =
        _popularMoviesByGenre

    fun fetchGenres() {
        viewModelScope.launch {
            repository.fetchMoviesGenre().collect { response ->
                _genresState.value = response
            }
        }
    }

    fun getPopularMoviesByGenre(id: String) {
        viewModelScope.launch {
            popularMoviesByGenre = repository.getDiscoverMovies(id)
        }
    }

    fun getPopularMoviesByGenre2(id: String): Flow<PagingData<MovieModel>> {
        return repository.getDiscoverMovies(id)
    }

    fun addGenres(genres: List<GenresMovieModel>) {
        viewModelScope.launch {
            repository.insertGenresList(genres)
        }
    }


/*
    fun fetchDiscoverMoviesWithGenres() {
        upcomingMovies.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.getUpcomingMovies()
            upcomingMovies.value = response
        }
    }
 */


}