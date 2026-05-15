package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel

@Dao
interface DiscoverTvShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscoverTvShows(discoverTvShows: List<TvShowModel>)

    @Query("SELECT * FROM tv_show_new_table")
    fun getAllDiscoverTvShows(): PagingSource<Int, TvShowModel>

    @Query("DELETE FROM tv_show_new_table")
    suspend fun deleteAllDiscoverTvShows()

}