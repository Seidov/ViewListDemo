package com.sultanseidov.viewlistdemo2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sultanseidov.viewlistdemo2.data.model.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.movie.MoviesRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.movie.TheTypeConverters
import com.sultanseidov.viewlistdemo2.data.model.viewlist.ViewListMoviesModel
import com.sultanseidov.viewlistdemo2.data.model.movie.MovieModel
import com.sultanseidov.viewlistdemo2.data.model.tvshow.TvShowModel
import com.sultanseidov.viewlistdemo2.data.model.tvshow.TvShowsRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.pinviewlist.PinViewListModel
import com.sultanseidov.viewlistdemo2.data.local.dao.*
import com.sultanseidov.viewlistdemo2.data.model.genre.GenresTvShowModel
import com.sultanseidov.viewlistdemo2.data.model.viewlist.ViewListTvShowsModel

@TypeConverters(TheTypeConverters::class)

@Database(
    entities = [MovieModel::class, MoviesRemoteKeys::class,
        TvShowModel::class, TvShowsRemoteKeys::class,
        GenresMovieModel::class, GenresTvShowModel::class,
        ViewListMoviesModel::class, ViewListTvShowsModel::class,
        PinViewListModel::class],
    version = 22,
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