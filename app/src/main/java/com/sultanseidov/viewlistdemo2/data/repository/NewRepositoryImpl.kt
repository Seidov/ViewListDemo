package com.sultanseidov.viewlistdemo2.data.repository

import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresDto
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresTvShowModel
import com.sultanseidov.viewlistdemo2.data.model.pinviewlist.PinViewListModel
import com.sultanseidov.viewlistdemo2.data.repository.dataSource.ILocalDataSource
import com.sultanseidov.viewlistdemo2.data.repository.dataSource.IRemoteDataSource
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.model.MoviesViewList
import com.sultanseidov.viewlistdemo2.domain.model.TVShowsViewList
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import com.sultanseidov.viewlistdemo2.domain.repository.INewRepository
import kotlinx.coroutines.flow.Flow

class NewRepositoryImpl(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) : INewRepository {

    /*
    override fun getAllDiscoverMovies(with_genres: String?): Flow<PagingData<MovieModel>> {
        TODO("Not yet implemented")
    }
     */


    override fun getAllDiscoverMovies(with_genres: String?): Flow<PagingData<MovieModel>> {
        return with_genres?.let { remoteDataSource.getAllDiscoverMovies(it) }!!
    }

    override fun getAllDiscoverTvShows(with_genres: String): Flow<PagingData<TvShowModel>> {
        return remoteDataSource.getAllDiscoverTvShows(with_genres)
    }

    override suspend fun getMovieGenres(): Flow<ResourceState<List<GenresMovieModel>>> {
        return remoteDataSource.getAllMovieGenres()
    }

    override suspend fun getTvShowGenres(): Flow<ResourceState<GenresDto>> {
        return remoteDataSource.getAllTvShowGenres()
    }

    //discoverMoviesDao
    override suspend fun insertDiscoverMovies(discoverMovies: List<MovieModel>) {
        localDataSource.insertDiscoverMovies(discoverMovies)
    }
    override suspend fun deleteAllDiscoverMovies() {
        localDataSource.deleteAllDiscoverMovies()
    }

    //discoverTvShowsDao
    override suspend fun insertDiscoverTvShows(discoverTvShows: List<TvShowModel>) {
        localDataSource.insertDiscoverTvShows(discoverTvShows)
    }
    override suspend fun deleteAllDiscoverTvShows() {
        localDataSource.deleteAllDiscoverTvShows()
    }

    //genresMovieDao
    override suspend fun getAllMovieGenres(): Flow<List<GenresMovieModel>> {
        return localDataSource.getAllMovieGenres()
    }
    override suspend fun insertMovieGenres(genres: List<GenresMovieModel>) {
        localDataSource.insertMovieGenres(genres)
    }
    override suspend fun deleteAllMovieGenres() {
        localDataSource.deleteAllMovieGenres()
    }

    //genresTvShowDao
    override suspend fun getAllTvShowGenres(): Flow<List<GenresTvShowModel>> {
        return localDataSource.getAllTvShowGenres()
    }
    override suspend fun insertTvShowGenres(genres: List<GenresTvShowModel>) {
        localDataSource.insertTvShowGenres(genres)
    }
    override suspend fun deleteAllTvShowGenres() {
        localDataSource.deleteAllTvShowGenres()
    }

    //viewListMoviesDao
    override suspend fun getMovieViewListById(movieId: Int): Flow<List<MoviesViewList>> {
        return localDataSource.getMovieViewListById(movieId)
    }
    override suspend fun getAllMoviesViewList(): Flow<List<MoviesViewList>> {
        return localDataSource.getAllMoviesViewList()
    }
    override suspend fun insertMovieViewList(movieViewList: MoviesViewList) {
        localDataSource.insertMovieViewList(movieViewList)
    }
    override suspend fun insertMoviesViewList(movieViewList: List<MoviesViewList>) {
       localDataSource.insertMoviesViewList(movieViewList)
    }
    override suspend fun updateMovieViewList(movieViewList: MoviesViewList) {
        localDataSource.updateMovieViewList(movieViewList)
    }
    override suspend fun deleteAllMovieViewList() {
        localDataSource.deleteAllMovieViewList()
    }

    //viewListTvShowsDao
    override suspend fun getTvShowViewListById(tvShowId: Int): Flow<List<TVShowsViewList>> {
        return localDataSource.getTvShowViewListById(tvShowId)
    }
    override suspend fun getAllTvShowsViewList(): Flow<List<TVShowsViewList>> {
        return localDataSource.getAllTvShowsViewList()
    }
    override suspend fun insertTvShowViewList(tvShowViewList: TVShowsViewList) {
        localDataSource.insertTvShowViewList(tvShowViewList)
    }
    override suspend fun insertTvShowsViewList(tvShowViewList: List<TVShowsViewList>) {
        localDataSource.insertTvShowsViewList(tvShowViewList)
    }
    override suspend fun updateTvShowViewList(tvShowViewList: TVShowsViewList) {
        localDataSource.updateTvShowViewList(tvShowViewList)
    }
    override suspend fun deleteAllTvShowViewList() {
        localDataSource.deleteAllTvShowViewList()
    }

    //pinViewListDao
    override suspend fun getAllPinsViewList(): Flow<List<PinViewListModel>> {
        return localDataSource.getAllPinsViewList()
    }
    override suspend fun insertPinViewList(pin: PinViewListModel) {
        localDataSource.insertPinViewList(pin)
    }
    override suspend fun insertPinsViewList(pins: List<PinViewListModel>) {
        localDataSource.insertPinsViewList(pins)
    }
    override suspend fun updatePinViewList(pin: PinViewListModel) {
        localDataSource.updatePinViewList(pin)
    }
    override suspend fun deleteAllPinsViewList() {
        localDataSource.deleteAllPinsViewList()
    }
}