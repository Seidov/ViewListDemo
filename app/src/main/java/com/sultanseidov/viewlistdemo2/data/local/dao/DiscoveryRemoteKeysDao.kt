package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sultanseidov.viewlistdemo2.data.model.dto.old.DiscoveryRemoteKeys

@Dao
interface DiscoveryRemoteKeysDao {

    @Query("SELECT * FROM discovery_remote_keys_table WHERE id = :id AND type = :type")
    suspend fun getRemoteKeys(id: Int, type: String): DiscoveryRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(remoteKeys: List<DiscoveryRemoteKeys>)

    @Query("DELETE FROM discovery_remote_keys_table WHERE type = :type")
    suspend fun deleteAllRemoteKeysByType(type: String)
}
