package com.sultanseidov.viewlistdemo2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sultanseidov.viewlistdemo2.data.entity.MovieModel
import com.sultanseidov.viewlistdemo2.data.entity.PopularMoviesRemoteKeys


@Database(
    entities = [MovieModel::class, PopularMoviesRemoteKeys::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun popularMoviesRemoteKeysDao(): PopularMoviesRemoteKeysDao

}