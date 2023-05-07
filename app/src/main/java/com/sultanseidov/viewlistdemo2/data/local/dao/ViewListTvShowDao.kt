package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sultanseidov.viewlistdemo2.data.model.viewlist.ViewListTvShowsModel
import com.sultanseidov.viewlistdemo2.data.model.tvshow.TvShowModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewListTvShowDao {

    @Query("SELECT * FROM view_list_tvshows_table WHERE tvShowId =:tvShowId")
    fun getTvShowById(tvShowId:Int): Flow<List<ViewListTvShowsModel>>

    @Query("SELECT * FROM view_list_tvshows_table")
    fun getAllTvShows(): Flow<List<ViewListTvShowsModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(myViewList: ViewListTvShowsModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(myViewList: List<TvShowModel>)

    @Update
    suspend fun updateTvShow(myViewList: ViewListTvShowsModel)

    @Query("DELETE FROM view_list_tvshows_table")
    suspend fun deleteAllTvShows()
}