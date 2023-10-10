package com.sultanseidov.viewlistdemo2.domain.repository

import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresTvShowModel
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.ResponseMovieGenresListModel
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.ResponseTVShowGenresListModel
import com.sultanseidov.viewlistdemo2.data.model.dto.movie.MoviesRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.pinviewlist.PinViewListModel
import com.sultanseidov.viewlistdemo2.data.model.dto.tvshow.TvShowsRemoteKeys
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.model.MoviesViewList
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import com.sultanseidov.viewlistdemo2.domain.model.TVShowsViewList
import kotlinx.coroutines.flow.Flow

interface IRepository {

    //DiscoverMoviesDao
    fun getAllDiscoverMovies(with_genres:String): Flow<PagingData<MovieModel>>
    suspend fun insertDiscoverMovies(discoverMovies: List<MovieModel>)
    suspend fun deleteAllDiscoverMovies()


    //DiscoverMoviesRemoteKeysDao
    suspend fun getDiscoverMoviesRemoteKeys(id: Int): MoviesRemoteKeys
    suspend fun insertAllDiscoverMoviesRemoteKeys(remoteKeys: List<MoviesRemoteKeys>)
    suspend fun deleteAllDiscoverMoviesRemoteKeys()


    //DiscoverTvShowsDao
    fun getAllDiscoverTvShows(with_genres:String): Flow<PagingData<TvShowModel>>
    suspend fun insertDiscoverTvShows(discoverTvShows: List<TvShowModel>)
    suspend fun deleteAllDiscoverTvShows()


    //DiscoverTvShowsRemoteKeys
    suspend fun getDiscoverTvShowsRemoteKeys(id: Int): TvShowsRemoteKeys
    suspend fun insertAllDiscoverTvShowsRemoteKeys(remoteKeys: List<TvShowsRemoteKeys>)
    suspend fun deleteAllDiscoverTvShowsRemoteKeys()


    //GenresMovieDao
    fun getAllMovieGenres(): Flow<ResourceState<ResponseMovieGenresListModel>>
    suspend fun insertMovieGenres(genres: List<GenresMovieModel>)
    suspend fun deleteAllMovieGenres()


    //GenresTvShowDao
    fun getAllTvShowGenres(): Flow<ResourceState<ResponseTVShowGenresListModel>>
    suspend fun insertTvShowGenres(genres: List<GenresTvShowModel>)
    suspend fun deleteAllTvShowGenres()


    //ViewListMovieDao
    fun getMovieViewListById(movieId: Int): Flow<List<MoviesViewList>>
    fun getAllMoviesViewList(): Flow<List<MoviesViewList>>
    suspend fun insertMovieViewList(movieViewList: MoviesViewList)
    suspend fun insertMoviesViewList(movieViewList: List<MoviesViewList>)
    suspend fun updateMovieViewList(movieViewList: MoviesViewList)
    suspend fun deleteAllMovieViewList()


    //ViewListTvShowDao
    fun getTvShowViewListById(tvShowId: Int): Flow<List<TVShowsViewList>>
    fun getAllTvShowsViewList(): Flow<List<TVShowsViewList>>
    suspend fun insertTvShowViewList(tvShowViewList: TVShowsViewList)
    suspend fun insertTvShowsViewList(tvShowViewList: List<TVShowsViewList>)
    suspend fun updateTvShowViewList(tvShowViewList: TVShowsViewList)
    suspend fun deleteAllTvShowViewList()


    //PinsViewListDao
    fun getAllPinsViewList(): Flow<ResourceState<Flow<List<PinViewListModel>>>>
    suspend fun insertPinViewList(pin: PinViewListModel)
    suspend fun insertPinsViewList(pins: List<PinViewListModel>)
    suspend fun updatePinViewList(pin: PinViewListModel)
    suspend fun deleteAllPinsViewList()

}