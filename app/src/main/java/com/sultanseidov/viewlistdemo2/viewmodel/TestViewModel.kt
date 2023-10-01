package com.sultanseidov.viewlistdemo2.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.data.model.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.movie.MovieModel
import com.sultanseidov.viewlistdemo2.data.model.genre.ResponseMovieGenresListModel
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalPagingApi
@HiltViewModel
class TestViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {

    val getPopularMovies = repositoryImpl.getAllDiscoverMovies("")


    private val _genresState =
        mutableStateOf<ResourceState<ResponseMovieGenresListModel>>(ResourceState.Success(null))
    val genresState: State<ResourceState<ResponseMovieGenresListModel>> = _genresState

    private var _popularMoviesByGenre =  repositoryImpl.getAllDiscoverMovies("")
    var popularMoviesByGenre: Flow<PagingData<MovieModel>> =
        _popularMoviesByGenre

    fun fetchGenres() {
        viewModelScope.launch {
            repositoryImpl.getAllMovieGenres().collect { response ->
                _genresState.value = response
            }
        }
    }

    fun getPopularMoviesByGenre(id: String) {
        viewModelScope.launch {
            popularMoviesByGenre = repositoryImpl.getAllDiscoverMovies(id)
        }
    }

    fun getPopularMoviesByGenre2(id: String): Flow<PagingData<MovieModel>> {
        return repositoryImpl.getAllDiscoverMovies(id)
    }

    fun addGenres(genres: List<GenresMovieModel>) {
        viewModelScope.launch {
            repositoryImpl.insertMovieGenres(genres)
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