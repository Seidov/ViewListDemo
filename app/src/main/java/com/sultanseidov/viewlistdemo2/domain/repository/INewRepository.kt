package com.sultanseidov.viewlistdemo2.domain.repository

import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresDto
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresTvShowModel
import com.sultanseidov.viewlistdemo2.data.model.pinviewlist.PinViewListModel
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.model.MoviesViewList
import com.sultanseidov.viewlistdemo2.domain.model.TVShowsViewList
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import kotlinx.coroutines.flow.Flow

interface INewRepository {

    fun getAllDiscoverMovies(with_genres:String?): Flow<PagingData<MovieModel>>

    fun getAllDiscoverTvShows(with_genres:String): Flow<PagingData<TvShowModel>>

    suspend fun getMovieGenres(): Flow<ResourceState<List<GenresMovieModel>>>

    suspend fun getTvShowGenres(): Flow<ResourceState<GenresDto>>

    //discoverMoviesDao
    suspend fun insertDiscoverMovies(discoverMovies: List<MovieModel>)
    suspend fun deleteAllDiscoverMovies()

    //discoverTvShowsDao
    suspend fun insertDiscoverTvShows(discoverTvShows: List<TvShowModel>)
    suspend fun deleteAllDiscoverTvShows()

    //genresMovieDao
    suspend fun getAllMovieGenres():Flow<List<GenresMovieModel>>
    suspend fun insertMovieGenres(genres: List<GenresMovieModel>)
    suspend fun deleteAllMovieGenres()

    //genresTvShowDao
    suspend fun getAllTvShowGenres():Flow<List<GenresTvShowModel>>
    suspend fun insertTvShowGenres(genres: List<GenresTvShowModel>)
    suspend fun deleteAllTvShowGenres()

    //viewListMoviesDao
    suspend fun getMovieViewListById(movieId: Int): Flow<List<MoviesViewList>>
    suspend fun getAllMoviesViewList(): Flow<List<MoviesViewList>>
    suspend fun insertMovieViewList(movieViewList: MoviesViewList)
    suspend fun insertMoviesViewList(movieViewList: List<MoviesViewList>)
    suspend fun updateMovieViewList(movieViewList: MoviesViewList)
    suspend fun deleteAllMovieViewList()

    //viewListTvShowsDao
    suspend fun getTvShowViewListById(tvShowId: Int): Flow<List<TVShowsViewList>>
    suspend fun getAllTvShowsViewList(): Flow<List<TVShowsViewList>>
    suspend fun insertTvShowViewList(tvShowViewList: TVShowsViewList)
    suspend fun insertTvShowsViewList(tvShowViewList: List<TVShowsViewList>)
    suspend fun updateTvShowViewList(tvShowViewList: TVShowsViewList)
    suspend fun deleteAllTvShowViewList()

    //pinViewListDao
    suspend fun getAllPinsViewList():  Flow<List<PinViewListModel>>
    suspend fun insertPinViewList(pin: PinViewListModel)
    suspend fun insertPinsViewList(pins: List<PinViewListModel>)
    suspend fun updatePinViewList(pin: PinViewListModel)
    suspend fun deleteAllPinsViewList()




}