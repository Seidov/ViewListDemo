package com.sultanseidov.viewlistdemo2.data.repository

import androidx.paging.PagingSource
import com.sultanseidov.viewlistdemo2.data.model.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.genre.GenresTvShowModel
import com.sultanseidov.viewlistdemo2.data.model.movie.MovieModel
import com.sultanseidov.viewlistdemo2.data.model.movie.MoviesRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.viewlist.ViewListMoviesModel
import com.sultanseidov.viewlistdemo2.data.model.tvshow.TvShowModel
import com.sultanseidov.viewlistdemo2.data.model.tvshow.TvShowsRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.viewlist.ViewListTvShowsModel
import com.sultanseidov.viewlistdemo2.data.model.viewlistpin.PinsViewListModel
import kotlinx.coroutines.flow.Flow

interface IRepository {

    //DiscoverMoviesDao
    fun getAllDiscoverMovies(): PagingSource<Int, MovieModel>
    suspend fun insertDiscoverMovies(discoverMovies: List<MovieModel>)
    suspend fun deleteAllDiscoverMovies()

    //DiscoverMoviesRemoteKeysDao
    suspend fun getDiscoverMoviesRemoteKeys(id: Int): MoviesRemoteKeys
    suspend fun insertAllDiscoverMoviesRemoteKeys(remoteKeys: List<MoviesRemoteKeys>)
    suspend fun deleteAllDiscoverMoviesRemoteKeys()

    //DiscoverTvShowsDao
    fun getAllDiscoverTvShows(): PagingSource<Int, TvShowModel>
    suspend fun insertDiscoverTvShows(discoverTvShows: List<TvShowModel>)
    suspend fun deleteAllDiscoverTvShows()

    //DiscoverTvShowsRemoteKeys
    suspend fun getDiscoverTvShowsRemoteKeys(id: Int): TvShowsRemoteKeys
    suspend fun insertAllDiscoverTvShowsRemoteKeys(remoteKeys: List<TvShowsRemoteKeys>)
    suspend fun deleteAllDiscoverTvShowsRemoteKeys()

    //GenresMovieDao
    fun getAllMovieGenres(): Flow<List<GenresMovieModel>>
    suspend fun insertMovieGenres(genres: List<GenresMovieModel>)
    suspend fun deleteAllMovieGenres()

    //GenresTvShowDao
    fun getAllTvShowGenres(): Flow<List<GenresTvShowModel>>
    suspend fun insertTvShowGenres(genres: List<GenresTvShowModel>)
    suspend fun deleteAllTvShowGenres()

    //ViewListMovieDao
    fun getMovieInMyViewListById(movieId: Int): Flow<List<ViewListMoviesModel>>
    fun getAllMoviesInMyViewList(): Flow<List<ViewListMoviesModel>>
    suspend fun insertMovieInMyViewList(movieViewList: ViewListMoviesModel)
    suspend fun insertMoviesInMyViewList(movieViewList: List<MovieModel>)
    suspend fun updateMovieInMyViewList(movieViewList: ViewListMoviesModel)
    suspend fun deleteAllMovieInMyViewList()

    //ViewListTvShowDao
    fun getTvShowInMyViewListById(tvShowId: Int): Flow<List<ViewListTvShowsModel>>
    fun getAllTvShowsInMyViewList(): Flow<List<ViewListTvShowsModel>>
    suspend fun insertTvShowInMyViewList(tvShowViewList: ViewListTvShowsModel)
    suspend fun insertTvShowsInMyViewList(tvShowViewList: List<TvShowModel>)
    suspend fun updateTvShowInMyViewList(tvShowViewList: ViewListTvShowsModel)
    suspend fun deleteAllTvShowInMyViewList()

    //PinsViewListDao
    fun getAllPinsViewList(): Flow<List<PinsViewListModel>>
    suspend fun insertPinViewList(viewList: PinsViewListModel)
    suspend fun updatePinViewList(viewList: PinsViewListModel)
    suspend fun deleteAllPinViewList()


}