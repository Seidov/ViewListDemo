package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.data.entity.genre.GenreModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenres(genres: List<GenreModel>)

    @Query("SELECT * FROM genres_table")
    fun getAllGenres(): Flow<List<GenreModel>>

    @Query("DELETE FROM genres_table")
    suspend fun deleteAllGenres()
}