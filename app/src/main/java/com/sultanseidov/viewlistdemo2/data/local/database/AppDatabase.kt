package com.sultanseidov.viewlistdemo2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sultanseidov.viewlistdemo2.data.entity.genre.GenreModel
import com.sultanseidov.viewlistdemo2.data.entity.movie.PopularMoviesRemoteKeys
import com.sultanseidov.viewlistdemo2.data.entity.movie.TheTypeConverters
import com.sultanseidov.viewlistdemo2.data.entity.myviewlist.MyViewListModel
import com.sultanseidov.viewlistdemo2.data.entity.movie.PopularMoviesModel
import com.sultanseidov.viewlistdemo2.data.entity.viewlistpin.ViewListPinModel
import com.sultanseidov.viewlistdemo2.data.local.dao.*

@TypeConverters(TheTypeConverters::class)

@Database(
    entities = [PopularMoviesModel::class, PopularMoviesRemoteKeys::class, GenreModel::class,
                MyViewListModel::class, ViewListPinModel::class],
    version = 13,
    exportSchema = false
)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun popularMoviesRemoteKeysDao(): PopularMoviesRemoteKeysDao
    abstract fun genresDao(): GenresDao
    abstract fun myViewListDao(): MyListViewDao
    abstract fun viewListPinsDao(): ViewListPinsDao


}