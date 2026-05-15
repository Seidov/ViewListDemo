package com.sultanseidov.viewlistdemo2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.data.entity.PopularMoviesRemoteKeys

@Dao
interface PopularMoviesRemoteKeysDao {

    @Query("SELECT * FROM popular_movies_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): PopularMoviesRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<PopularMoviesRemoteKeys>)

    @Query("DELETE FROM popular_movies_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}
