package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.data.entity.movie.MoviesRemoteKeys

@Dao
interface DiscoverMoviesRemoteKeysDao {

    @Query("SELECT * FROM movies_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): MoviesRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<MoviesRemoteKeys>)

    @Query("DELETE FROM movies_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}
