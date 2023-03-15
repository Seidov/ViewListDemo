package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.data.entity.movie.PopularMoviesModel

@Dao
interface PopularMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMovies(popularMovies: List<PopularMoviesModel>)

    @Query("SELECT * FROM popular_movies_table")
    fun getAllPopularMovies(): PagingSource<Int, PopularMoviesModel>

    @Query("DELETE FROM popular_movies_table")
    suspend fun deleteAllPopularMovies()

}