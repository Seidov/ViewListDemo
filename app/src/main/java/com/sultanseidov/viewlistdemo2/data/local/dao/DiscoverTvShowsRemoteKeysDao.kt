package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.data.entity.tvshow.TvShowsRemoteKeys

@Dao
interface DiscoverTvShowsRemoteKeysDao {

    @Query("SELECT * FROM tv_show_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): TvShowsRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<TvShowsRemoteKeys>)

    @Query("DELETE FROM tv_show_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}
