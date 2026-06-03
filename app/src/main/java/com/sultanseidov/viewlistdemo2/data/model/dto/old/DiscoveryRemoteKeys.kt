package com.sultanseidov.viewlistdemo2.data.model.dto.old

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "discovery_remote_keys_table")
data class DiscoveryRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int, // Movie or TV ID
    val type: String, // "MOVIE" or "TV"
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)
