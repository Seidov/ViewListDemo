package com.sultanseidov.viewlistdemo2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.pinviewlist.PinViewListModel
import com.sultanseidov.viewlistdemo2.data.local.dao.*
import com.sultanseidov.viewlistdemo2.data.local.entity.DiscoveryMediaEntity
import com.sultanseidov.viewlistdemo2.data.local.entity.DiscoveryRemoteKeysEntity
import com.sultanseidov.viewlistdemo2.data.local.entity.PinEntity
import com.sultanseidov.viewlistdemo2.data.local.entity.WatchedMovieEntity
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresTvShowModel
import com.sultanseidov.viewlistdemo2.data.model.dto.old.DiscoveryRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.dto.old.MoviesRemoteKeys
import com.sultanseidov.viewlistdemo2.data.model.dto.old.TvShowsRemoteKeys
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.model.MoviesViewList
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import com.sultanseidov.viewlistdemo2.domain.model.TVShowsViewList

@TypeConverters(RoomConverters::class)
@Database(
    entities = [
        MovieModel::class,
        MoviesRemoteKeys::class,
        TvShowModel::class,
        TvShowsRemoteKeys::class,
        DiscoveryRemoteKeys::class,
        DiscoveryMediaEntity::class,
        DiscoveryRemoteKeysEntity::class,
        GenresMovieModel::class,
        GenresTvShowModel::class,
        MoviesViewList::class,
        TVShowsViewList::class,
        PinViewListModel::class,
        WatchedMovieEntity::class,
        PinEntity::class,
    ],
    version = 40,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun discoverMoviesDao(): DiscoverMoviesDao
    abstract fun discoverMoviesRemoteKeysDao(): DiscoverMoviesRemoteKeysDao
    abstract fun discoverTvShowsDao(): DiscoverTvShowsDao
    abstract fun discoverTvShowsRemoteKeysDao(): DiscoverTvShowsRemoteKeysDao
    abstract fun discoveryRemoteKeysDao(): DiscoveryRemoteKeysDao
    abstract fun discoveryDao(): DiscoveryDao
    abstract fun genresMovieDao(): GenresMovieDao
    abstract fun genresTvShowDao(): GenresTvShowDao
    abstract fun viewListMoviesDao(): ViewListMoviesDao
    abstract fun viewListTvShowsDao(): ViewListTvShowDao
    abstract fun pinViewListDao(): PinViewListDao
    abstract fun watchedMovieDao(): WatchedMovieDao
    abstract fun pinDao(): PinDao
}
