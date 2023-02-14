package com.sultanseidov.viewlistdemo2.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.data.entity.MovieModel

@Dao
interface PopularMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMovies(popularMovies: List<MovieModel>)

    @Query("SELECT * FROM popular_movies_table")
    fun getAllPopularMovies(): PagingSource<Int, MovieModel>

    @Query("DELETE FROM popular_movies_table")
    suspend fun deleteAllPopularMovies()

}