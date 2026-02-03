package com.sultanseidov.viewlistdemo2.data.repository.dataSourceImp

import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresTvShowModel
import com.sultanseidov.viewlistdemo2.data.model.pinviewlist.PinViewListModel
import com.sultanseidov.viewlistdemo2.data.repository.dataSource.ILocalDataSource
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.model.MoviesViewList
import com.sultanseidov.viewlistdemo2.domain.model.TVShowsViewList
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val appDatabase: AppDatabase
):ILocalDataSource {

    //discoverMoviesDao
    override suspend fun insertDiscoverMovies(discoverMovies: List<MovieModel>) {
        appDatabase.discoverMoviesDao().insertDiscoverMovies(discoverMovies)
    }
    override suspend fun deleteAllDiscoverMovies() {
        appDatabase.discoverMoviesDao().deleteAllDiscoverMovies()
    }

    //discoverTvShowsDao
    override suspend fun insertDiscoverTvShows(discoverTvShows: List<TvShowModel>) {
        appDatabase.discoverTvShowsDao().insertDiscoverTvShows(discoverTvShows)
    }
    override suspend fun deleteAllDiscoverTvShows() {
        appDatabase.discoverTvShowsDao().deleteAllDiscoverTvShows()
    }

    //genresMovieDao
    override suspend fun getAllMovieGenres(): Flow<List<GenresMovieModel>> {
        return appDatabase.genresMovieDao().getAllGenres()
    }
    override suspend fun insertMovieGenres(genres: List<GenresMovieModel>) {
        appDatabase.genresMovieDao().insertGenres(genres)
    }
    override suspend fun deleteAllMovieGenres() {
        appDatabase.genresMovieDao().deleteAllGenres()
    }

    //genresTvShowDao
    override suspend fun getAllTvShowGenres(): Flow<List<GenresTvShowModel>> {
        return appDatabase.genresTvShowDao().getAllGenres()
    }
    override suspend fun insertTvShowGenres(genres: List<GenresTvShowModel>) {
        appDatabase.genresTvShowDao().insertGenres(genres)
    }
    override suspend fun deleteAllTvShowGenres() {
        appDatabase.genresTvShowDao().deleteAllGenres()
    }

    //viewListMoviesDao
    override suspend fun getMovieViewListById(movieId: Int): Flow<List<MoviesViewList>> {
        return appDatabase.viewListMoviesDao().getMovieById(movieId)
    }
    override suspend fun getAllMoviesViewList(): Flow<List<MoviesViewList>> {
        return appDatabase.viewListMoviesDao().getAllMovies()
    }
    override suspend fun insertMovieViewList(movieViewList: MoviesViewList) {
        appDatabase.viewListMoviesDao().insertMovie(movieViewList)
    }
    override suspend fun insertMoviesViewList(movieViewList: List<MoviesViewList>) {
        appDatabase.viewListMoviesDao().insertMovies(movieViewList)
    }
    override suspend fun updateMovieViewList(movieViewList: MoviesViewList) {
        appDatabase.viewListMoviesDao().updateMovie(movieViewList)
    }
    override suspend fun deleteAllMovieViewList() {
        appDatabase.viewListMoviesDao().deleteAllMovies()
    }

    //viewListTvShowsDao
    override suspend fun getTvShowViewListById(tvShowId: Int): Flow<List<TVShowsViewList>> {
        return appDatabase.viewListTvShowsDao().getTvShowById(tvShowId)
    }
    override suspend fun getAllTvShowsViewList(): Flow<List<TVShowsViewList>> {
        return appDatabase.viewListTvShowsDao().getAllTvShows()
    }
    override suspend fun insertTvShowViewList(tvShowViewList: TVShowsViewList) {
        appDatabase.viewListTvShowsDao().insertTvShow(tvShowViewList)
    }
    override suspend fun insertTvShowsViewList(tvShowViewList: List<TVShowsViewList>) {
        appDatabase.viewListTvShowsDao().insertTvShows(tvShowViewList)
    }
    override suspend fun updateTvShowViewList(tvShowViewList: TVShowsViewList) {
        appDatabase.viewListTvShowsDao().updateTvShow(tvShowViewList)
    }
    override suspend fun deleteAllTvShowViewList() {
        appDatabase.viewListTvShowsDao().deleteAllTvShows()
    }

    //pinViewListDao
    override suspend fun getAllPinsViewList(): Flow<List<PinViewListModel>> {
        return appDatabase.pinViewListDao().getAllPins()
    }
    override suspend fun insertPinViewList(pin: PinViewListModel) {
        appDatabase.pinViewListDao().insertPin(pin)
    }
    override suspend fun insertPinsViewList(pins: List<PinViewListModel>) {
        appDatabase.pinViewListDao().insertPins(pins)
    }
    override suspend fun updatePinViewList(pin: PinViewListModel) {
        appDatabase.pinViewListDao().updatePin(pin)
    }
    override suspend fun deleteAllPinsViewList() {
        appDatabase.pinViewListDao().deleteAllPins()
    }

}