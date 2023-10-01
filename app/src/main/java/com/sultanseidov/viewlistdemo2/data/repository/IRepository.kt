package com.sultanseidov.viewlistdemo2.data.repository

import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.model.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.genre.GenresTvShowModel
import com.sultanseidov.viewlistdemo2.data.model.genre.ResponseMovieGenresListModel
import com.sultanseidov.viewlistdemo2.data.model.genre.ResponseTVShowGenresListModel
import com.sultanseidov.viewlistdemo2.data.model.movie.MovieModel
import com.sultanseidov.viewlistdemo2.data.model.movie.MoviesRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.viewlist.ViewListMoviesModel
import com.sultanseidov.viewlistdemo2.data.model.tvshow.TvShowModel
import com.sultanseidov.viewlistdemo2.data.model.tvshow.TvShowsRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.viewlist.ViewListTvShowsModel
import com.sultanseidov.viewlistdemo2.data.model.pinviewlist.PinViewListModel
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
    fun getMovieViewListById(movieId: Int): Flow<List<ViewListMoviesModel>>
    fun getAllMoviesViewList(): Flow<List<ViewListMoviesModel>>
    suspend fun insertMovieViewList(movieViewList: ViewListMoviesModel)
    suspend fun insertMoviesViewList(movieViewList: List<MovieModel>)
    suspend fun updateMovieViewList(movieViewList: ViewListMoviesModel)
    suspend fun deleteAllMovieViewList()


    //ViewListTvShowDao
    fun getTvShowViewListById(tvShowId: Int): Flow<List<ViewListTvShowsModel>>
    fun getAllTvShowsViewList(): Flow<List<ViewListTvShowsModel>>
    suspend fun insertTvShowViewList(tvShowViewList: ViewListTvShowsModel)
    suspend fun insertTvShowsViewList(tvShowViewList: List<TvShowModel>)
    suspend fun updateTvShowViewList(tvShowViewList: ViewListTvShowsModel)
    suspend fun deleteAllTvShowViewList()


    //PinsViewListDao
    fun getAllPinsViewList(): Flow<ResourceState<Flow<List<PinViewListModel>>>>
    suspend fun insertPinViewList(pin: PinViewListModel)
    suspend fun insertPinsViewList(pins: List<PinViewListModel>)
    suspend fun updatePinViewList(pin: PinViewListModel)
    suspend fun deleteAllPinsViewList()

}