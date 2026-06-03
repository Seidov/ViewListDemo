package com.sultanseidov.viewlistdemo2.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.sultanseidov.viewlistdemo2.data.local.entity.DiscoveryMediaEntity
import com.sultanseidov.viewlistdemo2.data.local.entity.DiscoveryRemoteKeysEntity

@Dao
interface DiscoveryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(media: List<DiscoveryMediaEntity>)

    @Query("SELECT * FROM discovery_cache_table WHERE categoryTag = :tag ORDER BY page ASC, indexInPage ASC")
    fun getPagingSourceByTag(tag: String): PagingSource<Int, DiscoveryMediaEntity>

    @Query("DELETE FROM discovery_cache_table WHERE categoryTag = :tag")
    suspend fun clearCacheByTag(tag: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(keys: List<DiscoveryRemoteKeysEntity>)

    @Query("SELECT * FROM discovery_remote_keys_new WHERE id = :id AND categoryTag = :tag")
    suspend fun getRemoteKeys(id: Int, tag: String): DiscoveryRemoteKeysEntity?

    @Query("DELETE FROM discovery_remote_keys_new WHERE categoryTag = :tag")
    suspend fun clearRemoteKeysByTag(tag: String)
}
