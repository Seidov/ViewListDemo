package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sultanseidov.viewlistdemo2.domain.model.MoviesViewList
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewListMoviesDao {

    @Query("SELECT * FROM view_list_movies_table WHERE id =:id")
    fun getMovieById(id:Int): Flow<List<MoviesViewList>>

    @Query("SELECT * FROM view_list_movies_table")
    fun getAllMovies(): Flow<List<MoviesViewList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(myViewList: MoviesViewList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(myViewList: List<MoviesViewList>)

    @Update
    suspend fun updateMovie(myViewList: MoviesViewList)

    @Query("DELETE FROM view_list_movies_table")
    suspend fun deleteAllMovies()
}