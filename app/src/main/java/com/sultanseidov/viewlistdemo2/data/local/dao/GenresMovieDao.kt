package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenresMovieModel>)

    @Query("SELECT * FROM genres_movie_table")
    fun getAllGenres(): Flow<List<GenresMovieModel>>

    @Query("DELETE FROM genres_movie_table")
    suspend fun deleteAllGenres()
}