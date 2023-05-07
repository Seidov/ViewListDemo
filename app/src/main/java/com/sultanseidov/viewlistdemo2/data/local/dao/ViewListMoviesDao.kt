package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sultanseidov.viewlistdemo2.data.model.movie.MovieModel
import com.sultanseidov.viewlistdemo2.data.model.viewlist.ViewListMoviesModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewListMoviesDao {

    @Query("SELECT * FROM view_list_movies_table WHERE movieId =:movieId")
    fun getMovieById(movieId:Int): Flow<List<ViewListMoviesModel>>

    @Query("SELECT * FROM view_list_movies_table")
    fun getAllMovies(): Flow<List<ViewListMoviesModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(myViewList: ViewListMoviesModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(myViewList: List<MovieModel>)

    @Update
    suspend fun updateMovie(myViewList: ViewListMoviesModel)

    @Query("DELETE FROM view_list_movies_table")
    suspend fun deleteAllMovies()
}