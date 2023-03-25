package com.sultanseidov.viewlistdemo2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sultanseidov.viewlistdemo2.data.entity.genre.GenreModel
import com.sultanseidov.viewlistdemo2.data.entity.movie.MoviesRemoteKeys
import com.sultanseidov.viewlistdemo2.data.entity.movie.TheTypeConverters
import com.sultanseidov.viewlistdemo2.data.entity.myviewlist.MyViewListModel
import com.sultanseidov.viewlistdemo2.data.entity.movie.MovieModel
import com.sultanseidov.viewlistdemo2.data.entity.tvshow.TvShowModel
import com.sultanseidov.viewlistdemo2.data.entity.tvshow.TvShowsRemoteKeys
import com.sultanseidov.viewlistdemo2.data.entity.viewlistpin.PinsViewListModel
import com.sultanseidov.viewlistdemo2.data.local.dao.*

@TypeConverters(TheTypeConverters::class)

@Database(
    entities = [MovieModel::class, MoviesRemoteKeys::class,
        TvShowModel::class, TvShowsRemoteKeys::class,
        GenreModel::class,
        MyViewListModel::class, PinsViewListModel::class],
    version = 20,
    exportSchema = false
)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun discoverMoviesDao(): DiscoverMoviesDao
    abstract fun discoverMoviesRemoteKeysDao(): DiscoverMoviesRemoteKeysDao

    abstract fun discoverTvShowsDao(): DiscoverTvShowsDao
    abstract fun discoverTvShowsRemoteKeysDao(): DiscoverTvShowsRemoteKeysDao

    abstract fun genresDao(): GenresDao
    abstract fun myViewListDao(): MyListViewDao
    abstract fun viewListPinsDao(): ViewListPinsDao


}