package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.data.model.dto.movie.MoviesRemoteKeys

@Dao
interface DiscoverMoviesRemoteKeysDao {

    @Query("SELECT * FROM movies_new_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): MoviesRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(remoteKeys: List<MoviesRemoteKeys>)

    @Query("DELETE FROM movies_new_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}
