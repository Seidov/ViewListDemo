package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.data.local.entity.WatchedMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchedMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchedMovie(movie: WatchedMovieEntity)

    @Query("SELECT * FROM watched_movies_table WHERE movie_id = :id")
    suspend fun getWatchedMovieById(id: Long): WatchedMovieEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM watched_movies_table WHERE movie_id = :id)")
    fun isMovieWatchedFlow(id: Long): Flow<Boolean>

    @Query("SELECT * FROM watched_movies_table")
    suspend fun getAllWatchedMovies(): List<WatchedMovieEntity>

    @Query("SELECT movie_id FROM watched_movies_table")
    fun getAllWatchedMovieIdsFlow(): Flow<List<Long>>

    @Query("SELECT * FROM watched_movies_table WHERE assigned_pin_id = :pinId")
    fun getMoviesByPinIdFlow(pinId: Long): Flow<List<WatchedMovieEntity>>
}
