package com.sultanseidov.viewlistdemo2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.dto.movie.TheTypeConverters
import com.sultanseidov.viewlistdemo2.data.model.pinviewlist.PinViewListModel
import com.sultanseidov.viewlistdemo2.data.local.dao.*
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresTvShowModel
import com.sultanseidov.viewlistdemo2.data.model.dto.movie.MoviesRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.dto.tvshow.TvShowsRemoteKeys
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.model.MoviesViewList
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import com.sultanseidov.viewlistdemo2.domain.model.TVShowsViewList

@TypeConverters(TheTypeConverters::class)

@Database(
    entities = [
        MovieModel::class, MoviesRemoteKeys::class,
        TvShowModel::class, TvShowsRemoteKeys::class,
        GenresMovieModel::class, GenresTvShowModel::class,
        MoviesViewList::class, TVShowsViewList::class,
        PinViewListModel::class],
    version = 33,
    exportSchema = false
)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun discoverMoviesDao(): DiscoverMoviesDao
    abstract fun discoverMoviesRemoteKeysDao(): DiscoverMoviesRemoteKeysDao
    abstract fun discoverTvShowsDao(): DiscoverTvShowsDao
    abstract fun discoverTvShowsRemoteKeysDao(): DiscoverTvShowsRemoteKeysDao
    abstract fun genresMovieDao(): GenresMovieDao
    abstract fun genresTvShowDao(): GenresTvShowDao
    abstract fun viewListMoviesDao(): ViewListMoviesDao
    abstract fun viewListTvShowsDao(): ViewListTvShowDao
    abstract fun pinViewListDao(): PinViewListDao


}