package com.sultanseidov.viewlistdemo2.data.repository.dataSource

import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresDto
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {

    fun getAllDiscoverMovies(with_genres: String): Flow<PagingData<MovieModel>>

    fun getAllDiscoverTvShows(with_genres: String): Flow<PagingData<TvShowModel>>

    suspend fun getAllMovieGenres(): Flow<ResourceState<List<GenresMovieModel>>>

    suspend fun getAllTvShowGenres(): Flow<ResourceState<GenresDto>>

}