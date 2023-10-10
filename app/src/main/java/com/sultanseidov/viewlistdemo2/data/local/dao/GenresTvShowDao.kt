package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresTvShowModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresTvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenresTvShowModel>)

    @Query("SELECT * FROM genres_tv_shows_table")
    fun getAllGenres(): Flow<List<GenresTvShowModel>>

    @Query("DELETE FROM genres_tv_shows_table")
    suspend fun deleteAllGenres()
}