package com.sultanseidov.viewlistdemo2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sultanseidov.viewlistdemo2.data.entity.GenreModel
import com.sultanseidov.viewlistdemo2.data.entity.MovieModel
import com.sultanseidov.viewlistdemo2.data.entity.PopularMoviesRemoteKeys
import com.sultanseidov.viewlistdemo2.data.local.dao.GenresDao
import com.sultanseidov.viewlistdemo2.data.local.dao.PopularMoviesDao
import com.sultanseidov.viewlistdemo2.data.local.dao.PopularMoviesRemoteKeysDao


@Database(
    entities = [MovieModel::class, PopularMoviesRemoteKeys::class, GenreModel::class],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun popularMoviesRemoteKeysDao(): PopularMoviesRemoteKeysDao
    abstract fun genresDao(): GenresDao


}