package com.sultanseidov.viewlistdemo2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.domain.repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val repository: IMovieRepository
) : ViewModel() {

    fun fetchGenres() {
        viewModelScope.launch {
            repository.getMovieGenres().collect {
                // handle state
            }
        }
    }

    fun addGenres(genres: List<GenresMovieModel>) {
        viewModelScope.launch {
            repository.insertMovieGenres(genres)
        }
    }

    fun fetchDiscoverMoviesWithGenres() {
        viewModelScope.launch {
            repository.getAllDiscoverMovies("").collect {
                // handle paging data
            }
        }
    }
}
