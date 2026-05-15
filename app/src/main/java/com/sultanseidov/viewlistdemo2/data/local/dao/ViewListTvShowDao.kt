package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sultanseidov.viewlistdemo2.domain.model.TVShowsViewList
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewListTvShowDao {

    @Query("SELECT * FROM view_list_tvshows_table WHERE id =:id")
    fun getTvShowById(id:Int): Flow<List<TVShowsViewList>>

    @Query("SELECT * FROM view_list_tvshows_table")
    fun getAllTvShows(): Flow<List<TVShowsViewList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(myViewList: TVShowsViewList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(myViewList: List<TVShowsViewList>)

    @Update
    suspend fun updateTvShow(myViewList: TVShowsViewList)

    @Query("DELETE FROM view_list_tvshows_table")
    suspend fun deleteAllTvShows()
}