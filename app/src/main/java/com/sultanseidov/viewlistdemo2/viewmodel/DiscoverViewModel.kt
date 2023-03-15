package com.sultanseidov.viewlistdemo2.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sultanseidov.viewlistdemo2.data.entity.movie.PopularMoviesModel
import com.sultanseidov.viewlistdemo2.data.entity.genre.ResponseGenresListModel
import com.sultanseidov.viewlistdemo2.data.entity.base.ResourceState
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

    private val _genresState = mutableStateOf(ResponseGenresListModel(emptyList()))
    val genresState: State<ResponseGenresListModel> = _genresState

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

    private val _popularMoviesState = MutableStateFlow<PagingData<PopularMoviesModel>>(PagingData.empty())
    val popularMoviesState = _popularMoviesState

    fun fetchPopularMovies(genre:String) {
        viewModelScope.launch {
            repository.getPopularMovies(genre).cachedIn(viewModelScope).collect {
                _popularMoviesState.value = it
            }
        }
    }


}