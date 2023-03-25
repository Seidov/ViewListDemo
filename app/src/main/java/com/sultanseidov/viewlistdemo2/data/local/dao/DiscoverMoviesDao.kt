package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.data.entity.movie.MovieModel

@Dao
interface DiscoverMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDiscoverMovies(discoverMovies: List<MovieModel>)

    @Query("SELECT * FROM movies_table")
    fun getAllDiscoverMovies(): PagingSource<Int, MovieModel>

    @Query("DELETE FROM movies_table")
    suspend fun deleteAllDiscoverMovies()

}